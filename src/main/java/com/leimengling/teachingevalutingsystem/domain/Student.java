package com.leimengling.teachingevalutingsystem.domain;

import javax.persistence.Id;
import lombok.Data;

/**
 * Created by Lei MengLing.
 * 学生实体类
 */
@Data
public class Student {

  /**
   * id(与user表中id对应)
   */
  @Id
  private String oid;
  /**
   * 专业id（与major_info表中id对应）
   */

  private String majorId;
  /**
   * 班级id（与class_info表中id对应）
   */

  private String classId;

}
