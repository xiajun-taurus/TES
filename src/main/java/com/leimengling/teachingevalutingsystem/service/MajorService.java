package com.leimengling.teachingevalutingsystem.service;

import com.leimengling.teachingevalutingsystem.domain.MajorInfo;
import java.util.List;

/**
 * Created by Lei MengLing.
 */
public interface MajorService {
  List<? extends MajorInfo> findAllMajorInfo();

  int updateMajorName(String oid, String majorName);

  MajorInfo findMajorById(String oid);

  int addMajor(MajorInfo majorInfo);

  int deleteMajor(String oid);
}
