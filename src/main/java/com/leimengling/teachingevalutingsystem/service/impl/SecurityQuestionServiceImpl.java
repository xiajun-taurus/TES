package com.leimengling.teachingevalutingsystem.service.impl;

import com.leimengling.teachingevalutingsystem.domain.SecurityQuestion;
import com.leimengling.teachingevalutingsystem.repository.SecurityQuestionRepository;
import com.leimengling.teachingevalutingsystem.service.SecurityQuestionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lei MengLing.
 */
@Service
public class SecurityQuestionServiceImpl implements SecurityQuestionService {
  @Autowired
  private SecurityQuestionRepository securityQuestionRepository;

  @Override
  public int addSQ(SecurityQuestion question) {
    return securityQuestionRepository.insertSecurityQuestion(question);
  }

  @Override
  public List<SecurityQuestion> showAllQ() {
    return securityQuestionRepository.findAllSecurityQuestions();
  }
}
