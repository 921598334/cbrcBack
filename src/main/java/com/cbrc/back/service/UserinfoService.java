package com.cbrc.back.service;


import com.cbrc.back.mapper.UserinfoMapper;
import com.cbrc.back.model.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserinfoService {

    @Autowired
    UserinfoMapper userinfoMapper;



    //登陆时判断用户的用户名和密码时候相同
    public Userinfo checkUser(Userinfo user){
        return userinfoMapper.check(user);
    }

    //根据用户名寻找用户，在注册时用于判断当前用户是否存在
    public Userinfo findByUserName(String userName){
        return userinfoMapper.findByUserName(userName);
    }


    public Userinfo findById(Integer user_id) {
        return userinfoMapper.findById(user_id);
    }
}
