package com.cbrc.back.mapper;


import com.cbrc.back.model.Task;
import com.cbrc.back.model.TaskComplete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskCompleteMapper {




    //添加
//    @Insert("insert into taskcomplete(taskid,iscomplete,userid,orgid) " +
//            "values" +
//            " (#{taskid},#{iscomplete},#{userid},#{orgid}")
    void insert(TaskComplete taskComplete);




}




