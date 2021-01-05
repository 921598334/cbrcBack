package com.cbrc.back.mapper;


import com.cbrc.back.model.Task;
import com.cbrc.back.model.TaskComplete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskCompleteMapper {



    void insert(TaskComplete taskComplete);


    List<TaskComplete> query(TaskComplete taskComplete);

    void update(TaskComplete taskComplete);


    void deleteByTaskId(String id);

}




