package com.leimengling.teachingevalutingsystem.service;

import com.leimengling.teachingevalutingsystem.domain.User;

/**
 * Created by Lei MengLing.
 */

public interface UserService {

  /**
   * 添加user
   * @param user
   */
  int addUser(User user);

  /**
   * 根据username获取一个User对象
   * @param userName 用户名
   * @return User对象
   */
  User findUser(String userName);

  /**
   * 根据传入的表单数据更新user
   * @param user 传入的表单数据--被整合成了对象
   */
  int updateUser(User user);

  /**
   * 根据id查找一个user
   * @param oid id
   * @return User 对象
   */
  User findUserById(String oid);

  /**
   * 传入密码更新密码
   * @param newPassword
   * @return 受影响行数
   */
  int updatePassword(String id,String newPassword);

  /**
   * 修改密保问题
   * @param question answer oid
   * @return 受影响行数
   */
  int updateUserSecurityQuestion(String question,String answer,String oid);
}
