package com.leimengling.teachingevalutingsystem.service;

import com.leimengling.teachingevalutingsystem.domain.ClassInfo;
import java.util.List;

/**
 * Created by Lei MengLing.
 */
public interface ClassInfoService {

  /**
   * 新增课程信息
   * @param className 课程名称
   * @return 受影响行数
   */
  int addClass(String className);

  /**
   * c查询所有班级信息
   * @return 班级信息列表，附加信息
   */
  List<? extends ClassInfo> getAllClass();

  /**
   * 删除班级信息
   * @param oid 要删除的班级id
   * @return 受影响行数
   */
  int deleteClass(String oid);

  /**
   * 更新班级信息
   * @return 受影响行数
   */
  int updateClass(String oid,String className);

  ClassInfo getClass(String oid);
}
