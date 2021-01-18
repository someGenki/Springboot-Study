package com.yuan.study.controller;

import com.yuan.study.common.lang.Result;
import com.yuan.study.common.pojo.LoginDto;
import com.yuan.study.entity.User;
import com.yuan.study.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "security测试接口")
@RestController
public class SecurityController {

    @Autowired
    JwtUtil jwtUtil;


    @ApiOperation("用户登录")
    @PostMapping("/pub/login")
    public Result doLogin(LoginDto dto) {
        System.out.println(dto);
        return Result.succ(jwtUtil.generateToken(dto.getUsername()));
    }
    @GetMapping("/secu01")
    public  Result test01(){
        User p = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(p);
        return Result.succ(p);
    }

    @GetMapping("/user/secu02")
    public  Result test02(){

        return Result.succ("user");
    }
    @GetMapping("/admin/secu03")
    public  Result test03(){
        return Result.succ("admin");
    }
}
