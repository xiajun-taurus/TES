package com.leimengling.teachingevalutingsystem.domain;

import lombok.Data;

/**
 * Created by Lei MengLing.
 * 教师信息类
 */
@Data
public class Teacher {

  /**
   * 教师对应的用户id
   */
  private String oid;
  /**
   * 所授课程id
   */
  private String courseId;
  /**
   * 对应问卷id
   */
  private String paperId;
}
