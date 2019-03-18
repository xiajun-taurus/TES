package com.leimengling.teachingevalutingsystem.service.impl;

import com.leimengling.teachingevalutingsystem.domain.Paper;
import com.leimengling.teachingevalutingsystem.repository.ClassInfoRepository;
import com.leimengling.teachingevalutingsystem.repository.CourseInfoRepository;
import com.leimengling.teachingevalutingsystem.repository.PaperRepository;
import com.leimengling.teachingevalutingsystem.repository.StudentRepository;
import com.leimengling.teachingevalutingsystem.repository.TeacherRepository;
import com.leimengling.teachingevalutingsystem.service.PaperService;
import com.leimengling.teachingevalutingsystem.utils.ClassUtils;
import com.leimengling.teachingevalutingsystem.utils.DateUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lei MengLing.
 */
@Service
public class PaperServiceImpl implements PaperService {

  private StudentRepository studentRepository;
  private TeacherRepository teacherRepository;
  private ClassInfoRepository classInfoRepository;
  CourseInfoRepository courseInfoRepository;
  @Autowired
  public PaperServiceImpl(
      StudentRepository studentRepository,
      TeacherRepository teacherRepository,
      ClassInfoRepository classInfoRepository,
      CourseInfoRepository courseInfoRepository) {
    this.studentRepository = studentRepository;
    this.teacherRepository = teacherRepository;
    this.classInfoRepository = classInfoRepository;
    this.courseInfoRepository = courseInfoRepository;
  }

  @Override
  public Paper getPaperByStudentId(String id) {
    //根据学生id找班级id

    return null;
  }

  @Autowired
  private PaperRepository paperRepository;

  @Override
  public int updatePaper(Paper paper) {
    return paperRepository.updatePaper(paper);
  }

  @Override
  public int updatePaperQuestions(String id, List<String> questions) {
    //将数据库中的问题取出
    String questionsInPaper = paperRepository.findQuestionsInPaper(id);
    String join = String.join(",", questions);
    //新旧问题结合
    String s;
    //如果数据库为空不用结合
    if (questionsInPaper == null) {
      s = join;
    }else {
      s = join + "," + questionsInPaper;
    }
    //降重，把结合的问题id通过逗号分隔，放到set中去重
    String[] split = s.split(",");
    List<String> strings = Arrays.asList(split);
    HashSet<String> set = new HashSet<>(strings);
    for (int i = 0; i < strings.size(); i++) {
      set.add(strings.get(i));
    }
    String join1 = String.join(",", set);
    //最后更新到数据库中
    return paperRepository.updatePaperQuestions(id,join1);
  }

  @Override
  public List<? extends Paper> getAllPapers() {
    //内部类，添加格式化日期字段
    @lombok.Data
    class Data extends Paper{
      private String fDate;
    }
    //找到所有问卷信息
    List<Paper> allPapers = paperRepository.findAllPapers();
    List<Data> dataList =new ArrayList<>();
    //对每个问卷信息的日期格式化，放到新内部类里
    for (Paper paper:allPapers){
      Data data = new Data();
      try {
        ClassUtils.fatherToChild(paper,data);
      } catch (Exception e) {
        e.printStackTrace();
      }
      String s = DateUtils.defaultFomat(paper.getLastEditTime());
      data.setFDate(s);
      dataList.add(data);
    }
    return dataList;//返回带有格式化日期的对象列表
  }

  @Override
  public int addPaper(Paper paper) {
    return paperRepository.insertPaper(paper);
  }

  @Override
  public Paper getPaperById(String id) {
    return paperRepository.findPaperById(id);
  }

  @Override
  public int deletePaperById(String id) {
    return paperRepository.deletePaper(id);
  }
}
