package com.webank.ddcms.service;

import com.webank.ddcms.vo.common.CommonResponse;
import com.webank.ddcms.vo.common.HotDataRequest;

public interface TagService {

  CommonResponse listHotTags(HotDataRequest request);
}
