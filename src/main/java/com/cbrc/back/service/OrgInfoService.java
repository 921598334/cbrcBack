package com.cbrc.back.service;


import com.alibaba.fastjson.JSON;
import com.cbrc.back.mapper.OrgInfoMapper;
import com.cbrc.back.mapper.OrgTypeMapper;
import com.cbrc.back.model.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgInfoService {

    @Autowired
    OrgInfoMapper orgInfoMapper;

    @Autowired
    OrgTypeMapper orgTypeMapper;

    @Autowired
    TaskCompleteService taskCompleteService;

    @Autowired
    UserinfoService userinfoService;

    @Autowired
    TaskService taskService;

    public List<OrgInfo> query(OrgInfo orgInfo) {


        List<OrgInfo> orgInfoList =orgInfoMapper.query(orgInfo);


        //根据机构类型的id获取机构类型的中文名称
        for(OrgInfo orgInfoTmp:orgInfoList){

            OrgType orgTypeTmp = new OrgType();

            if(orgInfoTmp.getOrgtype()==null || orgInfoTmp.getOrgtype().equals("")){
                //表示没有通过机构类型id查询到机构类型
                orgInfoTmp.setOrgTypeName("未知机构类型："+orgInfoTmp.getOrgtype());
            }else{
                orgTypeTmp.setOrgtype(Integer.parseInt(orgInfoTmp.getOrgtype()));
                List<OrgType> orgTypes = orgTypeMapper.query(orgTypeTmp);
                if(orgTypes==null || orgTypes.size()==0){
                    //表示没有通过机构类型id查询到机构类型
                    orgInfoTmp.setOrgTypeName("未知机构类型："+orgInfoTmp.getOrgtype());
                }else{
                    orgInfoTmp.setOrgTypeName(orgTypes.get(0).getTypename());
                }
            }



        }


        return orgInfoList;

    }


    public void insert(OrgInfo orgInfo){
        orgInfoMapper.insert(orgInfo);
    }


    public void update(OrgInfo orgInfo){
        orgInfoMapper.update(orgInfo);
    }


    //删除公司同时会删除公司下的用户
    public void delete(OrgInfo orgInfo){

        Userinfo userinfoTmp = new Userinfo();
        userinfoTmp.setOrgid(orgInfo.getOrgid());
        List<Userinfo> userinfoList = userinfoService.query(userinfoTmp);

        //查询该公司下的所有用户
        for(Userinfo u:userinfoList){
            //该操作除了删除用户外还会删除用户完成过的taskcomplete，table1，table3
            userinfoService.delete(u);
        }

        //把taskcomplete中的数据也删除
        TaskComplete taskCompleteTmp = new TaskComplete();
        taskCompleteTmp.setOrgid(orgInfo.getOrgid());
        taskCompleteService.delete(taskCompleteTmp);


        //task中的orgtpe需要跟着删除
        Task taskTmp = new Task();
        taskTmp.setOrgtype("%\""+orgInfo.getOrgid()+"\"%");
        List<Task> tasks = taskService.query(taskTmp);

        //删除orgid
        for(Task t:tasks){
            List<String> orgTypes = JSON.parseArray( t.getOrgtype(),String.class);
            orgTypes.remove(orgInfo.getOrgid());
            String orgStr = JSON.toJSONString(orgTypes);
            t.setOrgtype(orgStr);
            taskService.update(t);
        }


        orgInfoMapper.delete(orgInfo);
    }


}
