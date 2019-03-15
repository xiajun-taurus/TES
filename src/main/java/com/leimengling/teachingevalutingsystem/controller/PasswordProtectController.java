package com.leimengling.teachingevalutingsystem.controller;

import com.leimengling.teachingevalutingsystem.domain.SecurityQuestion;
import com.leimengling.teachingevalutingsystem.domain.User;
import com.leimengling.teachingevalutingsystem.service.SecurityQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
public class PasswordProtectController {

  @Autowired
  private SecurityQuestionService securityQuestionService;

  /**
   * 展示所有密保问题到下拉列表
   * @param modelMap
   * @param httpSession
   * @return 返回原界面
   */
  @RequestMapping("password-protect")
  public String passwordProtect(ModelMap modelMap, HttpSession httpSession) {
    User user = (User) httpSession.getAttribute("userInfo");
    modelMap.addAttribute("userInfo", user);
    List<SecurityQuestion> questions = securityQuestionService.showAllQ();
    modelMap.addAttribute("questions", questions);
    log.info("{} question", questions);

    return "password-protect.html";
  }
}
