package com.webank.databrain.service;


import com.webank.databrain.vo.common.CommonResponse;
import com.webank.databrain.vo.common.HotDataRequest;
import com.webank.databrain.vo.request.tags.CreateTagRequest;
import com.webank.databrain.vo.response.tags.CreateTagResponse;

public interface TagService {

    CommonResponse listHotTags(HotDataRequest request);


    CommonResponse<CreateTagResponse> createTag(CreateTagRequest createTagRequest);

}
