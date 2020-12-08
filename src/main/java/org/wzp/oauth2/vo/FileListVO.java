package org.wzp.oauth2.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * file_list
 *
 * @author
 */
@ApiModel("文件列表VO")
@Data
public class FileListVO implements Serializable {

    private static final long serialVersionUID = -4831133267820203379L;

    @ApiModelProperty(value = "ID", example = "1")
    private Long id;

    @ApiModelProperty("修改时间")
    private LocalDateTime updatedTime;

    @ApiModelProperty(value = "文件名", example = "文件MD5后的md5值")
    private String fileName;

    @ApiModelProperty(value = "文件大小", example = "987981")
    private Long fileSize;

}