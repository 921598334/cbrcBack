package com.cbrc.back.mapper;


import com.cbrc.back.model.Table1;
import com.cbrc.back.model.TaskComplete;
import com.cbrc.back.model.Userinfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface Table1Mapper {


    void insert(Table1 table1);


    List<Table1> query(Table1 table1);

    Table1 collectFind(List<String> taskCompleteListId);
    Table1 collectFindRound(List<String> taskCompleteListId);



    List<Table1> collectFind1(List<String> taskCompleteListId);


    void deleteByTaskCompleteId(String taskcompleteid);


    void deleteByUserId(Integer userid);

}




