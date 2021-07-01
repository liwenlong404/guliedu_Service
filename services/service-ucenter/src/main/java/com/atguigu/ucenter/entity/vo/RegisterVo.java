package com.atguigu.ucenter.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author li
 * @create 2021-06-03 14:24
 */
@Data
public class RegisterVo {
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "邮箱")
    private String mail;
}
