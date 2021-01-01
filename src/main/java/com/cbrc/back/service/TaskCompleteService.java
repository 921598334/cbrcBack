package com.cbrc.back.service;


import com.cbrc.back.mapper.TaskCompleteMapper;
import com.cbrc.back.mapper.TaskMapper;
import com.cbrc.back.model.Task;
import com.cbrc.back.model.TaskComplete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskCompleteService {



    @Autowired
    TaskCompleteMapper taskCompleteMapper;

    //添加
    public void insert(TaskComplete taskComplete){
        taskCompleteMapper.insert(taskComplete);
    }





}
