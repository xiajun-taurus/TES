package com.leimengling.teachingevalutingsystem.controller;

import com.leimengling.teachingevalutingsystem.domain.User;
import com.leimengling.teachingevalutingsystem.service.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Lei MengLing.
 */
@Controller
public class UserController {

  @Autowired
  private UserService userService;

  @RequestMapping("updatePassword")
  public String updatePassword(@RequestParam("c-pwd") String oldPassword,
      @RequestParam("n-pwd") String newPassword,
      ModelMap modelMap, HttpSession session) {
    //获取session中的用户信息
    User userInfo = (User) session.getAttribute("userInfo");
    //获取从用户中提取出来的密码
    String password = userInfo.getPassword();
    //如果用户输入密码和原密码相同可以进行修改操作
    if (userInfo.getPassword().equals(oldPassword)) {
      int i = userService.updatePassword(userInfo.getOid(), newPassword);
      if (i > 0) {
        return "login";
      } else {
        modelMap.put("msg", "修改失败");
      }
    } else {
      modelMap.put("errMsg", "密码错误");
    }
    return "security-edit";
  }


}

