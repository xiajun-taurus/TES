package com.leimengling.teachingevalutingsystem.repository;

import com.leimengling.teachingevalutingsystem.domain.SecurityQuestion;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

/**
 * Created by Lei MengLing.
 */
@Mapper
public interface SecurityQuestionRepository {

  /**
   * 查询所有密保问题
   */
  @Select("select * from security_question")
  List<SecurityQuestion> findAllSecurityQuestions();

  /**
   * 插入一个密保问题
   *
   * @param question 待添加问题
   * @return 受影响行数
   */
  @Insert("insert into security_question(oid,question) values(#{oid},#{question})")
  @SelectKey(keyProperty = "oid", before = true, resultType = String.class, statement = "select replace(uuid(),'-','') as id from dual")
  int insertSecurityQuestion(SecurityQuestion question);
}
