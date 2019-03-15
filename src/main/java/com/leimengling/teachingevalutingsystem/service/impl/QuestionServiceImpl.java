package com.leimengling.teachingevalutingsystem.service.impl;

import com.leimengling.teachingevalutingsystem.domain.Question;
import com.leimengling.teachingevalutingsystem.repository.QuestionRepository;
import com.leimengling.teachingevalutingsystem.service.QuestionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lei MengLing.
 */
@Service
public class QuestionServiceImpl implements QuestionService {
  @Autowired
  private QuestionRepository questionRepository;

  /**
   * 查找所有题目
   * @return 题目列表
   */
  @Override
  public List<Question> findAllQuestion() {
    return questionRepository.findAllQuestions();
  }

  @Override
  public int updateQuestion(Question question) {
    return questionRepository.updateQuestion(question);
  }

  @Override
  public Question getAQuestion(String oid) {
    return questionRepository.findAQuestion(oid);
  }

  @Override
  public int deleteQuestion(String oid) {
    return questionRepository.deleteAQuestion(oid);
  }

  @Override
  public int addQuestion(Question question) {
    return questionRepository.addQuestion(question);
  }
}
