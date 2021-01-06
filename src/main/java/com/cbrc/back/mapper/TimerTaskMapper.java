package com.cbrc.back.mapper;


import com.cbrc.back.model.Task;
import com.cbrc.back.model.TimerTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TimerTaskMapper {



    List<TimerTask> query(TimerTask timerTask);



}




