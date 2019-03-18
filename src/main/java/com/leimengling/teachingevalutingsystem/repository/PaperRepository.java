package com.leimengling.teachingevalutingsystem.repository;

import com.leimengling.teachingevalutingsystem.domain.Paper;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * Created by Lei MengLing.
 */
@Mapper
public interface PaperRepository {

  /**
   * 查询所有试卷
   * @return
   */
  @Select("select * from papers where enable_status = 1")
  List<Paper> findAllPapers();

  /**
   * 返回所有试题id
   * @return
   */
  @Select("select oid from papers where enable_status = 1")
  List<String> findAllPapersId();


  /**
   * 根据编号查询问卷
   * @param oid
   * @return Paper对象
   */
  @Select("select * from papers where oid = #{oid}")
  Paper findPaperById(String oid);

  /**
   * 根据编号删除试卷
   * @param oid
   * @return 受影响行数
   */
  @Update("update papers set enable_status = 0 where oid  =#{oid}")
  int deletePaper(String oid);

  /**
   * 根据传来的paper对象更新数据库
   * @param paper
   * @return
   */
  @UpdateProvider(type = com.leimengling.teachingevalutingsystem.provider.UpdateProvider.class,method = "PaperUpdateProvider")
  int updatePaper(Paper paper);

  /**
   * 根据id更新对应问卷的问题
   * @param oid
   * @param questions
   * @return 受影响的行数
   */
  @Update("update papers set questions = #{questions} where oid = #{oid}")
  int updatePaperQuestions(@Param("oid") String oid,@Param("questions") String questions);

  /**
   * 根据id查询对应问卷的问题
   * @param oid
   * @return 问卷包含的问题id
   */
  @Select("select questions from papers where oid = #{oid}")
  String findQuestionsInPaper(String oid);

  /**
   * 插入一条问卷信息
   * @param paper
   * @return 受影响行数
   */
  @SelectKey(keyProperty = "oid", before = true, resultType = String.class, statement = "select replace(uuid(),'-','') as id from dual")
  @Insert("insert into papers(oid,paper_title) values(#{oid},#{paperTitle})")
  int insertPaper(Paper paper);
}
