package com.leimengling.teachingevalutingsystem.repository;

import com.leimengling.teachingevalutingsystem.domain.User;
import org.apache.ibatis.annotations.*;


/**
 * Created by Lei MengLing.
 */
@Mapper
public interface UserRepository{

  /**
   * 通过用户名查询用户
   * @param userName 用户名
   * @return 用户实体类
   */
  @Select("select * from user where username = #{userName}")
  User findByUserName(String userName);

  /**
   * 根据学工号查询用户
   */
  @Select("select * from user where school_no = #{schoolNo}")
  User findByShchoolNo(String schoolNo);

  /**
   * 插入一条用户信息
   * @param user
   * @return 受影响行数
   */
  @Insert("insert into `teaching_evaluting_system`.`user` ( `oid`, `school_no`, `username`, `truename`, `email`, `signature`, `avatar`, `role`, `security_question`, `security_answer`, `phone`) values ( #{oid}, #{schoolNo}, #{userName}, #{trueName}, #{eMail}, #{signature}, #{avatar}, #{role}, #{securityQuestion}, #{securityAnswer}, #{phone});")
  @SelectKey(keyProperty = "oid", before = true, resultType = String.class, statement = "select replace(uuid(),'-','') as id from dual")
  int insertUser(User user);

  /**
   * 根据id查询一个用户信息
   * @param id
   * @return 用户信息对象
   */
  @Select("select * from user where oid = #{id}")
  User findUserById(String id);

  /**
   * 根据id删除一个用户信息
   * @param oid
   * @return 受影响行数
   */
  @Delete("delete from user where oid = #{oid}")
  int deleteUser(String oid);

  /**
   * 根据user对象更新一个用户信息
   * @param user
   * @return 受影响行数
   */
  @UpdateProvider(type = com.leimengling.teachingevalutingsystem.provider.UpdateProvider.class,method = "UserProvider")
  int updateUser(User user);

  /**
   * 更新用户密保问题及答案
   * @param question 用户密保问题id
   * @param answer 密保问题答案
   * @param oid 用户id
   * @return 受影响行数
   */
  @Update("update user set security_question=#{question} ,security_answer=#{answer} where oid=#{oid}")
  int updateUserSecurityQuestion(@Param("question") String question,@Param("answer") String answer,@Param("oid") String oid);


}
