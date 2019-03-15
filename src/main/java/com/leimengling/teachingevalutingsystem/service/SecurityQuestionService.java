package com.leimengling.teachingevalutingsystem.service;

import com.leimengling.teachingevalutingsystem.domain.SecurityQuestion;
import java.util.List;

/**
 * Created by Lei MengLing.
 */
public interface SecurityQuestionService {

  //添加密保问题
  int addSQ(SecurityQuestion question);

  //查询所有密保问题
  List<SecurityQuestion> showAllQ();
}
