package com.leimengling.teachingevalutingsystem.service;

import com.leimengling.teachingevalutingsystem.domain.Question;
import java.util.List;

/**
 * Created by Lei MengLing.
 */
public interface QuestionService {

  /**
   * 添加一个问题
   * @param question 问题对象
   * @return 受影响的行数
   */
  int addQuestion(Question question);

  /**
   * 查询所有问题
   * @return List<Question>
   */
  List<Question> findAllQuestion();

  /**
   * 根据oid查询对应问题
   * @param oid
   * @return Question对象
   */
  Question getAQuestion(String oid);

  /**
   * 根据oid删除问题
   * @param oid
   * @return 受影响行数
   */
  int deleteQuestion(String oid);

  /**
   * 根据传入的对象进行更新
   * @param question
   * @return 受影响行数
   */
  int updateQuestion(Question question);
}
