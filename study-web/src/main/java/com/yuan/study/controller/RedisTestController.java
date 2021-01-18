package com.yuan.study.controller;

import com.yuan.study.service.TestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 引入依赖，配置地址密码，编写config
 * 主要就演示操作位图，可以用Another Redis Desktop Manager 查看
 * 其他用法见https://www.cnblogs.com/somegenki/p/13334936.html
 */
@Api(tags = "redis测试接口")
@RestController
public class RedisTestController {

    @Autowired
    TestService testService;

    @GetMapping("/redis01")
    public String redisTest01() {
        testService.RedisTest01();
        return "OK";
    }

    @GetMapping("/redis02")
    public String redisTest02() {
        testService.RedisTest02();
        return "OK";
    }

}
