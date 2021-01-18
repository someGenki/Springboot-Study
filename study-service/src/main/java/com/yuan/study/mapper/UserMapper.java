package com.yuan.study.mapper;

import com.yuan.study.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author yuan
 * @since 2021-01-17
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from t_user where username =#{username} ")
    User findByUsername(String username);

}
