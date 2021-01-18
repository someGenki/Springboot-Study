package com.yuan.study.service;

import com.yuan.study.entity.Test;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 测试用表 服务类
 * </p>
 *
 * @author yuan
 * @since 2021-01-17
 */
public interface TestService extends IService<Test> {

    void RedisTest01();
    void RedisTest02();
}
