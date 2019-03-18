package com.leimengling.teachingevalutingsystem.domain;

import java.util.Date;
import lombok.Data;

/**
 * Created by Lei MengLing.课程信息类
 */
@Data
public class CourseInfo {
  //课程id
  private String oid;
  //上次更新时间
  private Date lastEditTime;
  //课程名称
  private String courseName;
}
