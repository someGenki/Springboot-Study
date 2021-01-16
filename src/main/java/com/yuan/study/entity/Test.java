package com.yuan.study.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 测试用表
 *
 * @author yuan
 * @since 2021-01-16
 */
@Data
@EqualsAndHashCode
@TableName("t_test")
@ApiModel(value = "Test对象", description = "测试用表")
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private BigDecimal money;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;


}
