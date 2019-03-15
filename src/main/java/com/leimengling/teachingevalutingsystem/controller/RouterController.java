package com.leimengling.teachingevalutingsystem.controller;

import com.leimengling.teachingevalutingsystem.domain.Student;
import com.leimengling.teachingevalutingsystem.domain.User;
import com.leimengling.teachingevalutingsystem.service.StudentService;
import com.leimengling.teachingevalutingsystem.service.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Lei MengLing.
 */
@Controller
public class RouterController {

  @Autowired
  private UserService userService;
  @Autowired
  private StudentService studentService;

  /**
   * 跳转到教师首页，并在session中加入用户信息
   */
  @RequestMapping("teacher")
  public String teacher(HttpSession session) {
    User userInfo = (User) session.getAttribute("userInfo");
    if (userInfo == null) {
      return "redirect:login";
    } else {
      return "teacher";
    }

  }

  /**
   * 跳转到管理员界面，并在session中加入用户信息
   */
  @RequestMapping("admin")
  public String admin(HttpSession session, ModelMap modelMap) {
    User userInfo = (User) session.getAttribute("userInfo");
    if (userInfo == null) {
      return "redirect:login";
    } else {
      modelMap.put("userInfo", userInfo);
      return "admin";
    }

  }

  /**
   * 返回用户所在的主页，如果是管理员跳转到admin否则条转到index
   */
  @RequestMapping("home")
  public String home(HttpSession session, ModelMap modelMap) {
    User userInfo = (User) session.getAttribute("userInfo");
    if (userInfo == null) {
      return "login";
    }
    modelMap.put("userInfo", userInfo);
    if (userInfo != null && userInfo.getRole() == 0) {
      return "admin";
    } else if (userInfo != null && userInfo.getRole() == 1) {
      return "index";
    } else {
      Student studentById = studentService.findStudentById(userInfo.getOid());
      modelMap.put("student", studentById);
      return "index";
    }
  }

  /**
   * 跳转到结果分析页面
   * @return
   */
  @RequestMapping("result_charts")
  public String resultCharts() {
    return "result_charts";
  }
}

