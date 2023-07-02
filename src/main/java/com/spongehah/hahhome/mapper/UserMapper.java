package com.spongehah.hahhome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spongehah.hahhome.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User getUserByUsernameAndPassword(@Param("username") String username,@Param("password") String password);
}
