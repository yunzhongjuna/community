package com.afstudy.springboot.mapper;

import com.afstudy.springboot.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("insert into user(name,account_id,token,gmt_create,gmt_modified) values(#{name},#{account_id},#{token},#{gmt_create},#{gmt_modified})")
     void insert(User user);
    //设计字符串参数时，需加@Param("token")
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
}
