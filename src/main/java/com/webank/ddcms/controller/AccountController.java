package com.webank.ddcms.controller;

import com.webank.ddcms.enums.ThirdPartyType;
import com.webank.ddcms.service.AccountService;
import com.webank.ddcms.service.CompanyService;
import com.webank.ddcms.vo.common.CommonPageQueryRequest;
import com.webank.ddcms.vo.common.CommonResponse;
import com.webank.ddcms.vo.common.HotDataRequest;
import com.webank.ddcms.vo.request.account.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/api/account")
@Slf4j
public class AccountController {

  @Autowired private AccountService accountService;

  @Autowired private CompanyService companyService;

  @PostMapping("register")
  public CommonResponse register(@RequestBody @Valid RegisterRequest request) throws Exception {
    return accountService.registerAccount(request);
  }

  @PostMapping("login")
  public CommonResponse login(@RequestBody @Valid LoginRequest request) {
    return accountService.login(request);
  }

  @PostMapping("approveAccount")
  public CommonResponse approveAccount(@RequestBody @Valid ApproveAccountRequest request)
      throws Exception {
    return accountService.approveAccount(request);
  }

  @PostMapping("getHotCompanies")
  public CommonResponse getHotCompanies(@RequestBody @Valid HotDataRequest request) {
    return companyService.listHotCompanies(request);
  }

  @PostMapping("/pageQueryCompany")
  public CommonResponse pageQueryCompany(@RequestBody @Valid CommonPageQueryRequest request) {
    return companyService.listCompanyByPage(request);
  }

  @PostMapping("queryCompanyByUsername")
  public CommonResponse queryCompanyByUsername(@RequestBody @Valid QueryByUsernameRequest request) {
    return companyService.getCompanyByUsername(request);
  }

  @PostMapping("queryCompanyByAccountId")
  public CommonResponse queryCompanyByAccountId(
      @RequestBody @Valid QueryCompanyByAccountIdRequest request) {
    return companyService.getCompanyByAccountId(request);
  }

  @PostMapping("searchCompany")
  public CommonResponse searchCompanies(@RequestBody @Valid SearchAccountRequest request) {
    return companyService.searchCompanies(request);
  }

  /**
   * 绑定第三方账号
   * @param code 第三方授权后获得的code
   * @param type 第三方类型
   * @return {@code CommonResponse}
   * @throws IOException
   * @throws InterruptedException
   */
  @GetMapping("bindThirdParty")
  public CommonResponse bindThirdParty(@RequestParam("code") String code, @RequestParam("type") int type) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    BindThirdPartyRequest request = new BindThirdPartyRequest();
    request.setCode(code);
    request.setType(ThirdPartyType.getThirdPartyType(type));
    return accountService.bindThirdParty(request);
  }

  @GetMapping("loginWithThirdParty")
  public CommonResponse loginWithThirdParty(@RequestParam("code") String code, @RequestParam("type") int type) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    LoginWithThirdPartyRequest request = new LoginWithThirdPartyRequest();
    request.setCode(code);
    request.setType(ThirdPartyType.getThirdPartyType(type));
    return accountService.loginWithThirdParty(request);
  }
}
