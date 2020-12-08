package org.wzp.oauth2.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zp.wei
 * @DATE: 2020/8/29 10:25
 */
@ApiModel("角色权限")
@Data
public class RoleAuthority implements Serializable {
    private static final long serialVersionUID = 2979229337100949596L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long roleId;

    private Long authorityId;


}