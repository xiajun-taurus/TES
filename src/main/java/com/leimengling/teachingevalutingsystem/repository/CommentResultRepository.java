package com.leimengling.teachingevalutingsystem.repository;

import com.leimengling.teachingevalutingsystem.domain.CommentResult;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * Created by Lei MengLing.
 */
@Mapper
public interface CommentResultRepository {

  /**
   * 插入一个评论结果
   * @param commentResult 评论结果对象
   * @return 受影响行数
   */
  @Insert("insert into `teaching_evaluting_system`.`comment_result` ( `oid`, `teacher_id`, `paper_id`, `aver_score`, `comment`, `create_time`) values ( #{oid}, #{teacherId}, #{paperId}, #{averScore}, #{comment}, #{createTime});")
  @SelectKey(keyProperty = "oid", before = true, resultType = String.class, statement = "select replace(uuid(),'-','') as id from dual")
  int insertCommentResult(CommentResult commentResult);

  /**
   * 查询所有评教结果信息
   * @return 评教结果信息列表
   */
  @Select("select * from comment_result")
  List<CommentResult> findAllResultsByAdmin();


  /**
   * 查询对应教师所有评教结果信息
   * @return 评教结果信息列表
   */
  @Select("select * from comment_result where teacher_id = #{id}")
  List<CommentResult> findAllResults(String id);

  /**
   * 更新评教结果
   * @param result
   * @return 受影响行数
   */
  @UpdateProvider(type = com.leimengling.teachingevalutingsystem.provider.UpdateProvider.class, method = "CommentResultProvider")
  int updateResult(CommentResult result);

  /**
   * 根据teacherid和paperid查询一个评教结果
   * @param teacherId
   * @param paperId
   * @return
   */
  @Select("select oid from comment_result where teacher_id= #{teacherId}"
      + "and paper_id=#{paperId}")
  String getCommentIdByTeacherIdAndPaperId(@Param("teacherId") String teacherId,
      @Param("paperId") String paperId);

  @Select("select * from comment_result where oid = #{id}")
  CommentResult getResultById(String id);

}
