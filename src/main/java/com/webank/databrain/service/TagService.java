package com.webank.databrain.service;


import com.webank.databrain.vo.common.CommonResponse;
import com.webank.databrain.vo.common.HotDataRequest;

public interface TagService {

    CommonResponse listHotTags(HotDataRequest request);
}
