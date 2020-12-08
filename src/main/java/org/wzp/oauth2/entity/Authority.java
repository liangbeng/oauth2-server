package org.wzp.oauth2.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: zp.wei
 * @DATE: 2020/8/29 10:25
 */
@ApiModel("权限")
@Data
public class Authority extends BaseModel {

    private static final long serialVersionUID = 956313861222502466L;

    @ApiModelProperty(value = "权限名字")
    private String name;

}