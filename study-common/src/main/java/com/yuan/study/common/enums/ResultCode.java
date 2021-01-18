package com.yuan.study.common.enums;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

@Getter
@ApiModel("结果枚举异常")
public enum ResultCode {
    SUCCESS(200, "操作成功"),

    //    #1000~1999 区间表示参数错误
    UNKNOWN_EXCEPTION(1000, "Exception异常"),
    PARAM_IS_INVALID(10001, "参数无效"),
    WX_CODE_INVALID(1002, "登录时获取的 code码无效"),
    PARAM_VALID_Exception(1003, "参数校验异常"),

    //    #2000~2999 区间表示用户错误
    USER_NOT_EXIST(2001, "用户不存在"),
    NO_VALID_TOKEN(2002, "没有有效的token"),
    PERMISSION_DENIED(2003, "当前权限不足"),
    //    #3000~3999 区间表示sql错误
    SQL_Exception(3001, "sql异常");

    private final Integer code;
    private final String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
