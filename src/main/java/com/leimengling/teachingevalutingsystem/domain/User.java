package com.leimengling.teachingevalutingsystem.domain;

import lombok.Data;

/**
 * Created by Lei MengLing. 用户实体类
 */
@Data
public class User {

  /**
   * id
   */
  private String oid;
  /**
   * 学工号
   */
  private String schoolNo;
  /**
   * 用户名
   */
  private String userName;
  /**
   * 真实姓名
   */
  private String trueName;
  /**
   * 密码
   */
  private String password;
  /**
   * email
   */
  private String eMail;
  /**
   * 个性签名
   */
  private String signature;
  /**
   * 头像地址
   */
  private String avatar;
  /**
   * 用户类型：0：管理员；1：教师；2：学生
   */
  private Integer role;
  /**
   * 密保问题id
   */
  private String securityQuestion;
  /**
   * 密保问题答案
   */
  private String securityAnswer;
  /**
   * 手机号
   */
  private String phone;
}

