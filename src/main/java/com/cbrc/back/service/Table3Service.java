package com.cbrc.back.service;


import com.cbrc.back.mapper.Table1Mapper;
import com.cbrc.back.mapper.Table3Mapper;
import com.cbrc.back.mapper.TaskCompleteMapper;
import com.cbrc.back.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class Table3Service {



    @Autowired
    Table3Mapper table3Mapper;


    @Autowired
    OrgTypeService orgTypeService;

    @Autowired
    OrgInfoService orgInfoService;

    @Autowired
    TaskCompleteService taskCompleteService;

    @Autowired
    TaskCompleteMapper taskCompleteMapper;

    @Autowired
    TaskService taskService;

    @Autowired
    UserinfoService userinfoService;

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");


    public void insert(Table3 table3,String userid,String taskCompleteId){
        table3Mapper.insert(table3);


        TaskComplete taskCompleteTmp = new TaskComplete();
        taskCompleteTmp.setId(Integer.parseInt(taskCompleteId));
        taskCompleteTmp.setUserid(Integer.parseInt(userid) );
        taskCompleteTmp.setCompletetime(dateformat.format(System.currentTimeMillis()));
        taskCompleteTmp.setIscomplete(1);

        taskCompleteService.update(taskCompleteTmp);
    }




    public List<Table3> query(Table3 table3,String fromDate,String endDate){
        //查询到某个文件类型、某个机构类型的数据，在table3表中
        List<Table3> table3List = table3Mapper.query(table3);

        //最终返回的数据
        List<Table3> resultTable3 = new ArrayList<>();

        //开始判断起止时间，然后寻找该机构名称，以及完成的任务
        for(Table3 table3Tmp : table3List){
            if(table3Tmp.getDate().compareTo(fromDate)>=0 && table3Tmp.getDate().compareTo(endDate)<=0){

                //查询管理机构名称,管理员姓名,填表人姓名，填表人电话
                Userinfo userinfo = userinfoService.findById(table3Tmp.getUserid());
                OrgInfo orgInfoTmp = new OrgInfo();
                orgInfoTmp.setOrgid(userinfo.getOrgid());
                OrgInfo orgInfo = orgInfoService.query(orgInfoTmp).get(0);
                table3Tmp.setOrgName(orgInfo.getOrgname());
                table3Tmp.setManager(orgInfo.getManager());
                table3Tmp.setUserName(userinfo.getUsername());
                table3Tmp.setTel(userinfo.getTelphone());


                //查询任务标题
                TaskComplete taskCompleteTmp = new TaskComplete();
                taskCompleteTmp.setId(table3Tmp.getTaskcompleteid());
                Integer taskId = taskCompleteMapper.query(taskCompleteTmp).get(0).getTaskid();
                Task taskTmp = new Task();
                taskTmp.setId(taskId);
                Task task = taskService.query(taskTmp).get(0);
                table3Tmp.setTaskTitle(task.getTasktitle());
                table3Tmp.setPeriod(task.getPeriod());


                resultTable3.add(table3Tmp);
            }
        }

        return resultTable3;

    }








}
