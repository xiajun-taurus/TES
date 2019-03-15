package com.leimengling.teachingevalutingsystem.service;

import com.leimengling.teachingevalutingsystem.domain.Teacher;
import java.util.List;

/**
 * Created by Lei MengLing.
 */
public interface TeacherService {
  int bindPaper(String teacherId,String paperId);
  List<? extends Teacher> findAllTeacher();
  int addTeacher(Teacher teacher);
  Teacher findTeacherById(String id);
  List<String> findAllTeacherIdByPaperId(String paperId);
}
