package com.leimengling.teachingevalutingsystem.controller;

import com.leimengling.teachingevalutingsystem.domain.CourseInfo;
import com.leimengling.teachingevalutingsystem.service.CourseInfoService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Lei MengLing.
 */
@Controller
public class CourseInfoController {
  @Autowired
  private CourseInfoService courseInfoService;

  /**
   * 查询所有课程信息（包含名称）放到列表里，返回给前端
   * @param model
   * @return 课程信息列表
   */
  @RequestMapping("list_course")
  public String show(Model model){
    List<? extends CourseInfo> allCourseInfo = courseInfoService.findAllCourseInfo();
    model.addAttribute("courses",allCourseInfo);
    return "list_course";
  }

  /**
   * 从前端获得课程信息执行插入到数据库的操作
   * @param courseInfo
   * @return 成功状态
   */
  @RequestMapping("add_course")
  @ResponseBody
  public Map addCourse(@RequestBody CourseInfo courseInfo){
    HashMap<String, Object> modelMap = new HashMap<>();
    int i = courseInfoService.addCourse(courseInfo);
    if (i>0){
      modelMap.put("success",true);
    }else {
      modelMap.put("success",false);
    }
    return modelMap;
  }

  /**
   * 从前端获取课程信息，转成对象，更新数据库
   * @param courseInfo
   * @return 成功状态
   */
  @RequestMapping("edit_course")
  @ResponseBody
  public Map updateCourse(@RequestBody CourseInfo courseInfo){
    HashMap<String, Object> modelMap = new HashMap<>();
    String oid = courseInfo.getOid();
    String courseName = courseInfo.getCourseName();
    //调用更新方法
    int i = courseInfoService.updateCourseName(oid, courseName);
    if (i>0){
      modelMap.put("success",true);
    }else {
      modelMap.put("success",false);
    }
    return modelMap;
  }

  /**
   * 根据id查询一个课程信息，以json格式传给前端
   * @param oid
   * @return json格式课程信息
   */
  @RequestMapping("getCourse")
  @ResponseBody
  public CourseInfo getCourse(@RequestParam String oid){
    CourseInfo courseById = courseInfoService.findCourseById(oid);
    return courseById;
  }
}
