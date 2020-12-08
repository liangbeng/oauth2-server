package org.wzp.oauth2.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: zp.wei
 * @DATE: 2020/10/15 10:25
 */
@ApiModel("文件列表")
@Data
public class FileList extends BaseModel {

    private static final long serialVersionUID = -4831133267820200579L;

    @ApiModelProperty("文件名")
    private String fileName;

    @ApiModelProperty("文件大小")
    private Long fileSize;

    @ApiModelProperty("0：未删除 1：已删除")
    private Boolean removed;

}