package com.spongehah.hahhome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spongehah.hahhome.mapper.UserMapper;
import com.spongehah.hahhome.pojo.User;
import com.spongehah.hahhome.service.UserService;
import com.spongehah.hahhome.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        return userMapper.getUserByUsernameAndPassword(username, MD5Utils.getMD5(password));
    }
}
