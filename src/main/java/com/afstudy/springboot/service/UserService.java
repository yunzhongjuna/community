package com.afstudy.springboot.service;

import com.afstudy.springboot.mapper.UserMapper;
import com.afstudy.springboot.model.User;
import com.afstudy.springboot.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccount_idEqualTo(user.getAccount_id());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size()==0){
            //插入
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userMapper.insert(user);
        }else {
            //更新
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setGmt_modified(System.currentTimeMillis());
            updateUser.setName(user.getName());
            updateUser.setAvatar_url(user.getAvatar_url());
            updateUser.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
        }
    }
}
