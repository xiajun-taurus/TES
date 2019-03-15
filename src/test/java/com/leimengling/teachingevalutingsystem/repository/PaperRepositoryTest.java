package com.leimengling.teachingevalutingsystem.repository;

import static org.junit.Assert.*;

import org.junit.Assert;
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
public class PaperRepositoryTest {
  @Autowired
  private PaperRepository paperRepository;

  @Test
  public void findAllPapers() {
  }

  @Test
  public void findPaperById() {
  }

  @Test
  public void deletePaper() {
  }

  @Test
  public void updatePaper() {
  }

  @Test
  public void updatePaperQuestions() {
    int wne = paperRepository.updatePaperQuestions("123", "wne");
    Assert.assertEquals(1,wne);
  }

  @Test
  public void findQuestionsInPaper() {
  }
}