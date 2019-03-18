package com.leimengling.teachingevalutingsystem.service.impl;

import com.leimengling.teachingevalutingsystem.domain.ClassInfo;
import com.leimengling.teachingevalutingsystem.repository.ClassInfoRepository;
import com.leimengling.teachingevalutingsystem.repository.StudentRepository;
import com.leimengling.teachingevalutingsystem.service.ClassInfoService;
import com.leimengling.teachingevalutingsystem.utils.ClassUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lei MengLing.
 */
@Service
public class ClassInfoServiceImpl implements ClassInfoService {
  @Autowired
  private ClassInfoRepository classInfoRepository;
  @Autowired
  private StudentRepository studentRepository;
  @Override
  public int addClass(String className) {
    return classInfoRepository.insertClassName(className);
  }

  @Override
  public List<? extends ClassInfo> getAllClass() {
    //内部类，添加班级人数字段
    @lombok.Data
    class Data extends ClassInfo{
      private int count;
    }
    //查询所有班级信息
    List<ClassInfo> allClass = classInfoRepository.findAllClass();
    List<Data> datas = new ArrayList<>();
    //遍历班级列表，对每一个班级，查询出该班级内所有学生，求出学生人数，赋给count
    for(ClassInfo classInfo:allClass){
      Data data = new Data();
      try {
        ClassUtils.fatherToChild(classInfo,data);
      } catch (Exception e) {
        e.printStackTrace();
      }
      List<String> allStudentIdInClass = studentRepository
          .findAllStudentIdInClass(classInfo.getOid());
      data.setCount(allStudentIdInClass.size());
      datas.add(data);
    }
    return datas;
  }

  @Override
  public int deleteClass(String oid) {
    return classInfoRepository.deleteClassInfo(oid);
  }

  @Override
  public ClassInfo getClass(String oid) {
    return classInfoRepository.findClassInfoById(oid);
  }

  @Override
  public int updateClass(String oid,String className) {
    return classInfoRepository.updateClassName(oid,className);
  }
}
