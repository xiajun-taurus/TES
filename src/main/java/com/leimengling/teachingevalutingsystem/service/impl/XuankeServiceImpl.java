package com.leimengling.teachingevalutingsystem.service.impl;

import com.leimengling.teachingevalutingsystem.domain.ClassInfo;
import com.leimengling.teachingevalutingsystem.domain.CourseInfo;
import com.leimengling.teachingevalutingsystem.domain.XuanKe;
import com.leimengling.teachingevalutingsystem.repository.ClassInfoRepository;
import com.leimengling.teachingevalutingsystem.repository.CourseInfoRepository;
import com.leimengling.teachingevalutingsystem.repository.XuanKeRepository;
import com.leimengling.teachingevalutingsystem.service.XuankeService;
import com.leimengling.teachingevalutingsystem.utils.ClassUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lei MengLing.
 */
@Service
public class XuankeServiceImpl implements XuankeService {
  @Autowired
  private XuanKeRepository xuanKeRepository;
  @Autowired
  private ClassInfoRepository classInfoRepository;
  @Autowired
  private CourseInfoRepository courseInfoRepository;
  @Override
  public int addXuankeInfo(XuanKe xuanKe) {
    return xuanKeRepository.insertXuanke(xuanKe);
  }

  @Override
  public List<? extends XuanKe> findAllXuanKeInfo() {
    @lombok.Data
    class Data extends XuanKe{
      private String className;
      private String courseName;
    }
    List<? extends XuanKe> allXuanKeInfo = xuanKeRepository.findAllXuankeInfo();
    List<Data> datas = new ArrayList<>();
    for (XuanKe xuanKe:allXuanKeInfo){
      ClassInfo classInfoById = classInfoRepository.findClassInfoById(xuanKe.getClassId());
      CourseInfo courseInfoById = courseInfoRepository.findCourseInfoById(xuanKe.getCourseId());
      Data data = new Data();
      try {
        ClassUtils.fatherToChild(xuanKe,data);
      } catch (Exception e) {
        e.printStackTrace();
      }
      data.setClassName(classInfoById.getClassName());
      data.setCourseName(courseInfoById.getCourseName());
      datas.add(data);
    }
    return datas;
  }
}
