package com.cbrc.back.mapper;


import com.cbrc.back.model.Task;
import com.cbrc.back.model.TimerTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Timer;

@Mapper
public interface TimerTaskMapper {



    List<TimerTask> query(TimerTask timerTask);

    void insert(TimerTask timerTask);

    void delete(String id);

    void update(TimerTask timerTask);
}




