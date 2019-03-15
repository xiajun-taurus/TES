package com.leimengling.teachingevalutingsystem.repository;

import com.leimengling.teachingevalutingsystem.domain.Question;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * Created by Lei MengLing.
 */
@Mapper
public interface QuestionRepository {

  /**
   * 传入一个Question类，将其添加到数据库中
   *
   * @return 受影响行数
   */
  @SelectKey(keyProperty = "oid", before = true, resultType = String.class, statement = "select replace(uuid(),'-','') as id from dual")
  @Insert("insert into question_bank(oid,question,answerA,answerB,answerC,answerD,scoreA,scoreB,scoreC,scoreD) values (#{oid},#{question},#{answerA},#{answerB},#{answerC},#{answerD},#{scoreA},#{scoreB},#{scoreC},#{scoreD})")
  int addQuestion(Question question);

  /**
   * 获取数据库中所有题目
   *
   * @return List<Question>列表
   */
  @Select("select * from question_bank")
  List<Question> findAllQuestions();

  /**
   * 根据oid获取对应的题目
   *
   * @return Question对象
   */
  @Select("select * from question_bank where oid = #{oid}")
  Question findAQuestion(String oid);

  /**
   * 根据oid删除一个问题
   *
   * @return 受影响的行数
   */
  @Delete("delete from question_bank where oid = #{oid}")
  int deleteAQuestion(String oid);

  /**
   * 根据传来的question对象更新数据库对应数据
   * @param question
   * @return
   */
  @UpdateProvider(type = com.leimengling.teachingevalutingsystem.provider.UpdateProvider.class,method = "QuestionUpdateProvider")
  int updateQuestion(Question question);

}
