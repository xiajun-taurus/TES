package com.leimengling.teachingevalutingsystem.repository;

import com.leimengling.teachingevalutingsystem.domain.XuanKe;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

/**
 * Created by Lei MengLing.
 */
@Mapper
public interface XuanKeRepository {

  @Select("select class_id from xuanke where course_id =#{courseId}")
  List<String> findClassByCourseId(String courseId);

  @Insert("insert into xuanke(oid,course_id,class_id) values(#{oid},#{courseId},#{classId})")
  @SelectKey(keyProperty = "oid", before = true, resultType = String.class, statement = "select replace(uuid(),'-','') as id from dual")
  int insertXuanke(XuanKe xuanKe);

  @Select("select * from xuanke")
  List<XuanKe> findAllXuankeInfo();
}
