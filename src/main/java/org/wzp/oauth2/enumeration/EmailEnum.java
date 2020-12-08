package org.wzp.oauth2.enumeration;

import lombok.Getter;

/**
 * @Author: zp.wei
 * @DATE: 2020/11/23 11:01
 */
@Getter
public enum EmailEnum {

    //普通邮件
    ORDINARY_EMAIL(0, "普通邮件"),

    //附件邮件
    ACCESSORY_EMAIL(1,"附件邮件");



    private Integer code;

    private String value;

    private void EmailEnum() {

    }

    private EmailEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
