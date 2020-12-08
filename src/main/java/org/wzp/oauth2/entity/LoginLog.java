/*
package org.wzp.oauth2.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.wzp.oauth2.util.DateUtil;
import org.wzp.oauth2.util.IdKeyUtil;

import java.io.Serializable;

*/
/**
 * 登录日志
 *
 * @Author: zp.wei
 * @DATE: 2020/11/11 10:32
 *//*

@Setter
@Getter
@Document(indexName = "login_log")
public class LoginLog implements Serializable {
    private static final long serialVersionUID = 3896464095380415632L;

    @Id
    private Long id;

    private Long createdAt = DateUtil.sysTime();

    private Long updatedAt = DateUtil.sysTime();

    private Long userId;

    private String username;

    private String detail;

    private String loginIp;


    public LoginLog() {
        this.id = IdKeyUtil.generateId();
    }

    public String getId() {
        return String.valueOf(id);
    }


}
*/


package org.wzp.oauth2.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: zp.wei
 * @DATE: 2020/11/11 10:32
 */
@ApiModel("登录日志")
@Setter
@Getter
public class LoginLog extends BaseModel {

    private static final long serialVersionUID = 3896464095380415632L;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("详情")
    private String detail;

    @ApiModelProperty("登录ip")
    private String loginIp;

    @ApiModelProperty("日志类型")
    private String loginLogEnum;

}

