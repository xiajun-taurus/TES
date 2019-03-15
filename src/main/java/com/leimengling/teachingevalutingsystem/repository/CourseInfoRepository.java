package com.leimengling.teachingevalutingsystem.repository;

import com.leimengling.teachingevalutingsystem.domain.CourseInfo;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Lei MengLing.
 */
@Mapper
public interface CourseInfoRepository {

  /**
   * 插入一条课程信息
   *
   * @param courseInfo 课程信息对象
   * @return 受影响的行数
   */
  @Insert("insert into course_info(oid,course_name) values(#{oid},#{courseName})")
  @SelectKey(keyProperty = "oid", before = true, resultType = String.class, statement = "select replace(uuid(),'-','') as id from dual")
  int insertCourse(CourseInfo courseInfo);

  /**
   * 查询所有课程信息
   */
  @Select("select * from course_info")
  List<CourseInfo> findAllCourseInfo();

  /**
   * 根据id查询对应课程
   */
  @Select("select * from course_info where oid = #{id}")
  CourseInfo findCourseInfoById(String id);

  /**
   * 更新课程名称
   *
   * @return 受影响行数
   */
  @Update("update course_info set course_name = #{courseName} where oid = #{oid}")
  int updateCourseName(@Param("oid") String oid, @Param("courseName") String courseName);


}
