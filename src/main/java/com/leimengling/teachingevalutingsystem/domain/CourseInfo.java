package com.leimengling.teachingevalutingsystem.domain;

import java.util.Date;
import lombok.Data;

/**
 * Created by Lei MengLing.课程信息类
 */
@Data
public class CourseInfo {

  private String oid;
  private Date lastEditTime;
  private String courseName;
}
