package com.webank.databrain.service;


import com.webank.databrain.vo.common.CommonResponse;
import com.webank.databrain.vo.common.HotDataRequest;
import com.webank.databrain.vo.request.tags.CreateTagRequest;

public interface TagService {

    CommonResponse listHotTags(HotDataRequest request);


    CommonResponse createTag(CreateTagRequest createTagRequest);

}
