package com.webank.databrain.dao.mapper;

import com.webank.databrain.bo.DataSchemaWithAccessBO;
import com.webank.databrain.dao.entity.DataSchemaAccessInfoEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface DataSchemaAccessInfoMapper {

    @Insert("INSERT INTO t_data_schema_access_info(" +
            "data_schema_id," +
            "data_format," +
            "data_protocol, " +
            "content_schema," +
            "access_condition," +
            "uri," +
            "effect_time," +
            "expire_time," +
            "create_time" +
            ") " +
            "VALUES(" +
            "#{dataSchemaId}, " +
            "#{dataFormat}, " +
            "#{dataProtocol}," +
            "#{contentSchema}," +
            "#{accessCondition}," +
            "#{uri}," +
            "#{effectTime}," +
            "#{expireTime}," +
            "#{createTime}" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "pkId", keyColumn = "pk_id")
    void insertDataSchemaAccessInfo(DataSchemaAccessInfoEntity dataSchemaAccessInfoEntity);


    @Update("UPDATE t_data_schema_access_info SET " +
            "data_format=#{dataFormat}, " +
            "data_protocol=#{dataProtocol}, " +
            "content_schema=#{contentSchema}," +
            "access_condition=#{accessCondition}, " +
            "uri=#{uri}," +
            "effect_time=#{effectTime}," +
            "expire_time=#{expireTime} " +
            "WHERE pk_id=#{pkId}")
    void updateDataSchemaAccessInfo(DataSchemaAccessInfoEntity dataSchemaAccessInfoEntity);


    @Select("SELECT " +
            " pk_id as accessId," +
            " data_format," +
            " data_protocol," +
            " content_schema," +
            " access_condition," +
            " uri," +
            " effect_time," +
            " expire_time" +
            " from t_data_schema_access_info " +
            " where pk_id = #{accessId}")
    @ResultType(DataSchemaWithAccessBO.class)
    DataSchemaWithAccessBO getSchemaAccessByGid(Long accessId);

}
