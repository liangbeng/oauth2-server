package org.wzp.oauth2.enumeration;

import lombok.Getter;

/**
 * @Author: zp.wei
 * @DATE: 2020/11/16 16:17
 */
@Getter
public enum LoginLogEnum {

    //登录
    LOGIN(0, "登录"),

    //退出
    LOGIN_OUT(1, "退出");

    private Integer code;

    private String value;

    private void LoginLogEnum() {

    }

    private LoginLogEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
