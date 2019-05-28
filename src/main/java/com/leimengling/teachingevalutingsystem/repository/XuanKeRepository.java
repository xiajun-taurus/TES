package com.leimengling.teachingevalutingsystem.repository;

import com.leimengling.teachingevalutingsystem.domain.XuanKe;
import java.util.List;

import org.apache.ibatis.annotations.*;

/**
 * Created by Lei MengLing.
 */
@Mapper
public interface XuanKeRepository {

  /**
   * 通过课程id查询选修该课程的班级
   * @param courseId
   * @return 班级id列表
   */
  @Select("select class_id from xuanke where course_id =#{courseId}")
  List<String> findClassByCourseId(String courseId);

  /**
   * 插入一个选课信息
   * @param xuanKe
   * @return 受影响行数
   */
  @Insert("insert into xuanke(oid,course_id,class_id) values(#{oid},#{courseId},#{classId})")
  @SelectKey(keyProperty = "oid", before = true, resultType = String.class, statement = "select replace(uuid(),'-','') as id from dual")
  int insertXuanke(XuanKe xuanKe);

  /**
   * 查询所有选课信息
   * @return 选课信息列表
   */
  @Select("select * from xuanke")
  List<XuanKe> findAllXuankeInfo();

  /**
   * 删除一条选课信息
   */
  @Delete("delete from `teaching_evaluting_system`.`xuanke` where `oid`=#{id} ")
  int deleteXuankeInfo(String id);
}
