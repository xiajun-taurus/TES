package com.leimengling.teachingevalutingsystem.service.impl;

import com.leimengling.teachingevalutingsystem.VO.StudentVO;
import com.leimengling.teachingevalutingsystem.domain.ClassInfo;
import com.leimengling.teachingevalutingsystem.domain.CommentResult;
import com.leimengling.teachingevalutingsystem.domain.MajorInfo;
import com.leimengling.teachingevalutingsystem.domain.Paper;
import com.leimengling.teachingevalutingsystem.domain.Student;
import com.leimengling.teachingevalutingsystem.domain.Teacher;
import com.leimengling.teachingevalutingsystem.domain.User;
import com.leimengling.teachingevalutingsystem.repository.ClassInfoRepository;
import com.leimengling.teachingevalutingsystem.repository.CommentResultItemRepository;
import com.leimengling.teachingevalutingsystem.repository.CommentResultRepository;
import com.leimengling.teachingevalutingsystem.repository.MajorInfoRepository;
import com.leimengling.teachingevalutingsystem.repository.PaperRepository;
import com.leimengling.teachingevalutingsystem.repository.StudentRepository;
import com.leimengling.teachingevalutingsystem.repository.TeacherRepository;
import com.leimengling.teachingevalutingsystem.repository.UserRepository;
import com.leimengling.teachingevalutingsystem.service.StudentService;
import com.leimengling.teachingevalutingsystem.utils.ClassUtils;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Lei MengLing.
 */
@Service
public class StudentServiceImpl implements StudentService {

  @Autowired
  private WebApplicationContext webApplicationContext;
  @Autowired
  private StudentRepository studentRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ClassInfoRepository classInfoRepository;
  @Autowired
  private MajorInfoRepository majorInfoRepository;
  @Autowired
  private TeacherRepository teacherRepository;
  @Autowired
  private CommentResultRepository resultRepository;
  @Autowired
  private CommentResultItemRepository resultItemRepository;
  @Autowired
  private PaperRepository paperRepository;

  @Override
  public Student findStudentById(String oid) {
    @lombok.Data
    class Data extends Student{
      private String className;
      private String majorName;
    }
    Student byOid = studentRepository.findByOid(oid);
    Data data = new Data();
    try {
      ClassUtils.fatherToChild(byOid,data);
    } catch (Exception e) {
      e.printStackTrace();
    }
    ClassInfo classInfoById = classInfoRepository.findClassInfoById(byOid.getClassId());
    MajorInfo majorById = majorInfoRepository.findMajorById(byOid.getMajorId());
    data.setClassName(classInfoById.getClassName());
    data.setMajorName(majorById.getMajorName());
    return data;
  }

  @Override
  public ArrayList<Data> getCommentingPapers(String stuOid) {
    /**
     * 思路：application每分钟从数据库读取信息：K：教师id V：学生列表
     * 通过教师id查询学生列表，如果学生列表中包含当前登录学生的信息，那么在在页面显示这个教师的问卷
     */
    ServletContext servletContext = webApplicationContext.getServletContext();
    //获取所有有问卷的教师
    List<Teacher> bindedTeacher = teacherRepository.findBindedTeacher();
    ArrayList<StudentServiceImpl.Data> dataList = new ArrayList<>();
    bindedTeacher.forEach(teacher -> {
      String teacherOid = teacher.getOid();
      //获取该教师下所有学生
      List<String> students = (List<String>) servletContext.getAttribute(teacherOid);
      //如果包含此学生,将教师id，姓名，试卷信息放入列表
      if (students.contains(stuOid)) {
        Data data = new Data();
        String paperId = teacher.getPaperId();
        String resultId = resultRepository
            .getCommentIdByTeacherIdAndPaperId(teacherOid, paperId);
        CommentResult resultById = resultRepository.getResultById(resultId);
        Paper paperById = paperRepository.findPaperById(resultById.getPaperId());

        data.setPaper(paperById);
        data.setTeacherId(teacherOid);
        User userById = userRepository.findUserById(teacherOid);
        data.setTeacherName(userById.getTrueName());
        boolean comented = isComented(resultId, stuOid);
        data.setCommented(comented);
        data.setResultId(resultId);
        dataList.add(data);
      }
    });
    return dataList;
  }

  @Override
  public int delStudent(String oid) {
    userRepository.deleteUser(oid);
    return studentRepository.deleteStudent(oid);
  }

  /**
   * 加入学生信息
   *
   * @return 受影响行数
   */
  @Override
  public int addStudent(StudentVO studentVO) {
    User user = new User();
    user.setTrueName(studentVO.getUserName());
    user.setSchoolNo(studentVO.getSchoolNo());
    user.setUserName(studentVO.getSchoolNo());
    user.setRole(2);
    int i = userRepository.insertUser(user);
    if (i > 0) {
      User byUserName = userRepository.findByUserName(studentVO.getSchoolNo());
      Student student = new Student();
      student.setOid(byUserName.getOid());
      student.setClassId(studentVO.getClassId());
      student.setMajorId(studentVO.getMajorId());
      int i1 = studentRepository.insertStudent(student);
      if (i1 > 0) {
        return 1;
      }
    }
    return 0;
  }

  /**
   * 获取所有学生信息，包含姓名班级专业
   */
  @Override
  public List<? extends Student> findAllStudents() {
    @lombok.Data
    class Data extends Student {

      private String studentName;
      private String className;
      private String majorName;
    }

    List<Student> allStudents = studentRepository.findAllStudents();
    ArrayList<Data> datas = new ArrayList<>();
    for (Student student : allStudents) {
      Data data = new Data();
      try {
        ClassUtils.fatherToChild(student, data);
      } catch (Exception e) {
        e.printStackTrace();
      }
      ClassInfo classInfoById = classInfoRepository.findClassInfoById(student.getClassId());
      MajorInfo majorById = majorInfoRepository.findMajorById(student.getMajorId());
      User userById = userRepository.findUserById(student.getOid());

      data.setClassName(classInfoById.getClassName());
      data.setMajorName(majorById.getMajorName());
      data.setStudentName(userById.getTrueName());
      datas.add(data);
    }
    return datas;
  }

  /**
   * 判断学生是否对某个问卷进行答题
   *
   * @param resultId 问卷对应的结果id
   * @param stuOid 学生id
   * @return true：答过
   */
  public boolean isComented(String resultId, String stuOid) {
    List<String> allCommenterId = resultItemRepository.getAllCommenterId(resultId);
    //如果评价详情包含该学生的评价，返回true
    return allCommenterId.contains(stuOid) ? true : false;
  }

  @lombok.Data
  public class Data {

    private String resultId;
    private Paper paper;
    private String teacherId;
    private String teacherName;
    private boolean commented;
  }
}
