package com.leimengling.teachingevalutingsystem.controller;

import com.leimengling.teachingevalutingsystem.domain.Student;
import com.leimengling.teachingevalutingsystem.domain.User;
import com.leimengling.teachingevalutingsystem.service.ClassInfoService;
import com.leimengling.teachingevalutingsystem.service.MajorService;
import com.leimengling.teachingevalutingsystem.service.StudentService;
import com.leimengling.teachingevalutingsystem.service.UserService;
import com.leimengling.teachingevalutingsystem.utils.ClassUtils;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Lei MengLing. 更新操作
 */
@Controller
public class UpdateController {

  @Autowired
  private UserService userService;
  @Autowired
  private MajorService majorService;
  @Autowired
  private StudentService studentService;
  @Autowired
  private ClassInfoService classInfoService;

  /**
   * 更新个人信息
   */
  @RequestMapping("profile-edit")
  public String UpdateProfile(Model model, HttpSession session) {
    //从session中获取用户信息
    User userInfo = (User) session.getAttribute("userInfo");
    //根据用户id获取用户的信息
    userInfo = userService.findUserById(userInfo.getOid());
    //内部类，继承自user新增两个字段（因为学生有班级和专业）
    @lombok.Data
    class Data extends User {

      private String className;
      private String majorName;
    }
    //实例化这个内部类
    Data data = new Data();
    //将用户信息放入这个内部类对象
    try {
      ClassUtils.fatherToChild(userInfo, data);
    } catch (Exception e) {
      e.printStackTrace();
    }
    //如果用户角色是学生：把学生的班级和专业名称放入内部类对象，传给前端；否则之前传给前端
    if (userInfo.getRole() == 2) {
      Student studentById = studentService.findStudentById(userInfo.getOid());
      data.setMajorName(majorService.findMajorById(studentById.getMajorId()).getMajorName());
      data.setClassName(classInfoService.getClass(studentById.getClassId()).getClassName());
      model.addAttribute("userInf", data);
    } else {

      model.addAttribute("userInf", data);
    }
    return "profile-edit";
  }

  /**
   * 保存用户信息，从前台获取json（表单填写的内容），解析成User对象，用于数据库更新
   */
  @RequestMapping("saveProfile")
  @ResponseBody
  public Map saveProfile(@RequestBody User user) {
    HashMap<String, Object> modelMap = new HashMap<>();
    int i = userService.updateUser(user);
    if (i > 0) {
      modelMap.put("success", true);
    } else {
      modelMap.put("success", false);
    }
    return modelMap;
  }
}
