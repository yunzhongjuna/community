package com.afstudy.springboot.service;

import com.afstudy.springboot.mapper.UserMapper;
import com.afstudy.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbUser=userMapper.findByAccountId(user.getAccount_id());
        if(dbUser==null){
            //插入
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userMapper.insert(user);
        }else {
            //更新
            user.setGmt_modified(System.currentTimeMillis());
            user.setName(user.getName());
            user.setAvatar_url(user.getAvatar_url());
            user.setToken(user.getToken());
            userMapper.update(user);
        }
    }
}
