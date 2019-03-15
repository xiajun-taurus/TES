package com.leimengling.teachingevalutingsystem.repository;

import static org.junit.Assert.*;

import com.leimengling.teachingevalutingsystem.domain.Student;
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
public class StudentRepositoryTest {
  @Autowired
  private StudentRepository studentRepository;
  @Test
  public void findByOid() {
    Student student = studentRepository
        .findByOid("fb017e9067bd5c7a0167bd5da8350001");
    System.out.println(student.toString());
    Assert.assertEquals("信管151",student.getClassId());
  }
}