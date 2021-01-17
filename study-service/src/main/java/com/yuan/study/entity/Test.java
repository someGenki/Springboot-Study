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
 * <p>
 * 测试用表
 * </p>
 *
 * @author yuan
 * @since 2021-01-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_test")
@ApiModel(value="Test对象", description="测试用表")
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
