package com.cbrc.back.mapper;


import com.cbrc.back.model.OrgInfo;
import com.cbrc.back.model.Table1;
import com.cbrc.back.model.Task;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface TaskMapper {




    //添加
    @Insert("insert into task(tasktitle,taskdescribe,fromdate,enddate,filetype,orgtype,userid,createtime) " +
            "values" +
            " (#{tasktitle},#{taskdescribe},#{fromdate},#{enddate},#{filetype},#{orgtype},#{userid}),#{createtime}")
    void insert(Task task);


    //根据查询所有任务
    @Select("select * from task ")
    List<Task> find();


}




