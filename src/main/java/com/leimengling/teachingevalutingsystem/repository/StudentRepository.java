package com.leimengling.teachingevalutingsystem.repository;

import com.leimengling.teachingevalutingsystem.domain.Student;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Lei MengLing.
 */
@Mapper
public interface StudentRepository {

  /**
   * 通过userId查找学生信息
   *
   * @param userId 用户表id
   * @return 学生对象
   */
  @Select("select * from students where oid = #{userId}")
  Student findByOid(String userId);

  /**
   * 获取班级内所有学生id
   */
  @Select("select oid from students where class_id = #{classId}")
  List<String> findAllStudentIdInClass(String classId);

  /**
   * 获取所有学生信息
   */
  @Select("select * from students")
  List<Student> findAllStudents();

  /**
   * 插入一个学生信息
   * @param student
   * @return 受影响行数
   */
  @Insert("insert into students(oid,class_id,major_id) values(#{oid},#{classId},#{majorId})")
  int insertStudent(Student student);

  /**
   * 根据id在学生表里删除信息
   * @param oid
   * @return 受影响行数
   */
  @Delete("delete from students where oid = #{oid}")
  int deleteStudent(String oid);

}
