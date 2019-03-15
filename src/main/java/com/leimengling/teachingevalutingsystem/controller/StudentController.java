package com.leimengling.teachingevalutingsystem.controller;

import com.leimengling.teachingevalutingsystem.VO.CommentVO;
import com.leimengling.teachingevalutingsystem.VO.StudentVO;
import com.leimengling.teachingevalutingsystem.domain.Student;
import com.leimengling.teachingevalutingsystem.domain.User;
import com.leimengling.teachingevalutingsystem.service.ClassInfoService;
import com.leimengling.teachingevalutingsystem.service.CommentService;
import com.leimengling.teachingevalutingsystem.service.MajorService;
import com.leimengling.teachingevalutingsystem.service.StudentService;
import com.leimengling.teachingevalutingsystem.service.impl.StudentServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Lei MengLing.
 */
@Controller
public class StudentController {

  @Autowired
  private CommentService commentService;
  @Autowired
  private StudentService studentService;
  @Autowired
  private MajorService majorService;
  @Autowired
  private ClassInfoService classInfoService;

  /**
   * 新增学生
   */
  @RequestMapping("addStudent")
  @ResponseBody
  public Map addStudent(@RequestBody StudentVO studentVO) {

    HashMap<String, Object> modelMap = new HashMap<>();
    int i = studentService.addStudent(studentVO);
    if (i > 0) {
      modelMap.put("success", true);
    } else {
      modelMap.put("success", false);
    }
    return modelMap;
  }

  /**
   * 获取学生列表
   */
  @RequestMapping("list_student")
  public String show(Model model) {
    List<? extends Student> allStudents = studentService.findAllStudents();
    model.addAttribute("students", allStudents);
    model.addAttribute("majors", majorService.findAllMajorInfo());
    model.addAttribute("classes", classInfoService.getAllClass());
    return "list_student";
  }

  /**
   * 删除学生
   *
   * @param oid 被删除学生id
   */
  @RequestMapping("deleteStudent")
  public String delete(String oid) {
    studentService.delStudent(oid);
    return "redirect:list_student";
  }

  //获取问卷信息
  @RequestMapping("reading_papers")
  public String show(HttpSession session, Model model) {

    //从session中获取用户信息
    User student = (User) session.getAttribute("userInfo");
    String oid = student.getOid();//获得学生的id
    ArrayList<StudentServiceImpl.Data> commentingPapers = studentService.getCommentingPapers(oid);
    model.addAttribute("commentInfo", commentingPapers);

    return "reading_papers";
  }

  /**
   * 学生提交问卷： 从session获取用户信息，将评价产生的数据装填到VO，存到数据库，同时记录用户信息中的id
   */
  @RequestMapping("comment")
  @ResponseBody
  public Map comment(HttpSession session, @RequestBody CommentVO commentVO) {
    HashMap<String, Object> modelMap = new HashMap<>();
    User userInfo = (User) session.getAttribute("userInfo");
    //TODO：从session中获取用户信息赋给commenter_id
    String oid = userInfo.getOid();
    int i = commentService.addComment(commentVO, oid);
    if (i > 0) {
      modelMap.put("success", true);
    } else {
      modelMap.put("success", false);
    }
    return modelMap;
  }


}
