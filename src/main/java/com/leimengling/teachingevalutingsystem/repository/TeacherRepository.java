package com.leimengling.teachingevalutingsystem.repository;

import com.leimengling.teachingevalutingsystem.domain.Teacher;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Lei MengLing.
 */
@Mapper
public interface TeacherRepository {

  @Update("update teacher set paper_id = #{paperId} where oid = #{teacherId}")
  int bindPaper(@Param("teacherId") String teacherId, @Param("paperId") String paperId);

  @Select("select * from teacher")
  List<Teacher> findAllTeacher();

  @Select("select * from teacher where oid = #{id}")
  Teacher findTeacherById(String id);

  /**
   * 给定paperId返回所有教师id：查询所有使用此套问卷的老师
   */
  @Select("select oid from teacher where paper_id =#{paperId}")
  List<String> findAllTeacherIdByPaperId(String paperId);

  /**
   * 根据paperId返回所有课程id（一个教师对应一个课程？）
   */
  @Select("select course_id from teacher where paper_id = #{paperId}")
  List<String> findAllCourseIdByPaperId(String paperId);

  /**
   * 根据teacherId返回所有课程id（一个教师对应多个课程？）
   */
  @Select("select course_id from teacher where oid = #{teacherId}")
  List<String> findAllCourseIdByTeacherId(String teacherId);

  /**
   * 新增教师
   */
  @Insert("insert into teacher(oid,course_id,paper_id) values(#{oid},#{courseId},#{paperId})")
  int insertTeacher(Teacher teacher);

  /**
   * 获取已经绑定试卷的教师列表
   */
  @Select("select * from teacher where paper_id is not null")
  List<Teacher> findBindedTeacher();
}
