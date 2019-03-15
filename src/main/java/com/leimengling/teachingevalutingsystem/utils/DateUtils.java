package com.leimengling.teachingevalutingsystem.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lei MengLing.
 */
public class DateUtils {
  private static final String YEAR_MONTH_DAY_HOUR_MINITUES_SECOND = "yyyy-MM-dd HH:mm:ss";

  /**
   * 将日期格式化为：2000-1-1 12：00：00的格式
   * @param date 待转化的date
   * @return 格式化后的日期
   */
  public static String defaultFomat(Date date){
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YEAR_MONTH_DAY_HOUR_MINITUES_SECOND);
    return simpleDateFormat.format(date);
  }

}
