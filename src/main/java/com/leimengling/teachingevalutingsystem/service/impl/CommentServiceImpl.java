package com.leimengling.teachingevalutingsystem.service.impl;

import com.hankcs.hanlp.summary.TextRankKeyword;
import com.leimengling.teachingevalutingsystem.VO.CommentVO;
import com.leimengling.teachingevalutingsystem.domain.ChartsData;
import com.leimengling.teachingevalutingsystem.domain.CommentResult;
import com.leimengling.teachingevalutingsystem.domain.CommentResultItem;
import com.leimengling.teachingevalutingsystem.domain.Paper;
import com.leimengling.teachingevalutingsystem.domain.Student;
import com.leimengling.teachingevalutingsystem.domain.Teacher;
import com.leimengling.teachingevalutingsystem.domain.User;
import com.leimengling.teachingevalutingsystem.repository.ClassInfoRepository;
import com.leimengling.teachingevalutingsystem.repository.CommentResultItemRepository;
import com.leimengling.teachingevalutingsystem.repository.CommentResultRepository;
import com.leimengling.teachingevalutingsystem.repository.PaperRepository;
import com.leimengling.teachingevalutingsystem.repository.StudentRepository;
import com.leimengling.teachingevalutingsystem.repository.TeacherRepository;
import com.leimengling.teachingevalutingsystem.repository.UserRepository;
import com.leimengling.teachingevalutingsystem.repository.XuanKeRepository;
import com.leimengling.teachingevalutingsystem.service.CommentService;
import com.leimengling.teachingevalutingsystem.utils.ClassUtils;
import com.leimengling.teachingevalutingsystem.utils.DateUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Lei MengLing.
 */
@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  private CommentResultRepository resultRepository;
  @Autowired
  private CommentResultItemRepository resultItemRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PaperRepository paperRepository;
  @Autowired
  private StudentRepository studentRepository;
  @Autowired
  private ClassInfoRepository classInfoRepository;
  @Autowired
  private TeacherRepository teacherRepository;
  @Autowired
  private XuanKeRepository xuanKeRepository;

  /*
  获取评论结果
   */
  @Override
  public List<? extends CommentResult> findAllResults(String id) {
    //内部类，新增教师姓名，问卷名称、评价人数、格式化时间、关键词
    @lombok.Data
    class Data extends CommentResult {

      private String teacherName;//教师姓名
      private String paperName;//问卷名称
      private int count;//评价人数
      private String fDate;//格式化时间
      private List<String> keyWords;//关键词
    }
    //获取所有结果
    List<CommentResult> allResults = new ArrayList<>();
    if (id != null) {
      allResults = resultRepository.findAllResults(id);
    }else {
      allResults = resultRepository.findAllResultsByAdmin();
    }
    ArrayList<Data> datas = new ArrayList<>();
    //对于每一条记录，查询出对应的教师姓名，问卷名称，评价人数，放到内部类。再把评论使用textrank分成关键词，把时间格式化
    allResults.forEach(result -> {
      User userById = userRepository.findUserById(result.getTeacherId());
      Paper paperById = paperRepository.findPaperById(result.getPaperId());
      int i = resultItemRepository.countItemByResultId(result.getOid());
      Data data = new Data();
      try {
        ClassUtils.fatherToChild(result, data);
      } catch (Exception e) {
        e.printStackTrace();
      }
      //将对应的时间格式化，教师姓名、试卷名称、评论人数放入data中
      data.setFDate(DateUtils.defaultFomat(result.getCreateTime()));

      data.setTeacherName(userById.getTrueName());
      data.setPaperName(paperById.getPaperTitle());
      data.setCount(i);
      //调用textRank算法的方法，进行取词
      TextRankKeyword textRankKeyword = new TextRankKeyword();
      String comment = result.getComment();

      List<String> keyword = new ArrayList<>();
      //
      if (comment != null) {
        keyword = textRankKeyword.getKeyword(comment);
      }
      data.setKeyWords(keyword);
      datas.add(data);
    });
    return datas;
  }

  /**
   * 根据传来的对象和评价人id，提取主观评价和评价分数，记录评价记录，归并总评价结果
   *
   * @return 0 失败 1 成功
   */
  @Override
  public int addComment(CommentVO commentVO, String userId) {
    String comment = commentVO.getComment();
    //获取评价分数
    int score = commentVO.getScore();
    //获取评价id
    String resultId = commentVO.getResultId();
    //创建评价详情对象，并将评价分数，主观评价，评价结果id，评价者id写入
    CommentResultItem commentResultItem = new CommentResultItem();
    commentResultItem.setScore(score);
    commentResultItem.setComment(comment);
    commentResultItem.setResultId(resultId);
    commentResultItem.setCommenterId(userId);
    //将评价详情写入数据库
    int itemState = resultItemRepository.insertCommentResultItem(commentResultItem);
    //更新结果中的comment和score
    CommentResult resultById = resultRepository.getResultById(resultId);

    //取出改评价下有多少评价详情(评价人数)
    int i = resultItemRepository.countItemByResultId(resultId);
    //平均分=（上次平均分*上次评价人数+本次评价分数）/本次评价人数
    //本次评价人数-上次评价人数 =1
    //先乘再除会造成误差，在可允许范围之内
    float v = (resultById.getAverScore() * (i - 1) + score) / i;
    //取出之前的评价内容与现在的评价内容拼接
    String comment1 = resultById.getComment();
    if (comment1 != null) {
      comment += comment1;
    }
    comment += " ";
    CommentResult commentResult = new CommentResult();
    commentResult.setOid(resultId);
    commentResult.setAverScore(v);
    commentResult.setComment(comment);
    //更新平均分，和评价内容
    return resultRepository.updateResult(commentResult);
  }

  /**
   * 根据id获取评价结果的名称 格式：被评价教师姓名-绑定的问卷名称
   *
   * @return string类型的名称
   */
  @Override
  public String getResultTitle(String oid) {
    CommentResult resultById = resultRepository.getResultById(oid);
    //查询用户信息
    User userById = userRepository.findUserById(resultById.getTeacherId());
    //查询问卷信息
    Paper paperById = paperRepository.findPaperById(resultById.getPaperId());
    //拼接真实姓名和问卷名称并返回
    return userById.getTrueName() + "-" + paperById.getPaperTitle();
  }

  /**
   * 获取未评教学生名单
   *
   * @param oid 结果id
   * @return 未评价学生信息
   */
  @Override
  public List<? extends Student> getUncommented(String oid) {
    //内部类，增加学生姓名，班级名称，学号字段
    @lombok.Data
    class Data extends Student {

      private String stuName;
      private String className;
      private String schoolNo;
    }
    //根据当前评教结果id查找结果
    CommentResult resultById = resultRepository.getResultById(oid);
    //在结果信息中获取该教师的id
    String teacherId = resultById.getTeacherId();
    //根据id获取教师信息
    Teacher teacherById = teacherRepository.findTeacherById(teacherId);
    String courseId = teacherById.getCourseId();//获取教师所教课程id
    List<String> classByCourseId = xuanKeRepository.findClassByCourseId(courseId);//获取该课程所有班级
    ArrayList<String> allStudents = new ArrayList<>();
    for (String classId : classByCourseId) {
      //班里所有学生id
      List<String> allStudentIdInClass = studentRepository.findAllStudentIdInClass(classId);
      //将每批学生放到一个大集合
      allStudents.addAll(allStudentIdInClass);
    }
    //所有评价学生id
    List<String> allCommenterId = resultItemRepository.getAllCommenterId(oid);
    //声明未评价学生id
    List<String> allUnCommentId = new ArrayList<>();
    ArrayList<Data> datas = new ArrayList<>();
    //遍历所有学生id
    for (String id : allStudents) {
      //如果某个学生的id不在已评价id中，将该id放入未评价id列表
      if (!allCommenterId.contains(id)) {
        allUnCommentId.add(id);
      }
    }
    //遍历未评价id列表，对于每一个id查询出对应的信息，存到内部类中
    for (String id : allUnCommentId) {
      Data data = new Data();
      Student byOid = studentRepository.findByOid(id);
      try {
        ClassUtils.fatherToChild(byOid, data);
      } catch (Exception e) {
        e.printStackTrace();
      }
      User userById = userRepository.findUserById(id);
      data.setStuName(userById.getTrueName());
      data.setSchoolNo(userById.getSchoolNo());
      String classId = byOid.getClassId();
      data.setClassName(classInfoRepository.findClassInfoById(classId).getClassName());
      datas.add(data);
    }
    return datas;//返回带有扩展信息的对象列表
  }

  @Override
  public List<ChartsData> getResultCharts() {
    //1.获取所有评价结果
    List<CommentResult> allResults = resultRepository.findAllResultsByAdmin();
    //2.创建ChartsData的List
    ArrayList<ChartsData> chartsDatas = new ArrayList<>();
    for (CommentResult result : allResults) {
      ChartsData chartsData = new ChartsData();
      HashMap<String, Object> dataMap = chartsData.getDataMap();
      //查询当前评教结果下评教人数
      int commented = resultItemRepository.countItemByResultId(result.getOid());
      //查询当前评教结果下所有班级
      String teacherId = result.getTeacherId();
      Teacher teacherById = teacherRepository.findTeacherById(teacherId);
      String courseId = teacherById.getCourseId();//获取教师所教课程id
      List<String> classByCourseId = xuanKeRepository.findClassByCourseId(courseId);//获取该课程所有班级
      ArrayList<String> allStudents = new ArrayList<>();
      for (String classId : classByCourseId) {
        //班里所有学生id
        List<String> allStudentIdInClass = studentRepository.findAllStudentIdInClass(classId);
        allStudents.addAll(allStudentIdInClass);
      }
      Paper paperById = paperRepository.findPaperById(result.getPaperId());
      User userById = userRepository.findUserById(teacherId);
      String paperTitle = paperById.getPaperTitle();
      String trueName = userById.getTrueName();
      chartsData.setTitle(paperTitle + "-" + trueName);
      dataMap.put("id", result.getOid());
      dataMap.put("commented", commented);
      //未评价人数=总人数-已评教人数
      dataMap.put("uncommented", allStudents.size() - commented);

      chartsDatas.add(chartsData);
    }

    return chartsDatas;
  }
}
