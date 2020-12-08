package org.wzp.oauth2.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.wzp.oauth2.util.DateUtil;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: zp.wei
 * @DATE: 2020/10/28 15:43
 */
@Data
public class BaseModel implements Serializable {

    private static final long serialVersionUID = -6763183603127456104L;


    @ApiModelProperty("ID")
    @TableId(type = IdType.AUTO) // id自增
    private Long id;

    @ApiModelProperty("创建时间")
//    @JsonSerialize(using = LocalDateTimeSerializer.class)  //处理redis中对于LocalDateTime的jackson序列化
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class) //处理redis中对于LocalDateTime的jackson反序列化
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @ApiModelProperty("修改时间")
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;


    /**
     * 将时间格式化后返回前端
     *
     * @return
     */
    public String getCreatedTime() {
        if (createdTime != null) {
            return DateUtil.formatLocalDateTime(createdTime);
        }
        return null;
    }


    public String getUpdatedTime() {
        if (updatedTime != null) {
            return DateUtil.formatLocalDateTime(updatedTime);
        }
        return null;
    }


}
