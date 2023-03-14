package com.webank.databrain.dao.mapper;

import com.webank.databrain.dao.entity.AccountInfoEntity;
import org.apache.ibatis.annotations.*;

public interface AccountInfoMapper {

    @Update("UPDATE t_account_info SET status=#{status} WHERE did=#{did}")
    void updateStatus(String did, int status);

    @Insert("INSERT INTO t_account_info (user_name, did, account_type, private_key, salt, pwd_hash, status) values(#{userName}, #{did}, #{accountType}, #{privateKey}, #{salt}, #{pwdHash}, #{status})")
    @Options(useGeneratedKeys=true, keyProperty="pkId", keyColumn="pk_id")
    int insertAccount(AccountInfoEntity entity);

    @Select("SELECT * FROM t_account_info WHERE did=#{did}")
    AccountInfoEntity selectByDid(String did);

    @Select("SELECT * FROM t_account_info WHERE user_name=#{userName}")
    @ResultType(AccountInfoEntity.class)
    AccountInfoEntity selectByUserName(String userName);

    @Select("<script>" +
            "SELECT COUNT(1) FROM t_account_info a " +
            "<if test='accountType == 0'> INNER JOIN t_person_info p ON a.pk_id = p.account_id </if>" +
            "<if test='accountType == 1'> LEFT JOIN t_company_info c ON a.pk_id = c.account_id </if>" +
            "WHERE 1 = 1" +
            "<if test='status &gt; 0'> AND a.account_type = #{accountType} </if>" +
            "<if test='keyWord != null and accountType == 0'> AND p.person_name LIKE CONCAT('%', #{keyWord}, '%') </if>" +
            "<if test='keyWord != null and accountType == 1'> AND c.company_name LIKE CONCAT('%', #{keyWord}, '%') </if>" +
            "</script>")
    int totalCountWithStatus(int accountType, String keyWord, int status);
}
