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
            "SELECT COUNT(1) FROM t_account_info WHERE account_type = #{accountType} " +
            "<if test='status &gt; 0'> AND status = #{status} </if>" +
            "</script>")
    int totalCountWithStatus(int accountType, int status);
}
