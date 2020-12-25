package com.cbrc.back.mapper;


import com.cbrc.back.model.Table1;
import com.cbrc.back.model.Userinfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.poi.ss.formula.functions.T;

@Mapper
public interface Table1Mapper {


    //添加
    @Insert("insert into Table1(col1,col1_1,col1_11,col1_2,col1_3,col1_4,col1_5,col2,col2_1,col2_11,col2_2,col2_3,col3,col3_1,col3_2,col3_3,col4,col4_1,col4_11,col5,col5_1,col5_2,col6,col6_1,col6_2,col6_3,col7,col7_1,col7_2,col8,col8_1,col8_11,col8_2,col9,col9_1,col9_2,col9_3,col10_1,col10_2,col10_3,col11_1,col11_2,col11_3,col12_1,col12_2,col12_3,col13,col14,col15,managerName,orgName,period,creator,tel,colmark1,colmark1_1,colmark1_11,colmark1_2,colmark1_3,colmark1_4,colmark1_5,colmark2,colmark2_1,colmark2_11,colmark2_2,colmark2_3,colmark3,colmark3_1,colmark3_2,colmark3_3,colmark4,colmark4_1,colmark4_11,colmark5,colmark5_1,colmark5_2,colmark6,colmark6_1,colmark6_2,colmark6_3,colmark7,colmark7_1,colmark7_2,colmark8,colmark8_1,colmark8_11,colmark8_2,colmark9,colmark9_1,colmark9_2,colmark9_3,colmark10_1,colmark10_2,colmark10_3,colmark11_1,colmark11_2,colmark11_3,colmark12_1,colmark12_2,colmark12_3,colmark13,colmark14,colmark15) values (#{col1},#{col1_1},#{col1_11},#{col1_2},#{col1_3},#{col1_4},#{col1_5},#{col2},#{col2_1},#{col2_11},#{col2_2},#{col2_3},#{col3},#{col3_1},#{col3_2},#{col3_3},#{col4},#{col4_1},#{col4_11},#{col5},#{col5_1},#{col5_2},#{col6},#{col6_1},#{col6_2},#{col6_3},#{col7},#{col7_1},#{col7_2},#{col8},#{col8_1},#{col8_11},#{col8_2},#{col9},#{col9_1},#{col9_2},#{col9_3},#{col10_1},#{col10_2},#{col10_3},#{col11_1},#{col11_2},#{col11_3},#{col12_1},#{col12_2},#{col12_3},#{col13},#{col14},#{col15},#{managerName},#{orgName},#{period},#{creator},#{tel},#{colmark1},#{colmark1_1},#{colmark1_11},#{colmark1_2},#{colmark1_3},#{colmark1_4},#{colmark1_5},#{colmark2},#{colmark2_1},#{colmark2_11},#{colmark2_2},#{colmark2_3},#{colmark3},#{colmark3_1},#{colmark3_2},#{colmark3_3},#{colmark4},#{colmark4_1},#{colmark4_11},#{colmark5},#{colmark5_1},#{colmark5_2},#{colmark6},#{colmark6_1},#{colmark6_2},#{colmark6_3},#{colmark7},#{colmark7_1},#{colmark7_2},#{colmark8},#{colmark8_1},#{colmark8_11},#{colmark8_2},#{colmark9},#{colmark9_1},#{colmark9_2},#{colmark9_3},#{colmark10_1},#{colmark10_2},#{colmark10_3},#{colmark11_1},#{colmark11_2},#{colmark11_3},#{colmark12_1},#{colmark12_2},#{colmark12_3},#{colmark13},#{colmark14},#{colmark15})")
    void insert(Table1 table1);


}




