package com.leimengling.teachingevalutingsystem.domain;

import java.util.Date;
import lombok.Data;

/**
 * Created by Lei MengLing.
 */
@Data
public class CommentResultItem {

  private String oid;
  private Integer score;
  private String resultId;
  private String comment;
  private String commenterId;
  private Date createTime;
}
