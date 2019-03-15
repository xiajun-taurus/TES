package com.leimengling.teachingevalutingsystem;

import com.leimengling.teachingevalutingsystem.domain.Teacher;
import com.leimengling.teachingevalutingsystem.repository.CourseInfoRepository;
import com.leimengling.teachingevalutingsystem.repository.PaperRepository;
import com.leimengling.teachingevalutingsystem.repository.StudentRepository;
import com.leimengling.teachingevalutingsystem.repository.TeacherRepository;
import com.leimengling.teachingevalutingsystem.repository.XuanKeRepository;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Lei MengLing.
 */
@Component
@Slf4j
public class Task {

  private PaperRepository paperRepository;
  private TeacherRepository teacherRepository;
  private CourseInfoRepository courseInfoRepository;
  private StudentRepository studentRepository;
  private WebApplicationContext applicationContext;
  private XuanKeRepository xuanKeRepository;

  @Autowired
  public Task(PaperRepository paperRepository,
      TeacherRepository teacherRepository,
      CourseInfoRepository courseInfoRepository,
      StudentRepository studentRepository,
      WebApplicationContext applicationContext,
      XuanKeRepository xuanKeRepository) {
    this.paperRepository = paperRepository;
    this.teacherRepository = teacherRepository;
    this.courseInfoRepository = courseInfoRepository;
    this.studentRepository = studentRepository;
    this.applicationContext = applicationContext;
    this.xuanKeRepository = xuanKeRepository;
  }


  @Scheduled(fixedDelay = 1000 * 60)
  public void Job() {
    //获取application容器
    ServletContext servletContext = applicationContext.getServletContext();
    //获取所有绑定问卷的教师
    List<Teacher> bindedTeacher = teacherRepository.findBindedTeacher();
    bindedTeacher.forEach(teacher -> {
      AppData appData = new AppData();
      appData.setTeacherId(teacher.getOid());
      appData.setPaperId(teacher.getPaperId());
      String courseId = teacher.getCourseId();//获取教师所教课程的id
      List<String> classByCourseId = xuanKeRepository.findClassByCourseId(courseId);//通过课程id找到上这门课的班级id
      List<String> students = new ArrayList<>();
      classByCourseId.forEach(aClass -> {//将每个班的学生id取出，放到教师对应的集合里
        List<String> allStudentIdInClass = studentRepository.findAllStudentIdInClass(aClass);
        students.addAll(allStudentIdInClass);
      });
      appData.setStudentIds(students);
      //存放以教师id为引，学生列表为值的数据
      servletContext.setAttribute(teacher.getOid(), students);
    });
  }
  @Data
  static class AppData {

    private String teacherId;
    private String paperId;
    private List<String> studentIds;
  }
}


