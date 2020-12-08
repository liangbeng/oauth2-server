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
@ApiModel("权限VO")
@Data
public class AuthorityVO implements Serializable {
    private static final long serialVersionUID = 4130029249193116796L;

    @ApiModelProperty(value = "id", example = "1")
    private Long id;

    @ApiModelProperty("修改时间")
    private LocalDateTime updatedTime;

    @ApiModelProperty(value = "名字", example = "ROLE_USER")
    private String name;

}