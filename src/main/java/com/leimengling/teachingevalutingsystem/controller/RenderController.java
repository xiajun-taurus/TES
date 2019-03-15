package com.leimengling.teachingevalutingsystem.controller;

import com.leimengling.teachingevalutingsystem.domain.Student;
import com.leimengling.teachingevalutingsystem.domain.User;
import com.leimengling.teachingevalutingsystem.service.QuestionService;
import com.leimengling.teachingevalutingsystem.service.StudentService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Lei MengLing. 页面数据请求
 */
@Controller
public class RenderController {

  @Autowired
  private StudentService studentService;
  @Autowired
  private QuestionService questionService;

  /**
   * 渲染首页的个人数据
   */
  @PostMapping("indexRender")
  public String indexRender(HttpSession session, ModelMap modelMap) {
    User userInfo = (User) session.getAttribute("userInfo");
    //登录者身份为管理员
    if (userInfo != null && userInfo.getRole() != 2) {
      modelMap.addAttribute("userInfo", userInfo);
    } else if (userInfo != null && userInfo.getRole() == 2) {  //如果登录者的身份是2（学生）,查询学生数据
      Student studentById = studentService.findStudentById(userInfo.getOid());
      modelMap.put("trueName", userInfo.getTrueName());
      modelMap.put("email", userInfo.getEMail());
      modelMap.put("signature", userInfo.getSignature());
      modelMap.put("userName", userInfo.getUserName());
      modelMap.put("class", studentById.getClassId());
      modelMap.put("schoolNo", userInfo.getSchoolNo());
      modelMap.put("major", studentById.getMajorId());
      modelMap.put("phone", userInfo.getPhone());
    }
    return "index";
  }


  /**
   * 渲染个人信息编辑页数据
   */
  @PostMapping("renderProfileEdit")
  @ResponseBody
  public Map renderProfileEdit(HttpSession session) {
    User userInfo = (User) session.getAttribute("userInfo");
    HashMap<String, Object> modelMap = new HashMap<>();
    //登录者身份为管理员
    if (userInfo != null && userInfo.getRole() != 2) {

      modelMap.put("trueName", userInfo.getTrueName());
      modelMap.put("email", userInfo.getEMail());
      modelMap.put("signature", userInfo.getSignature());
      modelMap.put("userName", userInfo.getUserName());
      modelMap.put("schoolNo", userInfo.getSchoolNo());
      modelMap.put("phone", userInfo.getPhone());
    } else if (userInfo != null && userInfo.getRole() == 2) {  //如果登录者的身份是2（学生）,查询学生数据
      Student studentById = studentService.findStudentById(userInfo.getOid());
      modelMap.put("trueName", userInfo.getTrueName());
      modelMap.put("email", userInfo.getEMail());
      modelMap.put("signature", userInfo.getSignature());
      modelMap.put("userName", userInfo.getUserName());
      modelMap.put("class", studentById.getClassId());
      modelMap.put("schoolNo", userInfo.getSchoolNo());
      modelMap.put("major", studentById.getMajorId());
      modelMap.put("phone", userInfo.getPhone());
    }
    return modelMap;
  }


}
