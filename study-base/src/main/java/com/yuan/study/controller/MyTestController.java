package com.yuan.study.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yuan.study.common.Result;
import com.yuan.study.common.pojo.TestDto;
import com.yuan.study.entity.Test;
import com.yuan.study.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Api(tags = "测试接口")
@Slf4j
@RestController
public class MyTestController {

    @Autowired
    TestService testService;

    @GetMapping("/test01")
    public String test01() {
        log.debug("Debug");
        log.info("Info/信息");
        log.warn("Warn");
        log.error("Error");
        return "SUCCESS8";
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
