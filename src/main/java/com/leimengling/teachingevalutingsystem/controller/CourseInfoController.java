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
  @RequestMapping("list_course")
  public String show(Model model){
    List<? extends CourseInfo> allCourseInfo = courseInfoService.findAllCourseInfo();
    model.addAttribute("courses",allCourseInfo);
    return "list_course";
  }

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

  @RequestMapping("edit_course")
  @ResponseBody
  public Map updateCourse(@RequestBody CourseInfo courseInfo){
    HashMap<String, Object> modelMap = new HashMap<>();
    String oid = courseInfo.getOid();
    String courseName = courseInfo.getCourseName();
    int i = courseInfoService.updateCourseName(oid, courseName);
    if (i>0){
      modelMap.put("success",true);
    }else {
      modelMap.put("success",false);
    }
    return modelMap;
  }

  @RequestMapping("getCourse")
  @ResponseBody
  public CourseInfo getCourse(@RequestParam String oid){
    CourseInfo courseById = courseInfoService.findCourseById(oid);
    return courseById;
  }
}
