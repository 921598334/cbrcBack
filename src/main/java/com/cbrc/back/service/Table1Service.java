package com.cbrc.back.service;


import com.cbrc.back.mapper.Table1Mapper;
import com.cbrc.back.mapper.TaskCompleteMapper;
import com.cbrc.back.mapper.TaskMapper;
import com.cbrc.back.model.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class Table1Service {



    @Autowired
    Table1Mapper table1Mapper;

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



    //在table1中插入任务，同时设置taskcomplete的完成状态
    public void insert(Table1 table1,String userid,String taskCompleteId){
        table1Mapper.insert(table1);

        //数据插入成功后，需要更新tableconplete的完成状态以及完成人，完成时间
        TaskComplete taskCompleteTmp = new TaskComplete();
        taskCompleteTmp.setId(Integer.parseInt(taskCompleteId));
        taskCompleteTmp.setUserid(Integer.parseInt(userid) );
        taskCompleteTmp.setCompletetime(table1.getDate());
        taskCompleteTmp.setIscomplete(1);

        taskCompleteService.update(taskCompleteTmp);

    }


    public List<Table1> query(Table1 table1,String fromDate,String endDate){
        //查询到某个文件类型、某个机构类型的数据，在table1表中
        List<Table1> table1List = table1Mapper.query(table1);

        //最终返回的数据
        List<Table1> resultTable1 = new ArrayList<>();

        //开始判断起止时间，然后寻找该机构名称，以及完成的任务
        for(Table1 table1Tmp : table1List){
            if(table1Tmp.getDate().compareTo(fromDate)>=0 && table1Tmp.getDate().compareTo(endDate)<=0){

                //查询管理机构名称,管理员姓名,填表人姓名，填表人电话
//                Userinfo userinfo = userinfoService.findById(table1Tmp.getUserid());
//                OrgInfo orgInfoTmp = new OrgInfo();
//                orgInfoTmp.setOrgid(userinfo.getOrgid());
//                OrgInfo orgInfo = orgInfoService.query(orgInfoTmp).get(0);
//                table1Tmp.setOrgName(orgInfo.getOrgname());
//                table1Tmp.setManager(orgInfo.getManager());
//                table1Tmp.setUserName(userinfo.getUsername());
//                table1Tmp.setTel(userinfo.getTelphone());
//
//
//                //查询任务标题
//                TaskComplete taskCompleteTmp = new TaskComplete();
//                taskCompleteTmp.setId(table1Tmp.getTaskcompleteid());
//                Integer taskId = taskCompleteMapper.query(taskCompleteTmp).get(0).getTaskid();
//                Task taskTmp = new Task();
//                taskTmp.setId(taskId);
//                Task task = taskService.query(taskTmp).get(0);
//                table1Tmp.setTaskTitle(task.getTasktitle());
//                table1Tmp.setPeriod(task.getPeriod());


                //查询管理者名称


                resultTable1.add(table1Tmp);
            }
        }

        return resultTable1;

    }




    //基础查询
    public List<Table1> query(Table1 table1){
        //查询到某个文件类型、某个机构类型的数据，在table1表中
        List<Table1> table1List = table1Mapper.query(table1);

        return table1List;

    }


    //基于List<TaskComplete>的id属性，在table1表中查询taskcompleteid中的数据
    public Table1 collectFind(List<String> taskCompleteListId){
        return table1Mapper.collectFind(taskCompleteListId);
    }





}
