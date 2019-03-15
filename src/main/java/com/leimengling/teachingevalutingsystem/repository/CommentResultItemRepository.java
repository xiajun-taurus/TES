package com.leimengling.teachingevalutingsystem.repository;

import com.leimengling.teachingevalutingsystem.domain.CommentResultItem;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

/**
 * Created by Lei MengLing.
 */
@Mapper
public interface CommentResultItemRepository {
  @Insert("insert into `teaching_evaluting_system`.`comment_result_item` ( `oid`, `score`, `comment`, `result_id`, `commenter_id`) "
      + "values ( #{oid}, #{score}, #{comment}, #{resultId}, #{commenterId});")
  @SelectKey(
      keyProperty = "oid",
      before = true,
      resultType = String.class,
      statement = "select replace(uuid(),'-','') as id from dual"
  )
  int insertCommentResultItem(CommentResultItem resultItem);

  @Select("select count(1) from comment_result_item where result_id = #{id}")
  int countItemByResultId(String id);

  @Select("select commenter_id from comment_result_item where result_id = #{resultId}")
  List<String> getAllCommenterId(String resultId);

}
