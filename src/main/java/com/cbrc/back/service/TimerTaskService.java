package com.cbrc.back.service;


import com.cbrc.back.mapper.OrgInfoMapper;
import com.cbrc.back.mapper.OrgTypeMapper;
import com.cbrc.back.mapper.TimerTaskMapper;
import com.cbrc.back.model.OrgInfo;
import com.cbrc.back.model.OrgType;
import com.cbrc.back.model.TimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimerTaskService {

   @Autowired
    TimerTaskMapper taskMapper;

   public List<TimerTask> query(TimerTask timerTask){
       return taskMapper.query(timerTask);
   }


    public void insert(TimerTask timerTask){
         taskMapper.insert(timerTask);
    }


    public void update(TimerTask timerTask){
        taskMapper.update(timerTask);
    }



    public void delete(String id){
        taskMapper.delete(id);
    }
}
