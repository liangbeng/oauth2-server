package org.wzp.oauth2.enumeration;

/**
 * @Author: zp.wei
 * @DATE: 2020/9/1 10:31
 */
public enum ResultCodeEnum {

    RESULT_SUCCESS(true, 0, "请求成功"),
    BUSINESS_FAIL(false, 1, "业务处理失败"),
    FORBIDDEN(false, 400, "授权失败"),
    UNAUTHORIZED(false, 401, "未经授权的请求"),
    INVALID_TOKEN(false, 402, "无效的token"),
    ACCESS_DENIED(false, 403, "无权访问"),

    SYSTEM_ERROR(false, 100001, "系统错误"),

    JSON_PARSE_ERROR(false, 20001, "json解析异常"),
    PARAM_ERROR(false, 20002, "请求参数不正确"),
    LACK_NEEDS_PARAM(false, 20003, "缺少必要的参数"),
    ERROR_USERNAME_OR_PASSWORD(false, 20004, "账户名或密码错误"),
    USER_NOT_ENABLE(false, 20005, "用户未激活"),
    HAS_USER(false, 20006, "该用户名已存在"),
    OLD_PASSWORD_ERROR(false, 20007, "旧密码错误，请重新输入"),
    NEW_PASSWORD_HAS_SAME(false, 20008, "新旧密码相同，请重新输入新密码"),
    PASSWORD_HAS_SAME(false, 20009, "新旧密码相同，请重新输入新密码"),
    ERROR_EXCEL_DOWNLAND(false, 20010, "excel导出错误，请重新导出"),
    AUTHORITY_HAS_USE(false, 20011, "该权限有角色在使用，不能删除"),
    FILE_NOT_EXIST(false, 20012, "该文件不存在"),
    EXCEL_SUCCESS_IMPORT(true, 20013, "excel导入成功"),
    FILE_NOT_EXISTS(false, 20014, "文件不存在"),
    CHUNK_EXISTS(false,20015, "分片已存在"),
    CHUNK_NOT_EXISTS(false,20016, "分片不存在"),
    FILE_UPLOAD_ERROR(false,20017, "文件上传错误"),
    CHUNK_MERGE_FAIL(false,20018, "分片合并失败"),
    UPLOAD_FAIL(false,20019, "上传失败"),
    UPLOADING(false,20020, "上传中..."),
    CHUNK_UPLOAD_ERROR(false,20021, "分片上传错误");

    private Boolean success;

    private Integer code;

    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }


}
