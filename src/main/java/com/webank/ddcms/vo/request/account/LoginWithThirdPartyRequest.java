package com.webank.ddcms.vo.request.account;

import com.webank.ddcms.enums.ThirdPartyType;
import com.webank.ddcms.vo.common.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 使用第三方账号登录请求
 *
 * @author ashinnotfound
 * @date 2023/08/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginWithThirdPartyRequest extends CommonRequest {
    /**
     * 账号类型
     */
    @NotNull
    private ThirdPartyType type;

    /**
     * 登录后回调获得的code
     */
    private String code;
}
