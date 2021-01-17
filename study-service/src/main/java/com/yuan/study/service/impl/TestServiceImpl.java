package com.yuan.study.service.impl;

import com.yuan.study.entity.Test;
import com.yuan.study.mapper.TestMapper;
import com.yuan.study.service.TestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测试用表 服务实现类
 * </p>
 *
 * @author yuan
 * @since 2021-01-17
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {

}
