package com.webank.databrain.dao.mapper;


import com.webank.databrain.bo.DataSchemaDetailBO;
import com.webank.databrain.dao.entity.DataSchemaInfoEntity;
import com.webank.databrain.bo.DataSchemaWithAccessBO;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface DataSchemaInfoMapper {

    @Select("<script>" +
            "SELECT " +
            "a.pk_id as schemaId," +
            "a.data_schema_name," +
            "a.provider_id, " +
            "a.product_id," +
            "a.version," +
            "a.visible," +
            "a.data_schema_desc," +
            "a.data_schema_usage," +
            "a.price," +
            "a.status," +
            "a.review_time," +
            "a.create_time," +
            "d.product_name," +
            "e.company_name as providerName," +
            "b.tag_id_list  " +
            "from t_data_schema_info a " +
            "left join " +
            "(SELECT data_schema_id, GROUP_CONCAT(tag_id SEPARATOR ',') AS tag_id_list " +
            "FROM t_data_schema_tags " +
            "GROUP BY data_schema_id) b on a.pk_id = b.data_schema_id " +
            "left join " +
            "t_product_info d on a.product_id = d.pk_id " +
            "left join " +
            "t_company_info e on a.provider_id = e.account_id " +
            "where 1 =1  " +
            "<if test='reviewState != null and reviewState >= 0'> AND a.status = #{reviewState} </if>" +
            "<if test='productId != null and productId &gt; 0'> AND a.product_id = #{productId} </if>" +
            "<if test='providerId != null and providerId &gt; 0'> AND a.provider_id = #{providerId} </if>" +
            "<if test='keyWord != null and keyWord.trim() != \"\"'> AND a.data_schema_name like concat('%', #{keyWord}, '%') " +
            " or a.data_schema_desc like concat('%', #{keyWord}, '%') </if>" +
            "<if test='tagId != null and tagId >= 0'> HAVING FIND_IN_SET(#{tagId}, tag_id_list) </if>" +
            " ORDER BY a.create_time DESC LIMIT #{start}, #{pageSize} " +
            "</script>" )
    @ResultType(DataSchemaDetailBO.class)
    List<DataSchemaDetailBO> pageQuerySchema(@Param("start") int start,
                                             @Param("pageSize")int pageSize,
                                             @Param("productId") Long productId,
                                             @Param("providerId") Long providerId,
                                             @Param("keyWord") String keyWord,
                                             @Param("reviewState") Integer reviewState,
                                             @Param("tagId") Long tagId);

    @Select("<script>" +
            "SELECT " +
            "a.pk_id as schemaId," +
            "a.data_schema_name," +
            "a.provider_id, " +
            "a.product_id," +
            "a.version," +
            "a.visible," +
            "a.data_schema_desc," +
            "a.data_schema_usage," +
            "a.price," +
            "a.status," +
            "a.review_time," +
            "a.create_time," +
            "d.product_name," +
            "e.company_name as providerName " +
            "from t_data_schema_info a " +
            "left join " +
            "t_product_info d on a.product_id = d.pk_id " +
            "left join " +
            "t_company_info e on a.provider_id = e.account_id " +
            "left join " +
            "t_account_info f on e.account_id = f.pk_id " +
            "where f.did = #{did} " +
            "<if test='reviewState != null and reviewState >= 0'> AND a.status = #{reviewState} </if>" +
            "<if test='keyWord != null and keyWord.trim() != \"\"'> AND a.data_schema_name like concat('%', #{keyWord}, '%') " +
            " or a.data_schema_desc like concat('%', #{keyWord}, '%') </if>" +
            " ORDER BY a.create_time DESC LIMIT #{start}, #{pageSize} " +
            "</script>" )
    @ResultType(DataSchemaDetailBO.class)
    List<DataSchemaDetailBO> pageQueryMySchema(@Param("start") int start,
                                               @Param("pageSize")int pageSize,
                                               @Param("did") String did,
                                               @Param("keyWord") String keyWord,
                                               @Param("reviewState") Integer reviewState);

    @Select("<script>" +
            " SELECT COUNT(*) FROM (SELECT data_schema_id, GROUP_CONCAT(tag_id SEPARATOR ',') AS tag_id_list " +
            " FROM t_data_schema_tags " +
            " GROUP BY data_schema_id " +
            " <if test='tagId != null and tagId >= 0'> HAVING FIND_IN_SET(#{tagId}, tag_id_list) </if>" +
            " ) b"  +
            " left join " +
            " t_data_schema_info a on a.pk_id = b.data_schema_id " +
            " left join " +
            " t_company_info e on a.provider_id = e.account_id "  +
            " left join " +
            " t_account_info f on e.account_id = f.pk_id " +
            " where 1=1 " +
            "<if test='reviewState != null and reviewState >= 0'> AND a.status = #{reviewState} </if>" +
            "<if test='productId != null and productId &gt; 0'> AND a.product_id = #{productId} </if>" +
            "<if test='providerId != null and providerId &gt; 0'> AND a.provider_id = #{providerId} </if>" +
            "<if test='keyWord != null and keyWord.trim() != \"\"'> AND a.data_schema_name like concat('%', #{keyWord}, '%') " +
            " or a.data_schema_desc like concat('%', #{keyWord}, '%') </if>" +
            "<if test='did != null'> AND f.did = #{did} </if>" +
            "</script>")
    int count(
              @Param("productId") Long productId,
              @Param("providerId") Long providerId,
              @Param("keyWord") String keyWord,
              @Param("did") String did,
              @Param("reviewState") Integer reviewState,
              @Param("tagId") Long tagId);


    @Insert("INSERT INTO t_data_schema_info(" +
            "data_schema_bid," +
            "data_schema_name," +
            "provider_id, " +
            "product_id," +
            "version," +
            "visible," +
            "data_schema_desc," +
            "data_schema_usage," +
            "price," +
            "status," +
            "review_time" +
            ") " +
            "VALUES(" +
            "#{dataSchemaBid}, " +
            "#{dataSchemaName}, " +
            "#{providerId}," +
            "#{productId}," +
            "#{version}," +
            "#{visible}," +
            "#{dataSchemaDesc}," +
            "#{dataSchemaUsage}," +
            "#{price}," +
            "#{status}," +
            "#{reviewTime}" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "pkId", keyColumn = "pk_id")
    void insertDataSchemaInfo(DataSchemaInfoEntity dataSchemaInfoEntity);


    @Update("UPDATE t_data_schema_info SET " +
            "data_schema_name=#{dataSchemaName}, " +
            "visible=#{visible}, " +
            "data_schema_desc=#{dataSchemaDesc}, " +
            "data_schema_usage=#{dataSchemaUsage}," +
            "data_schema_desc=#{dataSchemaDesc}, " +
            "price=#{price}," +
            "version=#{version}," +
            "update_time=#{updateTime} " +
            "WHERE pk_id=#{pkId}")
    void updateDataSchemaInfo(DataSchemaInfoEntity dataSchemaInfoEntity);


    @Update("UPDATE t_data_schema_info SET " +
            "state=#{state}, " +
            "WHERE pk_id=#{schemaId}")
    void updateDataSchemaState(Long schemaId, int state);

    @Select("SELECT " +
            "a.pk_id as schemaId," +
            "a.data_schema_name," +
            "a.provider_id, " +
            "a.product_id," +
            "a.version," +
            "a.visible," +
            "a.data_schema_desc," +
            "a.data_schema_usage," +
            "a.price," +
            "a.status," +
            "a.review_time," +
            "a.create_time," +
            "d.product_name," +
            "e.company_name as providerName," +
            "g.pk_id as accessId " +
            "from t_data_schema_info a " +
            "left join " +
            "t_product_info d on a.product_id = d.pk_id " +
            "left join " +
            "t_company_info e on a.provider_id = e.pk_id " +
            "left join " +
            "t_account_info f on e.account_id = f.pk_id " +
            " left join " +
            "t_data_schema_access_info g on a.pk_id = g.data_schema_id" +
            " where a.pk_id = #{schemaId}")
    @ResultType(DataSchemaWithAccessBO.class)
    DataSchemaWithAccessBO getSchemaById(Long schemaId);

    @Select("SELECT " +
            " * " +
            " from t_data_schema_info " +
            " where pk_id = #{schemaId}")
    @ResultType(DataSchemaInfoEntity.class)
    DataSchemaInfoEntity getSchemaBySchemaId(Long schemaId);
}
