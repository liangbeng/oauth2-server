package org.wzp.oauth2.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * email_msg_log
 *
 * @author
 */
@ApiModel("邮件日志记录")
@Data
public class EmailMsgLog extends BaseModel {

    private static final long serialVersionUID = -5434550757784857653L;


    @ApiModelProperty("邮件标题")
    private String title;

    @ApiModelProperty("邮件接受方")
    private String email;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("邮件格式")
    private String emailEnum;

    @ApiModelProperty("交换机")
    private String exchange;

    @ApiModelProperty("路由键")
    private String routingKey;

    @ApiModelProperty("状态: 0投递中 1投递成功 2投递失败")
    private Integer status = 0;

    @ApiModelProperty("重试次数")
    private Integer tryCount = 0;


}