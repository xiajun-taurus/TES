package com.leimengling.teachingevalutingsystem.domain;

import java.util.Date;
import lombok.Data;

/**
 * Created by Lei MengLing.
 */
@Data
public class CommentResult {
  //评价结果id
  private String oid;
  //教师id
  private String teacherId;
  //问卷id
  private String paperId;
  //平均分数
  private Float averScore;
  //主观评价集合
  private String comment;
  //创建时间
  private Date createTime;
}
