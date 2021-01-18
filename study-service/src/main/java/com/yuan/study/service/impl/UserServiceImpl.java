package com.yuan.study.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.study.entity.User;
import com.yuan.study.mapper.UserMapper;
import com.yuan.study.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        System.out.println(user);
        if (user == null) {  //如果数据库里没有用户名，则认证失败
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }
}
