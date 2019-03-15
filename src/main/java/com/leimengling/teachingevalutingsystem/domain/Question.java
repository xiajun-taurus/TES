package com.leimengling.teachingevalutingsystem.domain;

import lombok.Data;

/**
 * Created by Lei MengLing. 题目实体类
 */
@Data
public class Question {

  //内码
  private String oid;
  //问题
  private String question;
  //答案A
  private String answerA;
  //答案B
  private String answerB;
  //答案C
  private String answerC;
  //答案D
  private String answerD;
  //A项权重
  private Integer scoreA;
  //B项权重
  private Integer scoreB;
  //C项权重
  private Integer scoreC;
  //D项权重
  private Integer scoreD;
}
