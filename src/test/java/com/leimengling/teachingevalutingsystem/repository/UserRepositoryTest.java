package com.leimengling.teachingevalutingsystem.repository;

import com.leimengling.teachingevalutingsystem.domain.User;
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
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {
  @Autowired
  private UserRepository userRepository;
  @Test
  @Ignore
  public void findByUserName() {
    User byUserName = userRepository.findByUserName("666666");
    Assert.assertEquals("夏军",byUserName.getTrueName());
  }
  @Test
  public void testInsert(){
    User user = new User();
    user.setUserName("admin");
    user.setRole(0);
    user.setSchoolNo("000000000");
    user.setTrueName("管理员");
    user.setEMail("admin@sdjtu.com");
    int i = userRepository.insertUser(user);
    Assert.assertEquals(1,i);
  }
}