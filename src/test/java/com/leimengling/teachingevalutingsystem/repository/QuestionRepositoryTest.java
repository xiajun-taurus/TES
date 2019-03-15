package com.leimengling.teachingevalutingsystem.repository;

import com.leimengling.teachingevalutingsystem.domain.Question;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Lei MengLing.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionRepositoryTest {
  @Autowired
  private QuestionRepository questionRepository;
  @Test
  @Ignore
  public void addQuestion() {
    Question question = new Question();
    question.setQuestion("上课表现");
    question.setAnswerA("非常好");
    question.setAnswerB("好");
    question.setAnswerC("一般");
    question.setAnswerD("差");
    question.setScoreA(3);
    question.setScoreB(2);
    question.setScoreC(1);
    question.setScoreD(0);
    int i = questionRepository.addQuestion(question);
    Assert.assertEquals(1,i);
  }
  @Test
  @Ignore
  public void deleteQuestion(){
    int count = questionRepository
        .deleteAQuestion("b1a0f6d2054e11e989b02dec18b133e8");
    Assert.assertEquals(1,count);
  }

  @Test
  public void updateQuestion(){
    Question question = new Question();
    question.setOid("1407689e05b611e989b02dec18b133e8");
    question.setQuestion("我-_-");

    int i = questionRepository.updateQuestion(question);
    Assert.assertEquals(1,i);
  }
}