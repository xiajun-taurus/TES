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

  @RequestMapping("list_major")
  public String listMajor(ModelMap modelMap){
    List<? extends MajorInfo> allMajorInfo = majorService.findAllMajorInfo();
    modelMap.put("majors",allMajorInfo);
    return "list_major";
  }

  @RequestMapping("addMajor")
  @ResponseBody
  public Map addClass(@RequestBody MajorInfo majorInfo){
    HashMap<String, Object> modelMap = new HashMap<>();
    String majorName = majorInfo.getMajorName();
    if (majorService.addMajor(majorInfo)==0){
      modelMap.put("success",false);
    }else{
      modelMap.put("success",true);
    }
    return modelMap;
  }

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

  @RequestMapping("getMajor")
  @ResponseBody
  public MajorInfo getClass(@RequestParam("oid") String oid){
    return majorService.findMajorById(oid);
  }
}
