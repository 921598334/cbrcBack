package com.cbrc.back.service;


import com.cbrc.back.mapper.*;
import com.cbrc.back.model.OrgInfo;
import com.cbrc.back.model.OrgType;
import com.cbrc.back.model.Task;
import com.cbrc.back.model.TaskComplete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {



    @Autowired
    TaskMapper taskMapper;

    @Autowired
    TaskCompleteMapper taskCompleteMapper;

    @Autowired
    Table1Mapper table1Mapper;

    @Autowired
    Table3Mapper table3Mapper;

    //添加,添加后返回
    public void insert(Task task){
        taskMapper.insert(task);

    }


    //查询所有任务
    public List<Task> query(Task task){
        return  taskMapper.query(task);
    }


    //删除任务时，还需要删除taskcomplete，以及table1，table3
    public void delete(String id){
        //首先删除任务
        taskMapper.delete(id);

        //查询对应的 taskComplete，一个任务下可能有多个taskComplete
        TaskComplete taskCompleteTmp = new TaskComplete();
        taskCompleteTmp.setTaskid(Integer.parseInt(id));
        List<TaskComplete> taskCompletes = taskCompleteMapper.query(taskCompleteTmp);

        //然后根据taskCompletes的id删除table1和table3表中的数据
        for(TaskComplete taskComplete:taskCompletes){
            table1Mapper.deleteByTaskCompleteId(taskComplete.getId()+"");
            table3Mapper.deleteByTaskCompleteId(taskComplete.getId()+"");
        }

        //然后删除taskComplete，根据task的id
        taskCompleteMapper.deleteByTaskId(id);
    }








    public void update(Task task){
        taskMapper.update(task);
    }


}
