package com.leimengling.teachingevalutingsystem.service;

import com.leimengling.teachingevalutingsystem.VO.StudentVO;
import com.leimengling.teachingevalutingsystem.domain.Student;
import com.leimengling.teachingevalutingsystem.service.impl.StudentServiceImpl.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lei MengLing.
 */
public interface StudentService {
  Student findStudentById(String oid);

  List<? extends Student> findAllStudents();

  int addStudent(StudentVO studentVO);

  int delStudent(String oid);

  ArrayList<Data> getCommentingPapers(String stuOid);
}
