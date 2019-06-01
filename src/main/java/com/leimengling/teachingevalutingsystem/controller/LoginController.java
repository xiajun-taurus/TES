package com.leimengling.teachingevalutingsystem.controller;

import com.leimengling.teachingevalutingsystem.domain.Student;
import com.leimengling.teachingevalutingsystem.domain.User;
import com.leimengling.teachingevalutingsystem.service.StudentService;
import com.leimengling.teachingevalutingsystem.service.UserService;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Lei MengLing. 关于登录的Controller
 */
@Controller
@Slf4j
public class LoginController {

  @Autowired
  private UserService userService;
  @Autowired
  private StudentService studentService;


  /**
   * 用户登录验证
   *
   * @param userName 用户名
   * @param passWord 密码
   * @return 页面转发请求
   */
  @RequestMapping("/validate")
  public String Validate(@RequestParam(name = "userName") String userName,
      @RequestParam(name = "passWord") String passWord, HttpSession session, ModelMap modelMap) {
    //根据用户名查询数据库获得user对象
    User user = userService.findUser(userName);
    //如果查询到用户，将获取到的密码与查询到的user对象中密码比对
    if (user != null && passWord.equals(user.getPassword())) {
      //登录成功，将用户信息放入session
      session.setAttribute("userInfo", user);
      //将用户信息放到map传给前端
      modelMap.addAttribute("userInfo", user);

      //根据不同的用户角色跳转到不同页面
      if (user.getRole() == 0) {
        log.info("用户{}登录成功，登录身份: {}", user.getUserName(), user.getRole());
        return "admin";
      } else if (user.getRole() == 1) {
        log.info("用户{}登录成功，登录身份: {}", user.getUserName(), user.getRole());
        return "index";
      } else {
        Student student = studentService.findStudentById(user.getOid());
        log.info("{}");
        modelMap.put("student", student);
        return "index";
      }

    } else {
      //没有查询到用户或者密码不匹配。返回登录页面
      log.info("登录失败");
      modelMap.addAttribute("errMsg", "用户名或密码错误");
      return "login";
    }

  }


  @RequestMapping("index")
  public String index(HttpSession httpSession){
    //将用户信息放到session
    if(httpSession.getAttribute("userInfo")==null){
      return "login";
    }
    return "index.html";
  }



  /**
   * 登录页面，同时清除seesion中存放的用户信息
   */
  @RequestMapping(value = {"/","login"}, method = RequestMethod.GET)
  public String Login(HttpSession session) {
    session.removeAttribute("userInfo");
    if (session.getAttribute("userInfo") == null) {
      log.info("用户session清理完毕");
    }

    return "login";
  }

  /**
   * 学生、教师首页
   */
  @GetMapping("index")
  public String Index(HttpSession session ,ModelMap modelMap) {
    User userInfo = (User) session.getAttribute("userInfo");
    if(userInfo==null){
      return "login";
    }
    //将用户信息，如果是学生，外加学生信息放入map传给前端
    Student studentById = studentService.findStudentById(userInfo.getOid());
    modelMap.put("userInfo",userInfo);
    modelMap.put("student",studentById);
    return "index";
  }



}
