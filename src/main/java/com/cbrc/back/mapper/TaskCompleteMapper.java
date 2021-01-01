package com.cbrc.back.mapper;


import com.cbrc.back.model.Task;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskCompleteMapper {




    //添加
    @Insert("insert into taskcomplete(taskid,iscomplete,userid,orgid) " +
            "values" +
            " (#{taskid},#{iscomplete},#{userid},#{orgid}")
    void insert(TaskCompleteMapper taskCompleteMapper);


    //根据查询所有任务
//    @Select("select * from task ")
//    List<Task> find();


}




