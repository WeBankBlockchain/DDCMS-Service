package com.webank.databrain.dao.mapper;

import com.webank.databrain.bo.AccAndComInfoBO;
import com.webank.databrain.dao.entity.CompanyInfoEntity;
import org.apache.ibatis.annotations.*;
import java.util.List;


@Mapper
public interface CompanyInfoMapper {

    @Select("SELECT * FROM t_company_info a" +
            " left join " +
            " t_account_info c ON a.account_id = c.pk_id " +
            " where c.account_type = 1 and c.status = 2 " +
            " ORDER BY a.create_time DESC LIMIT 0, #{topCount}")
    List<CompanyInfoEntity> listHotCompanies(int topCount);

    @Select("SELECT a.*, c.* FROM t_company_info c JOIN t_account_info a ORDER BY c.create_time DESC LIMIT #{offset}, #{pageSize}")
    @ResultType(AccAndComInfoBO.class)
    List<AccAndComInfoBO> listCompanies(int offset, int pageSize);

    @Select("SELECT * FROM t_company_info c INNER JOIN t_account_info a ON c.account_id = a.pk_id WHERE a.user_name=#{userName}")
    @ResultType(AccAndComInfoBO.class)
    AccAndComInfoBO queryCompanyByUsername(String userName);

    @Select("<script>" +
            "SELECT a.*, c.* FROM t_company_info c INNER JOIN t_account_info a ON c.account_id = a.pk_id WHERE a.account_type &lt; 3 " +
            "<if test='status &gt; 0'> AND a.status = #{status} </if>" +
            "<if test='keyWord != null'> AND c.company_name LIKE CONCAT('%', #{keyWord}, '%') </if>" +
            "ORDER BY c.create_time DESC LIMIT #{offset}, #{pageSize}" +
            "</script>")
    @ResultType(AccAndComInfoBO.class)
    List<AccAndComInfoBO> listCompanyWithStatus(int status, String keyWord, long offset, int pageSize);

    @Insert("INSERT INTO t_company_info (account_id, company_name, company_desc, company_cert_type, company_cert_no, company_cert_file_uri, company_contact) VALUES(#{accountId},#{companyName},#{companyDesc},#{companyCertType},#{companyCertNo},#{companyCertFileUri},#{companyContact})")
    void insertCompany(CompanyInfoEntity companyInfoEntity);

    @Select("SELECT COUNT(1) FROM t_company_info")
    int totalCount();
}
