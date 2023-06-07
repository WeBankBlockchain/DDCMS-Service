package com.webank.databrain.dao.mapper;

import com.webank.databrain.bo.DataSchemaDetailBO;
import com.webank.databrain.dao.entity.SchemaFavoriteInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SchemaFavoriteInfoMapper {

  @Insert(
      "INSERT INTO t_schema_favorite_info("
          + "schema_id,"
          + "account_id"
          + ") "
          + "VALUES("
          + "#{schemaId}, "
          + "#{accountId}"
          + ")")
  @Options(useGeneratedKeys = true, keyProperty = "pkId", keyColumn = "pk_id")
  void insertSchemaFavoriteInfo(SchemaFavoriteInfoEntity schemaFavoriteInfoEntity);

  @Delete(
      "delete from t_schema_favorite_info where schema_id = #{schemaId} and account_id = #{accountId}")
  void delSchemaFavoriteInfo(@Param("schemaId") Long schemaId, @Param("accountId") Long accountId);

  @Select(
      "<script>"
          + "SELECT "
          + "a.pk_id as schemaId,"
          + "a.data_schema_name,"
          + "a.provider_id, "
          + "a.product_id,"
          + "a.version,"
          + "a.visible,"
          + "a.data_schema_desc,"
          + "a.data_schema_usage,"
          + "a.price,"
          + "a.status,"
          + "a.review_time,"
          + "a.create_time,"
          + "d.product_name,"
          + "e.company_name as providerName "
          + "from t_schema_favorite_info f "
          + "left join "
          + "t_data_schema_info a on f.schema_id = a.pk_id "
          + "left join "
          + "t_product_info d on a.product_id = d.pk_id "
          + "left join "
          + "t_company_info e on a.provider_id = e.account_id "
          + "where f.account_id=#{accountId} "
          + "<if test='reviewState != null and reviewState >= 0'> AND a.status = #{reviewState} </if>"
          + "<if test='keyWord != null and keyWord.trim() != \"\"'> AND a.data_schema_name like concat('%', #{keyWord}, '%') "
          + " or a.data_schema_desc like concat('%', #{keyWord}, '%') </if>"
          + " ORDER BY a.create_time DESC LIMIT #{start}, #{pageSize} "
          + "</script>")
  @ResultType(DataSchemaDetailBO.class)
  List<DataSchemaDetailBO> pageQuerySchemaFavorite(
      @Param("start") int start,
      @Param("pageSize") int pageSize,
      @Param("accountId") Long accountId,
      @Param("keyWord") String keyWord,
      @Param("reviewState") Integer reviewState);

  @Select(
      "<script>"
          + "SELECT COUNT(*) "
          + "from t_schema_favorite_info f "
          + "left join "
          + "t_data_schema_info a on f.schema_id = a.pk_id "
          + " where f.account_id=#{accountId} "
          + "<if test='reviewState != null and reviewState >= 0'> AND a.status = #{reviewState} </if>"
          + "<if test='keyWord != null and keyWord.trim() != \"\"'> AND a.data_schema_name like concat('%', #{keyWord}, '%') "
          + " or a.data_schema_desc like concat('%', #{keyWord}, '%') </if>"
          + "</script>")
  int count(
      @Param("accountId") Long accountId,
      @Param("keyWord") String keyWord,
      @Param("reviewState") Integer reviewState);

  @Select(
      "<script>"
          + "SELECT "
          + " * "
          + " from t_schema_favorite_info "
          + " where account_id = #{accountId} and schema_id in "
          + "   <foreach item='id' index='index' collection='schemaIds' open='(' separator=',' close=')'>"
          + "       #{id}"
          + "   </foreach>"
          + "</script>")
  @ResultType(SchemaFavoriteInfoEntity.class)
  List<SchemaFavoriteInfoEntity> getSchemaFavBySchemaIds(Long accountId, List<Long> schemaIds);
}
