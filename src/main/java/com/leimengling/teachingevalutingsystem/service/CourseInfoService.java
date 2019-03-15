package com.leimengling.teachingevalutingsystem.service;

import com.leimengling.teachingevalutingsystem.domain.CourseInfo;
import java.util.List;

/**
 * Created by Lei MengLing.
 */
public interface CourseInfoService {

  List<? extends CourseInfo> findAllCourseInfo();

  int updateCourseName(String oid, String courseName);

  CourseInfo findCourseById(String oid);

  int addCourse(CourseInfo courseInfo);

}
