package com.webank.databrain.dao.mapper;

import com.webank.databrain.dao.entity.DataSchemaTagsEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DataSchemaTagsMapper {

  @Insert(
      "INSERT INTO t_data_schema_tags("
          + "data_schema_id,"
          + "tag_id"
          + ") "
          + "VALUES("
          + "#{dataSchemaId}, "
          + "#{tagId}"
          + ")")
  @Options(useGeneratedKeys = true, keyProperty = "pkId", keyColumn = "pk_id")
  void insertDataSchemaTag(DataSchemaTagsEntity dataSchemaTagsEntity);

  @Select("select * from t_data_schema_tags where data_schema_id = #{schemaId}")
  @ResultType(DataSchemaTagsEntity.class)
  List<DataSchemaTagsEntity> getSchemaTagsMap(Long schemaId);

  @Select(
      "<script>"
          + "SELECT * FROM t_data_schema_tags where data_schema_id in"
          + "   <foreach item='id' index='index' collection='ids' open='(' separator=',' close=')'>"
          + "       #{id}"
          + "   </foreach>"
          + "</script>")
  @ResultType(DataSchemaTagsEntity.class)
  List<DataSchemaTagsEntity> getSchemaTagsMapByIds(List<Long> ids);

  @Delete(
      "<script>"
          + "delete from t_data_schema_tags where data_schema_id = #{schemaId}"
          + " tag_id in "
          + "   <foreach item='id' index='index' collection='tagIds' open='(' separator=',' close=')'>"
          + "       #{id}"
          + "   </foreach>"
          + "</script>")
  void delDataSchemaTag(@Param("tagIds") List<Long> tagIds, @Param("schemaId") Long schemaId);
}
