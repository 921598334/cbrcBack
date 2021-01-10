package com.cbrc.back.service;


import com.cbrc.back.mapper.Table1Mapper;
import com.cbrc.back.mapper.Table3Mapper;
import com.cbrc.back.mapper.UserinfoMapper;
import com.cbrc.back.model.TaskComplete;
import com.cbrc.back.model.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserinfoService {

    @Autowired
    UserinfoMapper userinfoMapper;

    @Autowired
    Table1Mapper table1Mapper;

    @Autowired
    Table3Mapper table3Mapper;

    @Autowired
    TaskCompleteService taskCompleteService;


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


    public String update(Userinfo userinfo){

        //更新用户之前先检查下用户名是否存在
        Userinfo userinfoTmp = new Userinfo();

        //当用户确实是要更新用户名时
        if(userinfo.getUsername()!=null && !userinfo.getUsername().equals("")){

            userinfoTmp.setUsername(userinfo.getUsername());
            List<Userinfo> oldUserinfoList = userinfoMapper.query(userinfoTmp);

            int count = 0;
            //检查查询到到该用户名到id，如果除去本身的id外，还有其他的用户名，那就重名了，不能更新，否则可以更新
            for(Userinfo u:oldUserinfoList){

                if(u.getUserid().equals(userinfo.getUserid())){
                   continue;
                }
                ++count;
            }


            if(count==0){
                userinfoMapper.update(userinfo);
            }else{
                //除去自身之外，还有其他同名的用户名，就不能添加了
                return "该用户名已被其他用户使用，请更换用户名";
            }

        }else{
            userinfoMapper.update(userinfo);
        }


        return "S";


    }


    //删除用户同时还需要删除该用户完成过的任务,把taskcomplete的状态设置为1
    public void delete(Userinfo userinfo){


        table1Mapper.deleteByUserId(userinfo.getUserid());
        table3Mapper.deleteByUserId(userinfo.getUserid());


        TaskComplete taskCompleteTmp = new TaskComplete();
        //根据userid查询记录
        taskCompleteTmp.setUserid(userinfo.getUserid());
        List<TaskComplete> taskCompletes = taskCompleteService.query(taskCompleteTmp);

        for(TaskComplete t: taskCompletes){
            t.setIscomplete(0);
            t.setCompletetime("");
            t.setUserid(0);
            taskCompleteService.update(t);

        }



        //用户完成过的任务记录都删除后，最后删除用户
        userinfoMapper.delete(userinfo);
    }

    public String insert(Userinfo userinfo){

        //插入用户之前先检查下用户名是否存在
        Userinfo userinfoTmp = new Userinfo();
        userinfoTmp.setUsername(userinfo.getUsername());
        List<Userinfo> userinfoList = userinfoMapper.query(userinfoTmp);

        if(userinfoList.size()>=1){

            //当前用户名已经存在了
            return "当前用户名已经存在,请更换用户名";

        }else{
            userinfoMapper.insert(userinfo);
            return "S";
        }
    }

    public List<Userinfo> query(Userinfo userinfo){
        return userinfoMapper.query(userinfo);
    }

}
