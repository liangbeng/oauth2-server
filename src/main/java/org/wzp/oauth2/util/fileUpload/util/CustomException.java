package org.wzp.oauth2.util.fileUpload.util;


import org.wzp.oauth2.enumeration.ResultCodeEnum;

/**
 * 自定义异常
 */
public class CustomException extends Exception {
    private int code;

    public CustomException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    public int getCode() {
        return code;
    }
}
