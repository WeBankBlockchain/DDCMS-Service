package com.webank.ddcms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webank.ddcms.dao.entity.AccountInfoEntity;
import com.webank.ddcms.vo.common.CommonResponse;
import com.webank.ddcms.vo.request.account.*;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionException;

import java.io.IOException;

public interface AccountService {

  CommonResponse registerAccount(RegisterRequest request)
      throws TransactionException, JsonProcessingException;

  CommonResponse login(LoginRequest request);

  CommonResponse approveAccount(ApproveAccountRequest request) throws TransactionException;

  AccountInfoEntity loadAdminAccount() throws Exception;

  CommonResponse bindThirdParty(BindThirdPartyRequest request) throws IOException, InterruptedException;

  void initAdmin() throws Exception;
}
