package com.leimengling.teachingevalutingsystem.domain;

import lombok.Data;

/**
 * Created by Lei MengLing. 选课信息
 */
@Data
public class XuanKe {
  //选课信息id
  private String oid;
  //班级id
  private String classId;
  //课程ID
  private String courseId;

}
