package com.webank.ddcms.service.impl;

import com.webank.ddcms.bo.AccAndComInfoBO;
import com.webank.ddcms.dao.entity.CompanyInfoEntity;
import com.webank.ddcms.dao.mapper.AccountInfoMapper;
import com.webank.ddcms.dao.mapper.CompanyInfoMapper;
import com.webank.ddcms.service.CompanyService;
import com.webank.ddcms.vo.common.CommonPageQueryRequest;
import com.webank.ddcms.vo.common.CommonResponse;
import com.webank.ddcms.vo.common.HotDataRequest;
import com.webank.ddcms.vo.common.PageListData;
import com.webank.ddcms.vo.request.account.QueryByUsernameRequest;
import com.webank.ddcms.vo.request.account.QueryCompanyByAccountIdRequest;
import com.webank.ddcms.vo.request.account.SearchAccountRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

  @Autowired private CompanyInfoMapper companyInfoMapper;
  @Autowired private AccountInfoMapper accountInfoMapper;

  @Override
  public CommonResponse listHotCompanies(HotDataRequest request) {
    List<CompanyInfoEntity> companyInfoEntityList =
        companyInfoMapper.listHotCompanies(request.getTopCount());
    return CommonResponse.success(companyInfoEntityList);
  }

  @Override
  public CommonResponse listCompanyByPage(CommonPageQueryRequest request) {

    int totalCount = companyInfoMapper.totalCount();
    int pageCount = (int) Math.ceil(1.0 * totalCount / request.getPageSize());

    PageListData pageListData = new PageListData<>();
    pageListData.setPageCount(pageCount);
    pageListData.setTotalCount(totalCount);

    int offset = (request.getPageNo() - 1) * request.getPageSize();

    List<AccAndComInfoBO> companyInfoEntityList =
        companyInfoMapper.listCompanies(offset, request.getPageSize());
    pageListData.setItemList(companyInfoEntityList);
    return CommonResponse.success(pageListData);
  }

  @Override
  public CommonResponse getCompanyByUsername(QueryByUsernameRequest request) {
    AccAndComInfoBO accAndComInfoBO =
        companyInfoMapper.queryCompanyByUsername(request.getUserName());
    return CommonResponse.success(accAndComInfoBO);
  }

  @Override
  public CommonResponse searchCompanies(SearchAccountRequest request) {

    int totalCount =
        accountInfoMapper.totalCountWithStatus(
            request.getKeyWord(), Integer.parseInt(request.getAccountStatus()));
    int pageCount = (int) Math.ceil(1.0 * totalCount / request.getPageSize());

    PageListData pageListData = new PageListData<>();
    pageListData.setPageCount(pageCount);
    pageListData.setTotalCount(totalCount);

    int offset = (request.getPageNo() - 1) * request.getPageSize();

    List<AccAndComInfoBO> boList =
        companyInfoMapper.listCompanyWithStatus(
            Integer.parseInt(request.getAccountStatus()),
            request.getKeyWord(),
            (long) offset,
            request.getPageSize());
    pageListData.setItemList(boList);

    for(int i=0;i<boList.size();i++) {
      boList.get(i).setPrivateKey(null);
    }
    return CommonResponse.success(pageListData);
  }

  @Override
  public CommonResponse getCompanyByAccountId(QueryCompanyByAccountIdRequest request) {
    AccAndComInfoBO accAndComInfoBO =
        companyInfoMapper.queryCompanyByAccountId(request.getAccountId());
    accAndComInfoBO.setPrivateKey("");
    return CommonResponse.success(accAndComInfoBO);
  }
}
