package com.leimengling.teachingevalutingsystem.domain;

import java.util.HashMap;
import lombok.Data;

/**
 * Created by Lei MengLing.
 */
@Data
public class ChartsData {
  private String title;
  private HashMap<String,Object> dataMap = new HashMap<>();
}
