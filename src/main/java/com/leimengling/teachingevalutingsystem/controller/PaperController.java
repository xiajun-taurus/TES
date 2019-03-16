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


  //返回json
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

  //返回json的paper
  @GetMapping("getAPaper")
  @ResponseBody
  public Paper getPaper(@RequestParam String oid) {
    return paperService.getPaperById(oid);
  }

  //更新问卷标题
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


  @RequestMapping("paper/{resid}/{oid}")
  public String showPaper(@PathVariable("resid") String resultId, @PathVariable("oid") String oid,
      Model model) {
    Paper paperById = paperService.getPaperById(oid);
    String questions = paperById.getQuestions();
    List<Question> questionList = new ArrayList<>();
    String[] split = questions.split(",");
    for (int i = 0; i < split.length; i++) {
      Question aQuestion = questionService.getAQuestion(split[i]);
      questionList.add(aQuestion);
    }
    model.addAttribute("paperTitle", paperById.getPaperTitle());
    model.addAttribute("questions", questionList);
    model.addAttribute("resultId", resultId);
    return "paper";
  }

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

  @RequestMapping("deletePaper/{oid}")
  public String deletePaper(@PathVariable("oid") String oid) {
    int i = paperService.deletePaperById(oid);
    return "redirect:/list_paper";
  }
}

@Data
class QuestionData {

  private String oid;
  private List<String> questions;
}
