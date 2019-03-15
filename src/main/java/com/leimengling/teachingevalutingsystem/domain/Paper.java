package com.leimengling.teachingevalutingsystem.domain;

import java.util.Date;
import lombok.Data;

/**
 * Created by Lei MengLing. 问卷实体类
 */
@Data
public class Paper {

  //试卷编码
  private String oid;
  //试卷标题
  private String paperTitle;
  //卷内包含的试题编号（逗号隔开）
  private String questions;
  //上次修改时间
  private Date lastEditTime;
  //是否有效
  private int enableStatus;

}
