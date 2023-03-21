package com.webank.databrain.dao.mapper;

import com.webank.databrain.bo.HotProductBO;
import com.webank.databrain.dao.entity.DataSchemaInfoEntity;
import com.webank.databrain.dao.entity.ProductInfoEntity;
import com.webank.databrain.bo.ProductInfoBO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductInfoMapper {

    @Select("SELECT a.pk_id as productId, a.product_name,a.product_desc,a.status,a.review_time,a.create_time,c.company_name" +
            " FROM t_product_info a " +
            " LEFT JOIN t_company_info c ON a.provider_id = c.account_id " +
            " ORDER BY a.create_time DESC LIMIT #{start}, #{pageSize}")
    @ResultType(ProductInfoBO.class)
    List<ProductInfoBO> pageQueryProduct(@Param("start") long start, @Param("pageSize")int pageSize);

    @Select("SELECT c.company_name as productName, a.pk_id as productId FROM t_product_info a " +
            "JOIN t_account_info b ON a.provider_id = b.pk_id " +
            "JOIN t_company_info c ON a.provider_id = c.account_id " +
            "ORDER BY a.create_time DESC LIMIT 1, #{topN}")
    @ResultType(HotProductBO.class)
    List<HotProductBO> getHotProduct(@Param("topN") int topN);


    @Select("SELECT * FROM t_product_info where pk_id IN (#{ids})")
    List<ProductInfoEntity> getProductNameByIds(@Param("ids") List<Long> ids);


    @Insert("INSERT INTO t_product_info(" +
            "product_did," +
            "product_name," +
            "provider_id, " +
            "product_desc," +
            "status," +
            "review_time," +
            "create_time" +
            ") " +
            "VALUES(" +
            "#{productDid}, " +
            "#{productName}, " +
            "#{providerId}," +
            "#{productDesc}," +
            "#{status}," +
            "#{reviewTime}," +
            "#{createTime}" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "pkId", keyColumn = "pk_id")
    void insertProductInfo(ProductInfoEntity productInfoEntity);


    @Update("UPDATE t_product_info SET " +
            "product_name=#{productName}, " +
            "review_time=#{reviewTime}, " +
            "status=#{status}, " +
            "update_time=#{updateTime} " +
            "WHERE pk_id=#{pkId}")
    void updateProductInfo(ProductInfoEntity productInfoEntity);

    @Update("UPDATE t_product_info SET " +
            "review_time=#{reviewTime}, " +
            "status=#{status}, " +
            "update_time=#{updateTime} " +
            "WHERE pk_id=#{pkId}")
    void updateProductInfoState(ProductInfoEntity productInfoEntity);

    @Select("<script>" +
            "SELECT COUNT(*) " +
            " FROM t_product_info a " +
            " LEFT JOIN t_company_info b ON a.provider_id = b.account_id " +
            " LEFT JOIN t_account_info c ON b.account_id = c.pk_id " +
            " where 1=1" +
            "<if test='did != null'> AND c.did = #{did} </if> " +
            "</script>")
    int count(String did);

    @Select("SELECT a.pk_id as productId,  a.product_name,a.product_desc,a.status,a.review_time,a.create_time,c.company_name" +
            " FROM t_product_info a" +
            " left JOIN t_account_info b ON a.provider_id = b.pk_id" +
            " left JOIN t_company_info c ON a.provider_id = c.account_id" +
            " where a.pk_id = #{productId}")
    @ResultType(ProductInfoBO.class)
    ProductInfoBO getProductById(@Param("productId") Long productId);


    @Select("SELECT " +
            " * " +
            " from t_product_info " +
            " where pk_id = #{productId}")
    @ResultType(ProductInfoEntity.class)
    ProductInfoEntity getProductByProductId(Long productId);

    @Select("SELECT a.pk_id as productId, a.product_name,a.product_desc,a.status,a.review_time,a.create_time,c.company_name" +
            " FROM t_product_info a JOIN t_account_info b ON a.provider_id = b.pk_id" +
            " JOIN t_company_info c ON a.provider_id = c.account_id " +
            " where c.account_id = #{did}" +
            " ORDER BY a.create_time DESC LIMIT #{start}, #{pageSize}")
    @ResultType(ProductInfoBO.class)
    List<ProductInfoBO> pageQueryMyProduct(int offset, int pageSize, String did);
}
