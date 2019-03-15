package com.leimengling.teachingevalutingsystem.service.impl;

import com.leimengling.teachingevalutingsystem.domain.MajorInfo;
import com.leimengling.teachingevalutingsystem.repository.MajorInfoRepository;
import com.leimengling.teachingevalutingsystem.service.MajorService;
import com.leimengling.teachingevalutingsystem.utils.ClassUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lei MengLing.
 */
@Service
public class MajorServiceImpl implements MajorService {

  @Autowired
  private MajorInfoRepository majorInfoRepository;

  @Override
  public List<? extends MajorInfo> findAllMajorInfo() {
    @lombok.Data
    class Data extends MajorInfo {

      private int count;
    }
    List<MajorInfo> allMajor = majorInfoRepository.findAllMajor();
    ArrayList<Data> datas = new ArrayList<>();
    for (MajorInfo majorInfo : allMajor) {
      Data data = new Data();
      try {
        ClassUtils.fatherToChild(majorInfo, data);
      } catch (Exception e) {
        e.printStackTrace();
      }
      int count = majorInfoRepository.countMajor(majorInfo.getOid());
      data.setCount(count);
      datas.add(data);
    }
    return datas;
  }

  @Override
  public int updateMajorName(String oid, String majorName) {
    return majorInfoRepository.updateMajorName(oid, majorName);
  }

  @Override
  public MajorInfo findMajorById(String oid) {
    return majorInfoRepository.findMajorById(oid);
  }

  @Override
  public int addMajor(MajorInfo majorInfo) {
    return majorInfoRepository.insertMajorInfo(majorInfo);
  }

  @Override
  public int deleteMajor(String oid) {
    return majorInfoRepository.deleteMajorInfo(oid);
  }
}
