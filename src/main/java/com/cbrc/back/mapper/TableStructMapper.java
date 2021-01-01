package com.cbrc.back.mapper;


import com.cbrc.back.model.Table1;
import com.cbrc.back.model.TableStruct;
import com.cbrc.back.model.Userinfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;

@Mapper
public interface TableStructMapper {




//    //@Select("select * from cellinfo")
    ArrayList<TableStruct> findAll();
//
//
//    //根据表的类型查询表的结构
//    //@Select("select * from cellinfo where repid = #{repid}")
    ArrayList<TableStruct> findByRepId(@Param("repid") String repid);
//
//    //根据表的类型查询表的结构，滤除备注栏
//    //@Select("select * from cellinfo where repid = #{repid} and cellname not like '%备注%'")
    ArrayList<TableStruct> findByRepIdExcludeMark(@Param("repid") String repid);
}




