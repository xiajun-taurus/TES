package com.leimengling.teachingevalutingsystem.controller;

import com.leimengling.teachingevalutingsystem.domain.Question;
import com.leimengling.teachingevalutingsystem.domain.SecurityQuestion;
import com.leimengling.teachingevalutingsystem.domain.User;
import com.leimengling.teachingevalutingsystem.service.SecurityQuestionService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leimengling.teachingevalutingsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Lei MengLing.
 */
@Slf4j
@Controller
public class SecurityQuestionController {

  @Autowired
  private SecurityQuestionService securityQuestionService;
  @Autowired
  private UserService userService;

  /**
   * 获取所有密保问题，带到安全设置页面
   */
  @RequestMapping("security-edit")
  public String securityEdit(ModelMap modelMap) {
//    获取所有密保问题
    List<SecurityQuestion> questions = securityQuestionService.showAllQ();
//    装入map带给前端页面
    modelMap.addAttribute("questions", questions);
    return "security-edit";
  }

  //  获取所有密保问题
  @RequestMapping("list_securityquestion")
  public String show(Model model) {
    List<SecurityQuestion> securityQuestions = securityQuestionService.showAllQ();
    model.addAttribute("SQuestions", securityQuestions);
    return "list_securityquestion";
  }

  /**
   * 新增密保问题：将前端获取的密保问题的json传入，然后调用方法增加密保问题
   */
  @RequestMapping("add_securityquestion")
  @ResponseBody
  public Map addSQ(@RequestBody SecurityQuestion securityQuestion) {
    HashMap<String, Object> modelMap = new
        HashMap<>();

    int i = securityQuestionService.addSQ(securityQuestion);
    if (i > 0) {
      modelMap.put("success", true);
    } else {
      modelMap.put("success", false);
    }
    return modelMap;
  }

  /**
   * 根据用户id、回答和问题id更新用户的密保问题
   *
   * @return json格式数据
   */
  @ResponseBody
  @RequestMapping("updatePasswordProtect")
  public Map<String, Object> updatePasswordProtect(@RequestParam String userId,
      @RequestParam String answer, @RequestParam String questionId) {
    Map<String, Object> map = new HashMap<>();
    int result = userService.updateUserSecurityQuestion(questionId, answer, userId);
    if (result > 0) {
      map.put("flag", true);
      map.put("message", "密保设置成功！");
    } else {
      map.put("flag", false);
      map.put("message", "密保设置失败！");
    }
    log.info("{} map", map);
    return map;
  }

  /**
   * 列出所有密保问题，带到找回密码界面
   */
  @RequestMapping("find-password")
  public String findPassword(ModelMap modelMap) {
    List<SecurityQuestion> questions = securityQuestionService.showAllQ();
    modelMap.addAttribute("questions", questions);
    return "find-password.html";
  }

  /**
   * 根据用户输入获取用户的密码
   *
   * @return 用户密码
   */
  @RequestMapping("/getPassWord")
  public String getPassWord(RedirectAttributes redirectAttributes, @RequestParam String userId,
      @RequestParam String questionId, @RequestParam String answer) {
    log.info("{} userId", userId);
    User user = userService.findUser(userId);
    String message = "密保问题或者答案出错！";
//    如果用户回答与设定密保问题答案一致，将密码返回
    if (user.getSecurityQuestion().equals(questionId) && user.getSecurityAnswer().equals(answer)) {
      redirectAttributes.addFlashAttribute("password", user.getPassword());
      return "redirect:find-password";
    }
//    否则提示密保问题不正确
    redirectAttributes.addFlashAttribute("message", message);
    return "redirect:find-password";


  }
}
