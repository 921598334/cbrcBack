package com.cbrc.back.mapper;


import com.cbrc.back.model.Table1;
import com.cbrc.back.model.Table3;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface Table3Mapper {



    void insert(Table3 table3);



    List<Table3> query(Table3 table3);


    List<Table3> collectFind(List<String> taskCompleteListId);


}




