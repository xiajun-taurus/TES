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

  @Insert("insert into `teaching_evaluting_system`.`comment_result` ( `oid`, `teacher_id`, `class_id`, `paper_id`, `aver_score`, `comment`, `create_time`) values ( #{oid}, #{teacherId}, #{classId}, #{paperId}, #{averScore}, #{comment}, #{createTime});")
  @SelectKey(keyProperty = "oid", before = true, resultType = String.class, statement = "select replace(uuid(),'-','') as id from dual")
  int insertCommentResult(CommentResult commentResult);

  @Select("select * from comment_result")
  List<CommentResult> findAllResults();

  @UpdateProvider(type = com.leimengling.teachingevalutingsystem.provider.UpdateProvider.class, method = "CommentResultProvider")
  int updateResult(CommentResult result);

  @Select("select oid from comment_result where teacher_id= #{teacherId}"
      + "and paper_id=#{paperId}")
  String getCommentIdByTeacherIdAndPaperId(@Param("teacherId") String teacherId,
      @Param("paperId") String paperId);

  @Select("select * from comment_result where oid = #{id}")
  CommentResult getResultById(String id);

}
