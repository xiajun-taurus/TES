package com.leimengling.teachingevalutingsystem.domain;

import java.util.Date;
import lombok.Data;

/**
 * Created by Lei MengLing.
 */
@Data
public class CommentResultItem {
  //评价详情id
  private String oid;
  //分数
  private Integer score;
  //对应结果id
  private String resultId;
  //主观评价
  private String comment;
  //评价者id
  private String commenterId;
  //创建时间
  private Date createTime;
}
