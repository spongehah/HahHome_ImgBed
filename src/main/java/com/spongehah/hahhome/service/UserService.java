package com.spongehah.hahhome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spongehah.hahhome.pojo.User;

public interface UserService extends IService<User> {
    User getUserByUsernameAndPassword(String username, String password);
}
