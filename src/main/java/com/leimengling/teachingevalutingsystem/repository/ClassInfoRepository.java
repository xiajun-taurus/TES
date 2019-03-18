package com.leimengling.teachingevalutingsystem.repository;

import com.leimengling.teachingevalutingsystem.domain.ClassInfo;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
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
public interface ClassInfoRepository {

  /**
   * 插入班级信息
   *
   * @param className 班级名称
   * @return 影响行数
   */
  @Insert({"insert into classinfo(oid,classname) values(#{id},#{className})"})
  @SelectKey(keyProperty = "id", before = true, resultType = String.class, statement = "select replace(uuid(),'-','') as id from dual")
  int insertClassName(@Param("className") String className);

  /**
   * 查询所有班级信息
   *
   * @return 班级信息列表
   */
  @Select("select * from classinfo")
  List<ClassInfo> findAllClass();

  /**
   * 通过班级id找到班级信息
   *
   * @return classInfo 实体类
   */
  @Select("select * from classinfo where oid = #{oid}")
  ClassInfo findClassInfoById(String oid);

  /**
   * 通过班级id删除班级信息
   *
   * @return 受影响行数
   */
  @Delete("delete from classinfo where oid =#{oid}")
  int deleteClassInfo(String oid);

  /**
   * 修改班级名称
   */
  @Update("update classinfo set classname = #{className} where oid = #{oid}")
  int updateClassName(@Param("oid") String oid, @Param("className") String className);
}
