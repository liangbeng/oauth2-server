package org.wzp.oauth2.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.wzp.oauth2.entity.BaseModel;
import org.wzp.oauth2.enumeration.EmailEnum;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * msg_log
 *
 * @author
 */
@ApiModel("邮件记录VO")
@Data
public class EmailMsgLogVO implements Serializable {

    private static final long serialVersionUID = -6763182996327459904L;


    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty("修改时间")
    private LocalDateTime updatedTime;

    @ApiModelProperty(value = "邮件标题")
    private String title;

    @ApiModelProperty(value = "邮件接受方")
    private String email;

    @ApiModelProperty(value = "消息体")
    private String content;

    @ApiModelProperty(value = "邮件格式")
    private EmailEnum emailEnum;

    @ApiModelProperty(value = "文件url")
    private String filePath;

}