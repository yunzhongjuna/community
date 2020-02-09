package com.afstudy.springboot.mapper;

import com.afstudy.springboot.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("insert into user(name,account_id,token,gmt_create,gmt_modified) values(#{name},#{account_id},#{token},#{gmt_create},#{gmt_modified})")
     void insert(User user);

}
