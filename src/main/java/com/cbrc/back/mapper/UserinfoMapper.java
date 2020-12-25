package com.cbrc.back.mapper;


import com.cbrc.back.model.Userinfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserinfoMapper {



    //查到的数据可以直接封装为类的条件是类的属性顺序、名称（包括构造函数的参数列表）要与数据库的属性、名称顺序一致
    @Select("select * from userinfo where token= #{token}")
    Userinfo findByToken(@Param("token") String token);

    @Select("select * from userinfo where userid= #{userid}")
    Userinfo findById(@Param("userid") Integer userid);

    //这个用来检查用户是否存在
    @Select("select * from userinfo where name= #{username}")
    Userinfo findByUserName(@Param("name") String username);



    //检查用户名和密码是否匹配
    @Select("select * from userinfo where username= #{username} and password = #{password}")
    Userinfo check(Userinfo user);
}
