package com.leimengling.teachingevalutingsystem.controller;

import com.leimengling.teachingevalutingsystem.domain.Paper;
import com.leimengling.teachingevalutingsystem.domain.Question;
import com.leimengling.teachingevalutingsystem.service.PaperService;
import com.leimengling.teachingevalutingsystem.service.QuestionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Lei MengLing.
 */
@Controller
public class PaperController {

  @Autowired
  private PaperService paperService;
  @Autowired
  private QuestionService questionService;

  /**
   * 查询所有问卷并展示
   */
  @RequestMapping("list_paper")
  public String listPapers(Model model) {
    List<? extends Paper> allPapers = paperService.getAllPapers();
    model.addAttribute("papers", allPapers);
    return "list_paper";
  }


  /**
   * 向指定id的问卷添加问题
   * @param questionData
   * @return 成功状态 成功：true 否则 false
   */
  @RequestMapping("questionsToPaper")
  @ResponseBody
  public Map addToPaper(@RequestBody QuestionData questionData) {
    Map<String, Object> modelMap = new HashMap<>();
    int i = paperService.updatePaperQuestions(questionData.getOid(), questionData.getQuestions());
    if (i > 0) {
      modelMap.put("success", true);
    } else {
      modelMap.put("success", false);
    }
    return modelMap;
  }

  /**
   * 根据id查询问卷信息并返回给前端
   * @param oid
   * @return json格式的问卷信息
   */
  @GetMapping("getAPaper")
  @ResponseBody
  public Paper getPaper(@RequestParam String oid) {
    return paperService.getPaperById(oid);
  }

  /**
   * 传来paper对象，根据属性更新数据库
   * @param paper
   * @return 成功状态
   */
  @RequestMapping("updatePaper")
  @ResponseBody
  public Map updatePaperTitle(@RequestBody Paper paper) {
    HashMap<String, Object> modelMap = new HashMap<>();
    int i = paperService.updatePaper(paper);
    if (i > 0) {
      modelMap.put("success", true);
    } else {
      modelMap.put("success", false);
    }
    return modelMap;
  }

  /**
   * 传入一个结果id和问卷id展示一个问卷页面，答题结果直接整合到结果id对应的记录中
   * @param resultId
   * @param oid
   * @param model
   * @return 问卷作答页面
   */
  @RequestMapping("paper/{resid}/{oid}")
  public String showPaper(@PathVariable("resid") String resultId, @PathVariable("oid") String oid,
      Model model) {
    //根据id找到问卷
    Paper paperById = paperService.getPaperById(oid);
    //找到问卷中的问题id
    String questions = paperById.getQuestions();
    List<Question> questionList = new ArrayList<>();
    //用，分割得出每一个id，再利用得出的id将问题对象放到问题列表中
    String[] split = questions.split(",");
    for (int i = 0; i < split.length; i++) {
      Question aQuestion = questionService.getAQuestion(split[i]);
      questionList.add(aQuestion);
    }
    //将问卷题目、问题列表交给前端显示，问卷结果id用于结果统计
    model.addAttribute("paperTitle", paperById.getPaperTitle());
    model.addAttribute("questions", questionList);
    model.addAttribute("resultId", resultId);
    return "paper";
  }

  /**
   * 预览问卷，逻辑与展示问卷相同
   * @param oid
   * @param model
   * @return 问卷为空，跳转到空问卷页面，否则跳转到问卷预览页面
   */
  @RequestMapping("preview/{id}")
  public String previewPaper(@PathVariable("id") String oid, Model model) {
    Paper paperById = paperService.getPaperById(oid);
    String questions = paperById.getQuestions();
    if (questions == null) {
      return "blankpaper";
    }
    List<Question> questionList = new ArrayList<>();
    String[] split = questions.split(",");
    for (int i = 0; i < split.length; i++) {
      Question aQuestion = questionService.getAQuestion(split[i]);
      questionList.add(aQuestion);
    }
    model.addAttribute("paperTitle", paperById.getPaperTitle());
    model.addAttribute("questions", questionList);
    return "paper-preview";
  }

  /**
   * 添加问卷
   * @param paper
   * @return 成功状态
   */
  @RequestMapping("addPaper")
  @ResponseBody
  public Map addPaper(@RequestBody Paper paper) {
    HashMap<String, Object> modelMap = new HashMap<>();
    int i = paperService.addPaper(paper);
    if (i > 0) {
      modelMap.put("success", true);
    } else {
      modelMap.put("success", false);
    }
    return modelMap;
  }

  /**
   * 根据路径中的id删除问卷
   * @param oid
   * @return 重定向到原页面
   */
  @RequestMapping("deletePaper/{oid}")
  public String deletePaper(@PathVariable("oid") String oid) {
    int i = paperService.deletePaperById(oid);
    return "redirect:/list_paper";
  }
}

@Data
class QuestionData {
  //问卷id
  private String oid;
  //选中的问题的id组成的list
  private List<String> questions;
}
