package com.leimengling.teachingevalutingsystem.service;

import com.leimengling.teachingevalutingsystem.domain.XuanKe;
import java.util.List;

/**
 * Created by Lei MengLing.
 */
public interface XuankeService {
  int addXuankeInfo(XuanKe xuanKe);
  List<? extends XuanKe> findAllXuanKeInfo();

  int delXuankeInfo(String id);
}
