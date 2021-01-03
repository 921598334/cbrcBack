package com.cbrc.back.service;


import com.cbrc.back.mapper.TaskCompleteMapper;
import com.cbrc.back.mapper.TaskMapper;
import com.cbrc.back.model.Task;
import com.cbrc.back.model.TaskComplete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskCompleteService {



    @Autowired
    TaskCompleteMapper taskCompleteMapper;

    @Autowired
    TaskMapper taskMapper;

    //添加
    public void insert(TaskComplete taskComplete){
        taskCompleteMapper.insert(taskComplete);
    }


    //更新
    public void update(TaskComplete taskComplete){
        taskCompleteMapper.update(taskComplete);
    }


    //查询
    public List<TaskComplete> query(TaskComplete taskComplete,String fromDate,String endDate){

        //查询到机构id下的所有任务
        List<TaskComplete> taskCompletes = taskCompleteMapper.query(taskComplete);

        //根据任务id获取任务详情
        for(TaskComplete taskCompleteTmp :taskCompletes){

            Task task = new Task();
            task.setId(taskCompleteTmp.getTaskid());
            Task taskTmp = taskMapper.query(task).get(0);

            taskCompleteTmp.setTaskinfo(taskTmp);
        }


        List<TaskComplete> returnTaskCompletes = new ArrayList<>();

        //判断任务创建时间是否在查询的开始时间与结束时间访问内
        for(TaskComplete taskCompleteTmp :taskCompletes){

            String createTime = taskCompleteTmp.getTaskinfo().getCreatetime();

            if(createTime.compareTo(fromDate)>=0 && createTime.compareTo(endDate)<=0){
                returnTaskCompletes.add(taskCompleteTmp);
            }
        }

        return returnTaskCompletes;

    }





}
