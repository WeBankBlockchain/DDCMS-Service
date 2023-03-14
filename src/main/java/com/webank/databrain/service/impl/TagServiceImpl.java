package com.webank.databrain.service.impl;

import com.webank.databrain.dao.entity.TagInfoEntity;
import com.webank.databrain.dao.mapper.TagInfoMapper;
import com.webank.databrain.service.TagService;
import com.webank.databrain.vo.common.CommonResponse;
import com.webank.databrain.vo.common.HotDataRequest;
import com.webank.databrain.vo.request.tags.CreateTagRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagInfoMapper tagInfoMapper;

    public CommonResponse listHotTags(HotDataRequest request) {

        List<TagInfoEntity> tags = tagInfoMapper.queryHotTags(request.getTopCount());
        return CommonResponse.success(tags);
    }

    public CommonResponse createTag(CreateTagRequest createTagRequest){
        TagInfoEntity tagPO = new TagInfoEntity();
        tagPO.setTagName(createTagRequest.getTagName());
        tagInfoMapper.insertItem(tagPO);

        return CommonResponse.success(tagPO.getPkId());
    }
}
