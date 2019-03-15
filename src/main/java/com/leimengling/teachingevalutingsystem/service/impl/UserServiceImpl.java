package com.leimengling.teachingevalutingsystem.service.impl;

import com.leimengling.teachingevalutingsystem.domain.User;
import com.leimengling.teachingevalutingsystem.repository.UserRepository;
import com.leimengling.teachingevalutingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by Lei MengLing.
 */
@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User findUser(String userName) {

    return userRepository.findByUserName(userName);
  }

  @Override
  public int addUser(User user) {
    return userRepository.insertUser(user);
  }

  @Override
  public int updatePassword(String id,String newPassword) {
    User user = new User();
    user.setOid(id);
    user.setPassword(newPassword);
    return userRepository.updateUser(user);
  }
  public int updateUserSecurityQuestion(String question, String answer, String oid) {
    return userRepository.updateUserSecurityQuestion(question,answer,oid);
  }
  @Override
  public User findUserById(String oid) {
    return userRepository.findUserById(oid);
  }

  @Override
  public int updateUser(User user) {
    return userRepository.updateUser(user);
  }
}
