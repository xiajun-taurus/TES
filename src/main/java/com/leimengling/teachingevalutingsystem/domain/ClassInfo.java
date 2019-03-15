package com.leimengling.teachingevalutingsystem.domain;

import lombok.Data;

/**
 * Created by Lei MengLing.
 * 班级信息类
 */
@Data
public class ClassInfo {

  /**
   * 班级id
   */
  private String oid;
  /**
   * 班级名称
   */
  private String className;
}
