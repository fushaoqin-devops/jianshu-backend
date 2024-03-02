package com.fushaoqin.jianshu.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("follow")
public class Follow extends BaseEntity {

    @TableField("user_id")
    private Long userId;

    @TableField("follow_id")
    private Long followId;

}
