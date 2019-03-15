package com.leimengling.teachingevalutingsystem.service.impl;

import com.leimengling.teachingevalutingsystem.domain.CourseInfo;
import com.leimengling.teachingevalutingsystem.repository.CourseInfoRepository;
import com.leimengling.teachingevalutingsystem.service.CourseInfoService;
import com.leimengling.teachingevalutingsystem.utils.ClassUtils;
import com.leimengling.teachingevalutingsystem.utils.DateUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lei MengLing.
 */
@Service
public class CourseInfoServiceImpl implements CourseInfoService {
@Autowired
  private CourseInfoRepository courseInfoRepository;

  @Override
  public List<? extends CourseInfo> findAllCourseInfo() {
    @lombok.Data
    class Data extends CourseInfo{
      private String time;
    }
    List<CourseInfo> allCourseInfo = courseInfoRepository.findAllCourseInfo();
    ArrayList<Data> dataList = new ArrayList<>();
    for (CourseInfo courseInfo:allCourseInfo){
      Data data = new Data();
      Date lastEditTime = courseInfo.getLastEditTime();
      try {
        ClassUtils.fatherToChild(courseInfo,data);
      } catch (Exception e) {
        e.printStackTrace();
      }
      data.setTime(DateUtils.defaultFomat(lastEditTime));
      dataList.add(data);
    }
    return dataList;
  }

  @Override
  public int updateCourseName(String oid, String courseName) {
    return courseInfoRepository.updateCourseName(oid, courseName);
  }

  @Override
  public CourseInfo findCourseById(String oid) {
    return courseInfoRepository.findCourseInfoById(oid);
  }

  @Override
  public int addCourse(CourseInfo courseInfo) {
    return courseInfoRepository.insertCourse(courseInfo);
  }
}
