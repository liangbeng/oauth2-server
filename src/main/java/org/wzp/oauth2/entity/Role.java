package org.wzp.oauth2.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: zp.wei
 * @DATE: 2020/8/29 10:25
 */
@ApiModel("角色")
@Data
public class Role extends BaseModel {

    private static final long serialVersionUID = 3593649054528796460L;

    @ApiModelProperty(value = "角色名字")
    private String roleName;

}