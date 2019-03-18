package com.leimengling.teachingevalutingsystem.domain;

import java.util.HashMap;
import lombok.Data;

/**
 * Created by Lei MengLing.
 */
@Data
public class ChartsData {
  //图标标题
  private String title;
  //图标元素
  private HashMap<String,Object> dataMap = new HashMap<>();
}
