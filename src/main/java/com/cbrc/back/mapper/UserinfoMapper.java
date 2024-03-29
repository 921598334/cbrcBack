package com.cbrc.back.mapper;


import com.cbrc.back.model.Userinfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserinfoMapper {



    //查到的数据可以直接封装为类的条件是类的属性顺序、名称（包括构造函数的参数列表）要与数据库的属性、名称顺序一致
    //@Select("select * from userinfo where token= #{token}")
    Userinfo findByToken( String token);

    //@Select("select * from userinfo where userid= #{userid}")
    Userinfo findById(  Integer userid);

    //这个用来检查用户是否存在
    //@Select("select * from userinfo where name= #{username}")
    Userinfo findByUserName(  String username);



    //检查用户名和密码是否匹配
    //@Select("select * from userinfo where username= #{username} and password = #{password}")
    Userinfo check(Userinfo user);



    //更新用户信息
    //@Update("UPDATE userinfo SET username = #{username}, truename = #{truename},  password = #{password}, orgid = #{orgid}, state = #{state}, token = #{token}, modified = #{modified} where userid = #{userid}")
    void update(Userinfo user);


    void delete(Userinfo user);

    void insert(Userinfo user);

    List<Userinfo> query(Userinfo user);

}
