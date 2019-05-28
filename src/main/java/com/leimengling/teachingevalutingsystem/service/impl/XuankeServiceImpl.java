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
  public int delXuankeInfo(String id) {

    return xuanKeRepository.deleteXuankeInfo(id);
  }

  @Override
  public List<? extends XuanKe> findAllXuanKeInfo() {
    //创建内部类，继承并新增班级名称和课程名称
    @lombok.Data
    class Data extends XuanKe {

      private String className;
      private String courseName;
    }
    //查询所有的选课信息
    List<? extends XuanKe> allXuanKeInfo = xuanKeRepository.findAllXuankeInfo();
    //创建空列表准备装数据
    List<Data> datas = new ArrayList<>();
//    遍历选课信息，并查询出班级名称，课程名称装到内部类，然后放到新创建的列表里
    for (XuanKe xuanKe : allXuanKeInfo) {
      ClassInfo classInfoById = classInfoRepository.findClassInfoById(xuanKe.getClassId());
      CourseInfo courseInfoById = courseInfoRepository.findCourseInfoById(xuanKe.getCourseId());
      Data data = new Data();
      try {
        ClassUtils.fatherToChild(xuanKe, data);
      } catch (Exception e) {
        e.printStackTrace();
      }

      data.setClassName(classInfoById.getClassName());
      data.setCourseName(courseInfoById.getCourseName());
      datas.add(data);
    }
    return datas; //返回带有班级名称，课程名称的列表
  }
}
