package com.leimengling.teachingevalutingsystem.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Lei MengLing.
 */
public class ClassUtils {

  /**
   * 将父类属性值赋给子类属性值
   * @param superClass
   * @param childClass
   * @param <T>
   * @throws Exception
   */
  public static <T> void fatherToChild(T superClass,T childClass) throws Exception{
    //如果子类的superClass和父类的Class对象不一致，说明两者没有父子关系，抛出异常
    if (childClass.getClass().getSuperclass()!=superClass.getClass()){
      throw new Exception(childClass+"不是"+superClass+"的子类");
    }
    Class<?> fatherClass = superClass.getClass();
    Field[] declaredFields = fatherClass.getDeclaredFields();
    for (int i = 0; i < declaredFields.length; i++) {
      Field field=declaredFields[i];
      Method method=fatherClass.getDeclaredMethod("get"+upperHeadChar(field.getName()));
      Object obj = method.invoke(superClass);
      field.setAccessible(true);
      field.set(childClass,obj);
    }
  }
  /**
   * 首字母大写，in:deleteDate，out:DeleteDate
   */
  public static String upperHeadChar(String in) {
    String head = in.substring(0, 1);
    String out = head.toUpperCase() + in.substring(1, in.length());
    return out;
  }
}
