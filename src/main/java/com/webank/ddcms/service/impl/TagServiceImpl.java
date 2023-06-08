package com.webank.ddcms.service.impl;

import com.webank.ddcms.dao.entity.TagInfoEntity;
import com.webank.ddcms.dao.mapper.TagInfoMapper;
import com.webank.ddcms.service.TagService;
import com.webank.ddcms.vo.common.CommonResponse;
import com.webank.ddcms.vo.common.HotDataRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

  @Autowired private TagInfoMapper tagInfoMapper;

  @Override
  public CommonResponse listHotTags(HotDataRequest request) {
    List<TagInfoEntity> tags = tagInfoMapper.queryHotTags(request.getTopCount());
    return CommonResponse.success(tags);
  }
}
