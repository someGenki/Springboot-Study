package com.yuan.study.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yuan.study.common.lang.Result;
import com.yuan.study.common.pojo.TestDto;
import com.yuan.study.entity.Test;
import com.yuan.study.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Api(tags = "MP测试接口")
@Slf4j
@RestController
public class MPTestController {

    @Autowired
    TestService testService;

    @GetMapping("/test01")
    public Result test01() {
        System.out.println("成功");
        log.info("Info/信息");
        return Result.succ();
    }

    @GetMapping("/test02")
    public Result test02() {
        return Result.succ(LocalDateTime.now());
    }

    @GetMapping("/test03")
    public Result test03() {
        Test byId = testService.getById(1);
        return Result.succ(byId);
    }
    @GetMapping("/test04")
    @ApiImplicitParam(name = "money", value = "设置1号用户的价钱，0.00~100.00", required = true)
    public Result test04(Float money) {
        UpdateWrapper<Test> wrapper = new UpdateWrapper<>();
        testService.update(wrapper.eq("id", 1).set("money", money));
        return Result.succ(testService.getById(1));
    }
    @PostMapping("/test05")
    public Result test05(@Validated TestDto dto) {
        System.out.println(dto);
        return Result.succ();
    }
}
