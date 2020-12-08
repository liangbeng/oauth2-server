package org.wzp.oauth2.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: zp.wei
 * @DATE: 2020/8/29 10:25
 */
@ApiModel("角色VO")
@Data
public class RoleVO implements Serializable {
    private static final long serialVersionUID = 3593643054528796460L;

    @ApiModelProperty(value = "ID", example = "1")
    private Long id;

    @ApiModelProperty("修改时间")
    private LocalDateTime updatedTime;

    @ApiModelProperty(value = "角色名字", example = "二级管理员")
    private String roleName;

}