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
@ApiModel("用户角色")
@Data
public class UserRole implements Serializable {

    private static final long serialVersionUID = -4243021201827695530L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long roleId;

}