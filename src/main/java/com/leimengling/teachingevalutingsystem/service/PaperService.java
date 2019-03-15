package com.leimengling.teachingevalutingsystem.service;

import com.leimengling.teachingevalutingsystem.domain.Paper;
import java.util.List;

/**
 * Created by Lei MengLing.
 */
public interface PaperService {

  /**
   * 传入一个问卷对象创建一个问卷
   * @param paper
   * @return 受影响的行数
   */
  int addPaper(Paper paper);

  /**
   * 获取所有问卷
   * @return paper列表
   */
  List<? extends Paper> getAllPapers();

  /**
   * 根据id查询对应的试卷
   * @param id
   * @return paper实体类对象
   */
  Paper getPaperById(String id);

  /**
   * 根据id删除对应试卷
   * @param id
   * @return
   */
  int deletePaperById(String id);

  /**
   * 根据id更新问题
   * @param id
   * @param questions
   * @return 受影响行数
   */
  int updatePaperQuestions(String id,List<String> questions);

  /**
   * 根据传回的对象更新问卷
   * @param paper
   * @return 受影响的行数
   */
  int updatePaper(Paper paper);

  /**
   * 同过学生id找到能回答的试题
   * @param id
   * @return
   */
  Paper getPaperByStudentId(String id);
}
