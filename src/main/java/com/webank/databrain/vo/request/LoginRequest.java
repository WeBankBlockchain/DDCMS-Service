package com.webank.databrain.vo.request;

import com.webank.databrain.vo.common.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginRequest extends CommonRequest {
    @NotBlank(message = "用户名不能为空.")
    private String userName;
    @NotBlank(message = "密码不能为空.")
    private String password;
}