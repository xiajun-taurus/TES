package com.leimengling.teachingevalutingsystem.controller;

import com.leimengling.teachingevalutingsystem.domain.ChartsData;
import com.leimengling.teachingevalutingsystem.domain.Student;
import com.leimengling.teachingevalutingsystem.service.CommentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Lei MengLing. 评教结果
 */
@Controller
public class ResultController {

  @Autowired
  private CommentService commentService;

  /**
   * 返回所有答题结果，并返回到结果列表页面
   * @param modelMap
   * @return
   */
  @RequestMapping("get_result")
  public String getResult(ModelMap modelMap) {
    modelMap.addAttribute("results", commentService.findAllResults());
    return "list_result";
  }

  /**
   * 以json格式返回答题结果的图表数据
   * @return
   */
  @RequestMapping("charts")
  @ResponseBody
  public List<ChartsData> chartsData() {
    return commentService.getResultCharts();
  }


  /**
   * 跳转到对应问卷的未评教列表
   * @param oid 问卷id
   * @param modelMap 未评教人员信息
   * @return 未评教列表
   */
  @RequestMapping("uncommented/{id}")
  public String showUncommented(@PathVariable("id") String oid, ModelMap modelMap) {
    List<? extends Student> uncommented = commentService.getUncommented(oid);
    //未评教列表名称
    modelMap.put("title", commentService.getResultTitle(oid));
    //未评教人员信息
    modelMap.put("students", uncommented);
    return "uncommented";
  }
}
