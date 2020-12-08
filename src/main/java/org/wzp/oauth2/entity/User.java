package org.wzp.oauth2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.wzp.oauth2.util.DateUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @Author: zp.wei
 * @DATE: 2020/8/29 10:25
 */
@ApiModel("用户")
@Data
public class User extends BaseModel implements UserDetails {

    private static final long serialVersionUID = -2176447626846746697L;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    @JsonIgnore
//    @JSONField(serialize = false)
    private String password;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别，未知，男，女")
    private String sex;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "生日")
    private String birthday;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "是否激活")
    private Boolean enable = false;

    @ApiModelProperty(value = "最后登录时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "最后登录IP")
    private String lastLoginIp;

    @ApiModelProperty(value = "注册IP")
    private String registerIp;

    @JsonIgnore
    @Transient
    private Collection<GrantedAuthority> authorities;

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return enable;
    }

    @Transient
    private List<Authority> authorityList;


    /**
     * 将时间格式化后返回前端
     *
     * @return
     */
    public String getLastLoginTime() {
        if (lastLoginTime != null) {
            return DateUtil.formatLocalDateTime(lastLoginTime);
        }
        return null;
    }

}
