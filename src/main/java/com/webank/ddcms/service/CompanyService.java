package com.webank.ddcms.service;

import com.webank.ddcms.vo.common.CommonPageQueryRequest;
import com.webank.ddcms.vo.common.CommonResponse;
import com.webank.ddcms.vo.common.HotDataRequest;
import com.webank.ddcms.vo.request.account.QueryByUsernameRequest;
import com.webank.ddcms.vo.request.account.QueryCompanyByAccountIdRequest;
import com.webank.ddcms.vo.request.account.SearchAccountRequest;

public interface CompanyService {
  CommonResponse listHotCompanies(HotDataRequest request);

  CommonResponse listCompanyByPage(CommonPageQueryRequest request);

  CommonResponse getCompanyByUsername(QueryByUsernameRequest request);

  CommonResponse searchCompanies(SearchAccountRequest request);

  CommonResponse getCompanyByAccountId(QueryCompanyByAccountIdRequest request);
}
