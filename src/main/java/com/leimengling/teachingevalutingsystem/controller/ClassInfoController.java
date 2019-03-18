package com.leimengling.teachingevalutingsystem.controller;

import com.leimengling.teachingevalutingsystem.domain.ClassInfo;
import com.leimengling.teachingevalutingsystem.domain.XuanKe;
import com.leimengling.teachingevalutingsystem.service.ClassInfoService;
import com.leimengling.teachingevalutingsystem.service.CourseInfoService;
import com.leimengling.teachingevalutingsystem.service.XuankeService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Lei MengLing.
 */

@Controller
public class ClassInfoController {
  private ClassInfoService classInfoService;
  private XuankeService xuankeService;
  private CourseInfoService courseInfoService;
@Autowired
  public ClassInfoController(
      ClassInfoService classInfoService,
      XuankeService xuankeService,
      CourseInfoService courseInfoService) {
    this.classInfoService = classInfoService;
    this.xuankeService = xuankeService;
    this.courseInfoService = courseInfoService;
  }

  /**
   * 查询所有班级信息，返回给前端
   * @param modelMap
   * @return 班级列表页面
   */
  @RequestMapping("list_class")
  public String listClass(ModelMap modelMap) {
    List<? extends ClassInfo> allClass = classInfoService.getAllClass();
    modelMap.put("classes", allClass);
    return "list_class";
  }

  /**
   * 从前端获取班级信息，插入到数据库
   * @param classInfo
   * @return 成功状态
   */
  @RequestMapping("addClass")
  @ResponseBody
  public Map addClass(@RequestBody ClassInfo classInfo){
    HashMap<String, Object> modelMap = new HashMap<>();
    String className = classInfo.getClassName();
    if (classInfoService.addClass(className)==0){
      modelMap.put("success",false);
    }else{
      modelMap.put("success",true);
    }
    return modelMap;
  }

  /**
   * 获取id，删除对应班级信息
   * @param classInfo
   * @return 成功状态
   */
  @RequestMapping("deleteClass")
  @ResponseBody
  public Map delectQuestion(@RequestBody ClassInfo classInfo){
    HashMap<String, Object> modelMap = new HashMap<>();
    String oid = classInfo.getOid();
    if (classInfoService.deleteClass(oid)==0){
      modelMap.put("success",false);
    }else{
      modelMap.put("success",true);
    }
    return modelMap;

  }

  /**
   * 根据传来的班级信息，更新数据库
   * @param classInfo
   * @return 成功状态
   */
  @PostMapping("updateClass")
  @ResponseBody
  public Map updateQuestion(@RequestBody ClassInfo classInfo){
    HashMap<String,Boolean> modelMap = new HashMap<>();
    if (classInfoService.updateClass(classInfo.getOid(),classInfo.getClassName())>0){
      modelMap.put("success",true);
    }else {
      modelMap.put("success",false);
    }
    return modelMap;
  }

  /**
   * 按照id查询一个班级信息，传给前端
   * @param oid
   * @return json格式的班级信息
   */
  @RequestMapping("getClass")
  @ResponseBody
  public ClassInfo getClass(@RequestParam("oid") String oid){
    return classInfoService.getClass(oid);
  }


  /**
   * 根据传入的选课信息（班级id，课程id），更新数据库
   * @param xuanKe
   * @return 成功状态
   */
  @RequestMapping("addXuanke")
  @ResponseBody
  public Map addXuankeInfo(@RequestBody XuanKe xuanKe){
    HashMap<String,Boolean> modelMap = new HashMap<>();
    if (xuankeService.addXuankeInfo(xuanKe)>0){
      modelMap.put("success",true);
    }else {
      modelMap.put("success",false);
    }
    return modelMap;
  }

  /**
   * 查询所有选课信息，传给前端
   * @param modelMap
   * @return 选课信息列表界面
   */
  @RequestMapping("list_xuanke")
  public String listXuanke(ModelMap modelMap){
    modelMap.put("xuankes",xuankeService.findAllXuanKeInfo());
    modelMap.put("classes",classInfoService.getAllClass());
    modelMap.put("courses",courseInfoService.findAllCourseInfo());
    return "list_xuanke";
  }

}
