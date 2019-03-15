package com.leimengling.teachingevalutingsystem.controller;

import com.leimengling.teachingevalutingsystem.domain.Paper;
import com.leimengling.teachingevalutingsystem.domain.Question;
import com.leimengling.teachingevalutingsystem.service.PaperService;
import com.leimengling.teachingevalutingsystem.service.QuestionService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Lei MengLing.
 */
@Controller
public class QuestionController {

  @Autowired
  private QuestionService questionService;
  @Autowired
  private PaperService paperService;

  @ModelAttribute("papers")
  public List<? extends Paper> getAllPapers() {
    List<? extends Paper> allPapers = paperService.getAllPapers();
    return allPapers;
  }

  /**
   * 跳转到list_question页面，并展示所有题目
   *
   * @return list_question页面
   */
  @RequestMapping("list_question")
  public String showAllQuestion(ModelMap modelMap) {
    List<Question> allQuestion = questionService.findAllQuestion();
    modelMap.addAttribute("questions", allQuestion);
    return "list_question";
  }

  /**
   * 根据传来的id执行删除问题操作
   *
   * @return 重定向到问题列表页面
   */
  @RequestMapping("delete")
  public String delectQuestion(String oid) {
    questionService.deleteQuestion(oid);
    return "redirect:list_question";
  }

  /**
   * 根据id执行查询一个问题的操作
   *
   * @return json格式的问题信息
   */
  @RequestMapping("getAQuestion")
  @ResponseBody
  public Question getAQuestion(@RequestParam String oid) {
    Question question = questionService.getAQuestion(oid);
    return question;
  }

  /**
   * 增加一个问题
   *
   * @return 返回是否添加成功，成功：success：true 否则 false
   */
  @PostMapping(value = "addQuestion")
  @ResponseBody
  public Map addQuestion(@RequestBody Question question) {
    HashMap<String, Boolean> modelMap = new HashMap<>();
    if (questionService.addQuestion(question) > 0) {
      modelMap.put("success", true);
    } else {
      modelMap.put("success", false);
    }
    return modelMap;
  }

  /**
   * 更新一个问题
   *
   * @return 返回是否添加成功，成功：success：true 否则 false
   */
  @PostMapping("updateQuestion")
  @ResponseBody
  public Map updateQuestion(@RequestBody Question question) {
    HashMap<String, Boolean> modelMap = new HashMap<>();
    if (questionService.updateQuestion(question) > 0) {
      modelMap.put("success", true);
    } else {
      modelMap.put("success", false);
    }
    return modelMap;
  }
}
