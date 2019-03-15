package com.leimengling.teachingevalutingsystem.service;

import com.leimengling.teachingevalutingsystem.VO.CommentVO;
import com.leimengling.teachingevalutingsystem.domain.ChartsData;
import com.leimengling.teachingevalutingsystem.domain.CommentResult;
import com.leimengling.teachingevalutingsystem.domain.Student;
import java.util.List;

/**
 * Created by Lei MengLing.
 */
public interface CommentService {
  int addComment(CommentVO commentVO,String userId);
  List<? extends CommentResult> findAllResults();
  List<ChartsData> getResultCharts();
  List<? extends Student> getUncommented(String oid);
  String getResultTitle(String oid);
}
