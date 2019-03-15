package com.leimengling.teachingevalutingsystem.VO;

import com.leimengling.teachingevalutingsystem.domain.Student;
import lombok.Data;

/**
 * Created by Lei MengLing.
 */
@Data
public class StudentVO extends Student {
  private String userName;
  private String schoolNo;
}
