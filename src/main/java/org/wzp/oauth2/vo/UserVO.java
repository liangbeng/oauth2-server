package org.wzp.oauth2.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: zp.wei
 * @DATE: 2020/10/12 9:48
 */
@ApiModel("用户VO")
@Data
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1881205447443932056L;

    @ApiModelProperty(value = "用户id", example = "1")
    private Long id;

    @ApiModelProperty("修改时间")
    private LocalDateTime updatedTime;

    @ApiModelProperty(value = "用户名", example = "123456")
    private String username;

    @ApiModelProperty(value = "密码", example = "e10adc3949ba59abbe56e057f20f883e")
    private String password;

    @ApiModelProperty(value = "姓名", example = "张三")
    private String name;

    @ApiModelProperty(value = "性别:未知，男，女",example = "男")
    private String sex;

    @ApiModelProperty(value = "电话", example = "15699996666")
    private String phone;

    @ApiModelProperty(value = "生日", example = "21312321323")
    private String birthday;

    @ApiModelProperty(value = "省", example = "四川")
    private String province;

    @ApiModelProperty(value = "市", example = "成都")
    private String city;

    @ApiModelProperty(value = "角色id", example = "[1,2,3]")
    private List<Long> roleIds;


}
