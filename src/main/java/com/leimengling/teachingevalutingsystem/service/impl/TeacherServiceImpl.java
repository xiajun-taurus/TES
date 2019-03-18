package com.leimengling.teachingevalutingsystem.service.impl;

import com.leimengling.teachingevalutingsystem.domain.CommentResult;
import com.leimengling.teachingevalutingsystem.domain.CourseInfo;
import com.leimengling.teachingevalutingsystem.domain.Paper;
import com.leimengling.teachingevalutingsystem.domain.Teacher;
import com.leimengling.teachingevalutingsystem.domain.User;
import com.leimengling.teachingevalutingsystem.repository.CommentResultRepository;
import com.leimengling.teachingevalutingsystem.repository.CourseInfoRepository;
import com.leimengling.teachingevalutingsystem.repository.PaperRepository;
import com.leimengling.teachingevalutingsystem.repository.TeacherRepository;
import com.leimengling.teachingevalutingsystem.repository.UserRepository;
import com.leimengling.teachingevalutingsystem.service.TeacherService;
import com.leimengling.teachingevalutingsystem.utils.ClassUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lei MengLing.
 */
@Service
public class TeacherServiceImpl implements TeacherService {

  @Autowired
  private TeacherRepository teacherRepository;
  @Autowired
  private CommentResultRepository commentResultRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private CourseInfoRepository courseInfoRepository;
  @Autowired
  private PaperRepository paperRepository;

  /**
   * 传入教师id和问卷id，进行绑定
   * @param teacherId
   * @param paperId
   * @return 受影响行数
   */
  @Override
  public int bindPaper(String teacherId, String paperId) {
//    先进行绑定
    int bindPaper = teacherRepository.bindPaper(teacherId, paperId);

    if (bindPaper > 0) {
//      绑定成功之后生成一个对应的result
      CommentResult commentResult = new CommentResult();
      commentResult.setPaperId(paperId);
      commentResult.setTeacherId(teacherId);
//      初始化平均分
      commentResult.setAverScore((float) 0);
//      将result放入数据库，后期评教的时候直接对此result进行更新
      int insertCommentResult = commentResultRepository.insertCommentResult(commentResult);
      if (insertCommentResult > 0) {
        return 1;//创建成功。返回1
      } else {
        return 0;
      }
    } else {
      return 0;
    }
  }

  @Override
  public List<? extends Teacher> findAllTeacher() {
    @lombok.Data
    class Data extends Teacher{
      private String teacherName;
      private String courseName;
      private String paperName;
    }
    List<Data> datas = new ArrayList<>();
    List<Teacher> allTeacher = teacherRepository.findAllTeacher();
    allTeacher.forEach(teacher -> {
      Data data = new Data();
      //分别根据用户id和课程id还有试卷id查询各自名称
      User userById = userRepository.findUserById(teacher.getOid());
      CourseInfo courseInfoById = courseInfoRepository.findCourseInfoById(teacher.getCourseId());
      Paper paperById = paperRepository.findPaperById(teacher.getPaperId());
      try {
        ClassUtils.fatherToChild(teacher,data);
      } catch (Exception e) {
        e.printStackTrace();
      }
      data.setTeacherName(userById.getTrueName());
      data.setCourseName(courseInfoById.getCourseName());
      //如果有问卷，设置问卷名称
      if (paperById != null) {
        data.setPaperName(paperById.getPaperTitle());
      }

      datas.add(data);
    });
    return datas;
  }

  @Override
  public int addTeacher(Teacher teacher) {
    return teacherRepository.insertTeacher(teacher);
  }

  @Override
  public Teacher findTeacherById(String id) {
    return teacherRepository.findTeacherById(id);
  }

  @Override
  public List<String> findAllTeacherIdByPaperId(String paperId) {
    return teacherRepository.findAllTeacherIdByPaperId(paperId);
  }
}
