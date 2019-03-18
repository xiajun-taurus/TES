package com.leimengling.teachingevalutingsystem.controller;

import com.leimengling.teachingevalutingsystem.domain.MajorInfo;
import com.leimengling.teachingevalutingsystem.service.MajorService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Lei MengLing.
 * 专业信息相关
 */
@Controller
public class MajorInfoController {
  @Autowired
  private MajorService majorService;

  /**
   * 展示所有专业信息
   * @param modelMap
   * @return 返回专业列表页面
   */
  @RequestMapping("list_major")
  public String listMajor(ModelMap modelMap){
    List<? extends MajorInfo> allMajorInfo = majorService.findAllMajorInfo();
    modelMap.put("majors",allMajorInfo);
    return "list_major";
  }

  /**
   * 添加专业信息
   * @param majorInfo
   * @return 成功状态
   */
  @RequestMapping("addMajor")
  @ResponseBody
  public Map addClass(@RequestBody MajorInfo majorInfo){
    HashMap<String, Object> modelMap = new HashMap<>();
    //从前端获取的json转成major对象，再取出专业名称插入数据库，id自动生成
    String majorName = majorInfo.getMajorName();
    if (majorService.addMajor(majorInfo)==0){
      modelMap.put("success",false);
    }else{
      modelMap.put("success",true);
    }
    return modelMap;
  }

  /**
   * 根据前端传来的信息删除专业信息
   * @param majorInfo
   * @return 成功状态
   */
  @RequestMapping("deleteMajor")
  @ResponseBody
  public Map delectQuestion(@RequestBody MajorInfo majorInfo){
    HashMap<String, Object> modelMap = new HashMap<>();
    String oid = majorInfo.getOid();
    if (majorService.deleteMajor(oid)==0){
      modelMap.put("success",false);
    }else{
      modelMap.put("success",true);
    }
    return modelMap;
  }

  /**
   * 根据传入的专业信息，更新数据库，返回更新状态
   * @param majorInfo
   * @return 成功状态
   */
  @PostMapping("updateMajor")
  @ResponseBody
  public Map updateQuestion(@RequestBody MajorInfo majorInfo){
    HashMap<String,Boolean> modelMap = new HashMap<>();
    if (majorService.updateMajorName(majorInfo.getOid(),majorInfo.getMajorName())>0){
      modelMap.put("success",true);
    }else {
      modelMap.put("success",false);
    }
    return modelMap;
  }

  /**
   * 根据id查询专业信息，返回给前端
   * @param oid
   * @return
   */
  @RequestMapping("getMajor")
  @ResponseBody
  public MajorInfo getClass(@RequestParam("oid") String oid){
    return majorService.findMajorById(oid);
  }
}
