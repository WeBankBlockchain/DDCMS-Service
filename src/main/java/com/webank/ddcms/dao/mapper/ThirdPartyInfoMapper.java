package com.webank.ddcms.dao.mapper;

import com.webank.ddcms.dao.entity.ThirdPartyInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ThirdPartyInfoMapper {

    @Insert("INSERT INTO `t_third_party_info` (pk_id, ${field}) VALUES (#{pkId}, #{value})")
    int insertOne(long pkId, String field, long value);

    @Select("SELECT * FROM `t_third_party_info` WHERE pk_id = #{pkId}")
    ThirdPartyInfoEntity searchOne(long pkId);

    @Update("UPDATE `t_third_party_info` SET ${field} = #{value} WHERE pk_id = #{pkId}")
    int updateOne(long pkId, String field, long value);
}
