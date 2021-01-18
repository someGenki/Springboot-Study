package com.yuan.study.service;

import com.yuan.study.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author yuan
 * @since 2021-01-17
 */
public interface UserService extends IService<User>, UserDetailsService {

   UserDetails loadUserByUsername(String username);
}
