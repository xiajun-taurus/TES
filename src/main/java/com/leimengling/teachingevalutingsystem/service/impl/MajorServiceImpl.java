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
    //内部类，新增专业人数字段
    @lombok.Data
    class Data extends MajorInfo {

      private int count;
    }
    //查询所有专业
    List<MajorInfo> allMajor = majorInfoRepository.findAllMajor();
    ArrayList<Data> datas = new ArrayList<>();
    //对每一个专业，获取专业下的人数，放到内部类中
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
    return datas;//返回带有专业人数的专业信息
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
