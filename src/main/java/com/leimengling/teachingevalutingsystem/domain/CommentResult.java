package com.leimengling.teachingevalutingsystem.domain;

import java.util.Date;
import lombok.Data;

/**
 * Created by Lei MengLing.
 */
@Data
public class CommentResult {

  private String oid;
  private String teacherId;
  private String paperId;
  private Float averScore;
  private String comment;
  private Date createTime;
}
