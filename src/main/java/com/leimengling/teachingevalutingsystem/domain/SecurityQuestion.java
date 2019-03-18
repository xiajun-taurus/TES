package com.leimengling.teachingevalutingsystem.domain;

import lombok.Data;

/**
 * Created by Lei MengLing. 安全问题
 */
@Data
public class SecurityQuestion {
  //密保问题id
  private String oid;
  //密保问题
  private String question;
}
