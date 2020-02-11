package com.afstudy.springboot.mapper;

import com.afstudy.springboot.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("insert into user(name,account_id,token,gmt_create,gmt_modified,avatar_url) values(#{name},#{account_id},#{token},#{gmt_create},#{gmt_modified},#{avatar_url})")
     void insert(User user);

    //设计字符串参数时，需加@Param("token")
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id")Integer id);

    @Select("select * from user where account_id = #{account_id}")
    User findByAccountId(@Param("account_id")String account_id);

    @Update("update user set name =#{name},token =#{token},gmt_modified =#{gmt_modified},avatar_url =#{avatar_url} where account_id = #{account_id}")
    void update(User user);
}
