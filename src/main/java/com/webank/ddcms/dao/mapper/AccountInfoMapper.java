package com.webank.ddcms.dao.mapper;

import com.webank.ddcms.dao.entity.AccountInfoEntity;
import org.apache.ibatis.annotations.*;

public interface AccountInfoMapper {

  @Update("UPDATE t_account_info SET status=#{status} WHERE did=#{did}")
  void updateStatus(String did, int status);

  @Insert(
      "INSERT INTO t_account_info (user_name, did, account_type, private_key, password, status) values(#{userName}, #{did}, #{accountType}, #{privateKey}, #{password}, #{status})")
  @Options(useGeneratedKeys = true, keyProperty = "pkId", keyColumn = "pk_id")
  int insertAccount(AccountInfoEntity entity);

  @Select("SELECT * FROM t_account_info WHERE did=#{did}")
  AccountInfoEntity selectByDid(String did);

  @Select("SELECT * FROM t_account_info WHERE user_name=#{userName}")
  @ResultType(AccountInfoEntity.class)
  AccountInfoEntity selectByUserName(String userName);

  @Select(
          "<script>"
                  + "SELECT COUNT(1) FROM t_account_info a INNER JOIN t_company_info c ON a.pk_id = c.account_id "
                  + "WHERE a.account_type &lt; 3 "
                  + "<if test='status &gt; 0'> AND a.status = #{status} </if>"
                  + "<if test='companyName != null'> AND c.company_name LIKE CONCAT('%', #{companyName}, '%') </if>"
                  + "</script>")
  int totalCountWithStatus(@Param("companyName") String companyName,@Param("status") Integer status);

  @Select("SELECT * FROM t_account_info  ORDER BY pk_id LIMIT 1")
  @ResultType(AccountInfoEntity.class)
  AccountInfoEntity selectTheFirstOne();
}
