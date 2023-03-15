package com.webank.databrain.dao.mapper;

import com.webank.databrain.dao.entity.TagInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TagInfoMapper {

    @Insert("INSERT INTO t_tag_info (tag_name) VALUES(#{tagName})")
    @Options(useGeneratedKeys = true, keyProperty = "pkId")
    void insertItem(TagInfoEntity tag);

    @Select("SELECT * FROM t_tag_info ORDER BY pk_id DESC LIMIT #{topN}")
    @ResultType(TagInfoEntity.class)
    List<TagInfoEntity> queryHotTags(int topN);


    @Select("SELECT * FROM t_tag_info where tag_name = #{name}")
    @ResultType(TagInfoEntity.class)
    TagInfoEntity queryTagByName(String name);


    @Select("<script>" +
            "SELECT * FROM t_tag_info where pk_id in" +
            "   <foreach item='id' index='index' collection='ids' open='(' separator=',' close=')'>" +
            "       #{id}" +
            "   </foreach>" +
            "</script>")
    @ResultType(TagInfoEntity.class)
    List<TagInfoEntity> queryTagByIds(List<Long> ids);
}
