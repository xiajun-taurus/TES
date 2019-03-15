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
import org.springframework.stereotype.Service;

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
  private XuanKeRepository  xuanKeRepository;
  /*
  获取评论结果
   */
  @Override
  public List<? extends CommentResult> findAllResults() {
    @lombok.Data
    class Data extends CommentResult {

      private String teacherName;
      private String paperName;
      private int count;
      private String fDate;
      private List<String> keyWords;
    }
    List<CommentResult> allResults = resultRepository.findAllResults();
    ArrayList<Data> datas = new ArrayList<>();
    allResults.forEach(result -> {
      User userById = userRepository.findUserById(result.getTeacherId());
      Paper paperById = paperRepository.findPaperById(result.getPaperId());
      int i = resultItemRepository.countItemByResultId(result.getOid());
      Data data = new Data();
      try {
        ClassUtils.fatherToChild(result,data);
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
      List<String> keyword = textRankKeyword.getKeyword(result.getComment());
      data.setKeyWords(keyword);
      datas.add(data);
    });
    return datas;
  }

  /*
 新增评论
   */
  @Override
  public int addComment(CommentVO commentVO, String userId) {
    String comment = commentVO.getComment();
    int score = commentVO.getScore();
    String resultId = commentVO.getResultId();
    CommentResultItem commentResultItem = new CommentResultItem();
    commentResultItem.setScore(score);
    commentResultItem.setComment(comment);
    commentResultItem.setResultId(resultId);
    commentResultItem.setCommenterId(userId);
    int itemState = resultItemRepository.insertCommentResultItem(commentResultItem);
    //更新结果中的comment和score
    CommentResult resultById = resultRepository.getResultById(resultId);
    int i = resultItemRepository.countItemByResultId(resultId);
    float v = (resultById.getAverScore() * (i - 1) + score) / i;

    String comment1 = resultById.getComment();
    if (comment1 != null) {
      comment += comment1;
    }
    comment += " ";
    CommentResult commentResult = new CommentResult();
    commentResult.setOid(resultId);
    commentResult.setAverScore(v);
    commentResult.setComment(comment);
    return resultRepository.updateResult(commentResult);
  }

  @Override
  public String getResultTitle(String oid) {
    CommentResult resultById = resultRepository.getResultById(oid);
    User userById = userRepository.findUserById(resultById.getTeacherId());
    Paper paperById = paperRepository.findPaperById(resultById.getPaperId());
    return userById.getTrueName()+"-"+paperById.getPaperTitle();
  }

  /**
   * 获取未评教学生名单
   * @param oid 结果id
   * @return 未评价学生信息
   */
  @Override
  public List<? extends Student> getUncommented(String oid) {
    @lombok.Data
    class Data extends Student{
      private String stuName;
      private String className;
      private String schoolNo;
    }
    CommentResult resultById = resultRepository.getResultById(oid);
    //获取该教师的id
    String teacherId = resultById.getTeacherId();
    Teacher teacherById = teacherRepository.findTeacherById(teacherId);
    String courseId = teacherById.getCourseId();//获取教师所教课程id
    List<String> classByCourseId = xuanKeRepository.findClassByCourseId(courseId);//获取该课程所有班级
    ArrayList<String> allStudents = new ArrayList<>();
    for (String classId:classByCourseId){
      //班里所有学生id
      List<String> allStudentIdInClass = studentRepository.findAllStudentIdInClass(classId);
      allStudents.addAll(allStudentIdInClass);
    }
    //评价学生id
    List<String> allCommenterId = resultItemRepository.getAllCommenterId(oid);
    //声明未评价学生id
    List<String> allUnCommentId = new ArrayList<>();
    ArrayList<Data> datas = new ArrayList<>();
    for (String id:allStudents){
      //如果评价者里没有班级内的某个id，将其放入
      if (!allCommenterId.contains(id)){
        allUnCommentId.add(id);
      }
    }
    for(String id:allUnCommentId){
      Data data = new Data();
      Student byOid = studentRepository.findByOid(id);
      try {
        ClassUtils.fatherToChild(byOid,data);
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
    return datas;
  }

  @Override
  public List<ChartsData> getResultCharts() {
    //1.获取所有评价结果
    List<CommentResult> allResults = resultRepository.findAllResults();
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
      for (String classId:classByCourseId){
        //班里所有学生id
        List<String> allStudentIdInClass = studentRepository.findAllStudentIdInClass(classId);
        allStudents.addAll(allStudentIdInClass);
      }
      Paper paperById = paperRepository.findPaperById(result.getPaperId());
      User userById = userRepository.findUserById(teacherId);
      String paperTitle = paperById.getPaperTitle();
      String trueName = userById.getTrueName();
      chartsData.setTitle(paperTitle+"-"+trueName);
      dataMap.put("id",result.getOid());
      dataMap.put("commented", commented);
      //未评价人数=总人数-已评教人数
      dataMap.put("uncommented", allStudents.size()-commented);

      chartsDatas.add(chartsData);
    }

    return chartsDatas;
  }
}
