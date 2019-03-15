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
public class TeacherRepositoryTest {
  @Autowired
  private TeacherRepository teacherRepository;
  @Test
  public void bindPaper() {
    int i = teacherRepository.bindPaper("123", "456");
    Assert.assertEquals(1,i);
  }
}