package com.leimengling.teachingevalutingsystem.controller;

import com.leimengling.teachingevalutingsystem.VO.PaperBindVO;
import com.leimengling.teachingevalutingsystem.VO.TeacherVO;
import com.leimengling.teachingevalutingsystem.domain.CourseInfo;
import com.leimengling.teachingevalutingsystem.domain.Paper;
import com.leimengling.teachingevalutingsystem.domain.Teacher;
import com.leimengling.teachingevalutingsystem.domain.User;
import com.leimengling.teachingevalutingsystem.service.CourseInfoService;
import com.leimengling.teachingevalutingsystem.service.PaperService;
import com.leimengling.teachingevalutingsystem.service.TeacherService;
import com.leimengling.teachingevalutingsystem.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Lei MengLing. 教师相关
 */

@Controller
public class TeacherController {

  @Autowired
  private TeacherService teacherService;
  @Autowired
  private CourseInfoService courseInfoService;
  @Autowired
  private PaperService paperService;
  @Autowired
  private UserService userService;

  /**
   * 获取所有课程信息展示到页面
   */
  @ModelAttribute("courses")
  public List<? extends CourseInfo> allCourses() {
    return courseInfoService.findAllCourseInfo();
  }

  /**
   * 获取所有问卷信息展示到页面
   */
  @ModelAttribute("papers")
  public List<? extends Paper> allPapers() {
    return paperService.getAllPapers();
  }

  /**
   * 获取所有教师信息展示到页面
   */
  @RequestMapping("list_teacher")
  public String show(Model model) {
    List<? extends Teacher> allTeacher = teacherService.findAllTeacher();
    model.addAttribute("teachers", allTeacher);
    return "list_teacher";
  }

  //教师与问卷绑定
  @RequestMapping("paperbind")
  @ResponseBody
  public Map paperBind(@RequestBody PaperBindVO bindVO) {
    Map<String, Object> modelMap = new HashMap<>();
    String teacherId = bindVO.getTeacherId();
    String paperId = bindVO.getPaperId();
    int i = teacherService.bindPaper(teacherId, paperId);
    if (i > 0) {
      modelMap.put("success", true);
    } else {
      modelMap.put("success", false);
    }
    return modelMap;
  }

  /**
   * 将前端json装到teacherVO中，然后将VO数据取出，调用方法将教师和问卷联系起来
   *
   * @return 成功success字段为true否则为false
   */
  @RequestMapping("addTeacher")
  @ResponseBody
  public Map addTeacher(@RequestBody TeacherVO teacherVO) {
    Map<String, Object> modelMap = new HashMap<>();
    String userName = teacherVO.getUserName();
    String schoolNo = teacherVO.getSchoolNo();
    String courseId = teacherVO.getCourseId();
    User user = new User();
    user.setRole(1);
    user.setTrueName(userName);
    user.setSchoolNo(schoolNo);
    user.setUserName(schoolNo);
    int i = userService.addUser(user);
    if (i > 0) {
      Teacher teacher = new Teacher();
      User user1 = userService.findUser(schoolNo);
      teacher.setOid(user1.getOid());
      teacher.setCourseId(courseId);
      int i1 = teacherService.addTeacher(teacher);
      if (i1 > 0) {
        modelMap.put("success", true);
      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", "教师信息添加失败");
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "用户信息添加失败");
    }
    return modelMap;
  }
}
