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

  @Insert("insert into `teaching_evaluting_system`.`user` ( `oid`, `school_no`, `username`, `truename`, `email`, `signature`, `avatar`, `role`, `security_question`, `security_answer`, `phone`) values ( #{oid}, #{schoolNo}, #{userName}, #{trueName}, #{eMail}, #{signature}, #{avatar}, #{role}, #{securityQuestion}, #{securityAnswer}, #{phone});")
  @SelectKey(keyProperty = "oid", before = true, resultType = String.class, statement = "select replace(uuid(),'-','') as id from dual")
  int insertUser(User user);

  @Select("select * from user where oid = #{id}")
  User findUserById(String id);

  @Delete("delete from user where oid = #{oid}")
  int deleteUser(String oid);

  @UpdateProvider(type = com.leimengling.teachingevalutingsystem.provider.UpdateProvider.class,method = "UserProvider")
  int updateUser(User user);

  @Update("update user set security_question=#{question} ,security_answer=#{answer} where oid=#{oid}")
  int updateUserSecurityQuestion(@Param("question") String question,@Param("answer") String answer,@Param("oid") String oid);


}
