package com.leimengling.teachingevalutingsystem.repository;

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
public class ClassInfoRepositoryTest {
  @Autowired
  private ClassInfoRepository classInfoRepository;

  @Test
  public void testInsert() {
    int i = classInfoRepository.insertClassName("计算154");
    Assert.assertEquals(1, i);
  }
}