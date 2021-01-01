package com.cbrc.back.service;


import com.cbrc.back.mapper.OrgInfoMapper;
import com.cbrc.back.mapper.OrgTypeMapper;
import com.cbrc.back.mapper.TaskMapper;
import com.cbrc.back.model.OrgInfo;
import com.cbrc.back.model.OrgType;
import com.cbrc.back.model.Task;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {



    @Autowired
    TaskMapper taskMapper;

    //添加,添加后返回
    public void insert(Task task){
        taskMapper.insert(task);

    }


    //查询所有任务
    public List<Task> find(){
        return  taskMapper.find();
    }





}
