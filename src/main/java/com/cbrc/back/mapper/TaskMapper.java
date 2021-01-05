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



    void insert(Task task);


    List<Task> query(Task task);


    void delete(String id);



    void update(Task task);
}




