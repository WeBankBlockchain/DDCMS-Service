package com.webank.ddcms.dao.mapper;

import com.webank.ddcms.dao.entity.ThirdPartyInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ThirdPartyInfoMapper {

    @Insert("INSERT INTO `t_third_party_info` (did, ${field}) VALUES (#{did}, #{value})")
    int insertOne(String did, String field, long value);

    @Select("SELECT * FROM `t_third_party_info` WHERE did = #{did}")
    ThirdPartyInfoEntity searchOneByDid(String did);

    @Select("SELECT * FROM `t_third_party_info` WHERE ${field} = #{value}")
    ThirdPartyInfoEntity searchOne(String field, long value);

    @Update("UPDATE `t_third_party_info` SET ${field} = #{value} WHERE did = #{did}")
    int updateOne(String did, String field, long value);
}
