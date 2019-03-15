package com.leimengling.teachingevalutingsystem.repository;

import com.leimengling.teachingevalutingsystem.domain.MajorInfo;
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
public interface MajorInfoRepository {

  /**
   * 查询所有专业
   */
  @Select("select * from major_info")
  List<MajorInfo> findAllMajor();

  /**
   * 根据id查询对应专业信息
   *
   * @param oid id
   * @return 专业信息
   */
  @Select("select * from major_info where oid = #{oid}")
  MajorInfo findMajorById(String oid);

  /**
   * 插入一条专业信息
   *
   * @param majorInfo 待添加专业
   * @return 受影响行数
   */
  @Insert("insert into major_info(oid,major_name) values(#{oid},#{majorName})")
  @SelectKey(keyProperty = "oid", before = true, resultType = String.class, statement = "select replace(uuid(),'-','') as id from dual")
  int insertMajorInfo(MajorInfo majorInfo);

  /**
   * 更新专业名称
   *
   * @param oid 专业内码
   * @param majorName 新专业名称
   * @return 受影响行数
   */
  @Update("update major_info set major_name = #{majorName} where oid = #{oid}")
  int updateMajorName(@Param("oid") String oid, @Param("majorName") String majorName);

  /**
   * 根据专业内码删除相应专业信息
   *
   * @param oid 专业内码
   * @return 受影响行数
   */
  @Delete("delete from major_info where oid = #{oid}")
  int deleteMajorInfo(String oid);

  /**
   * 查询本专业学生人数
   *
   * @param oid 专业id
   * @return 人数
   */
  @Select("select count(1) from students where major_id = #{oid}")
  int countMajor(String oid);
}
