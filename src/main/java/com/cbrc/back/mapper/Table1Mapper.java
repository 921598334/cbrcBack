package com.cbrc.back.mapper;


import com.cbrc.back.model.Table1;
import com.cbrc.back.model.Userinfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface Table1Mapper {


    //添加
    @Insert("insert into Table1(date,col1,col1_1,col1_11,col1_2,col1_3,col1_4,col1_5,col2,col2_1,col2_11,col2_2,col2_3,col3,col3_1,col3_2,col3_3,col4,col4_1,col4_11,col5,col5_1,col5_2,col6,col6_1,col6_2,col6_3,col7,col7_1,col7_2,col8,col8_1,col8_11,col8_2,col9,col9_1,col9_2,col9_3,col10_1,col10_2,col10_3,col11_1,col11_2,col11_3,col12_1,col12_2,col12_3,col13,col14,col15,managerName,orgName,period,creator,tel,colmark1,colmark1_1,colmark1_11,colmark1_2,colmark1_3,colmark1_4,colmark1_5,colmark2,colmark2_1,colmark2_11,colmark2_2,colmark2_3,colmark3,colmark3_1,colmark3_2,colmark3_3,colmark4,colmark4_1,colmark4_11,colmark5,colmark5_1,colmark5_2,colmark6,colmark6_1,colmark6_2,colmark6_3,colmark7,colmark7_1,colmark7_2,colmark8,colmark8_1,colmark8_11,colmark8_2,colmark9,colmark9_1,colmark9_2,colmark9_3,colmark10_1,colmark10_2,colmark10_3,colmark11_1,colmark11_2,colmark11_3,colmark12_1,colmark12_2,colmark12_3,colmark13,colmark14,colmark15) values (#{date},#{col1},#{col1_1},#{col1_11},#{col1_2},#{col1_3},#{col1_4},#{col1_5},#{col2},#{col2_1},#{col2_11},#{col2_2},#{col2_3},#{col3},#{col3_1},#{col3_2},#{col3_3},#{col4},#{col4_1},#{col4_11},#{col5},#{col5_1},#{col5_2},#{col6},#{col6_1},#{col6_2},#{col6_3},#{col7},#{col7_1},#{col7_2},#{col8},#{col8_1},#{col8_11},#{col8_2},#{col9},#{col9_1},#{col9_2},#{col9_3},#{col10_1},#{col10_2},#{col10_3},#{col11_1},#{col11_2},#{col11_3},#{col12_1},#{col12_2},#{col12_3},#{col13},#{col14},#{col15},#{managerName},#{orgName},#{period},#{creator},#{tel},#{colmark1},#{colmark1_1},#{colmark1_11},#{colmark1_2},#{colmark1_3},#{colmark1_4},#{colmark1_5},#{colmark2},#{colmark2_1},#{colmark2_11},#{colmark2_2},#{colmark2_3},#{colmark3},#{colmark3_1},#{colmark3_2},#{colmark3_3},#{colmark4},#{colmark4_1},#{colmark4_11},#{colmark5},#{colmark5_1},#{colmark5_2},#{colmark6},#{colmark6_1},#{colmark6_2},#{colmark6_3},#{colmark7},#{colmark7_1},#{colmark7_2},#{colmark8},#{colmark8_1},#{colmark8_11},#{colmark8_2},#{colmark9},#{colmark9_1},#{colmark9_2},#{colmark9_3},#{colmark10_1},#{colmark10_2},#{colmark10_3},#{colmark11_1},#{colmark11_2},#{colmark11_3},#{colmark12_1},#{colmark12_2},#{colmark12_3},#{colmark13},#{colmark14},#{colmark15})")
    void insert(Table1 table1);


    //查询,某个公司
    @Select("select * from Table1 where orgName= #{orgName} and date >= #{fromData} and date <= #{endData}")
    ArrayList<Table1> findByOrgName(@Param("orgName") String orgName, @Param("fromData") String fromData, @Param("endData") String endData);


    //查询,所有数据
    @Select("select * from Table1 where  date >= #{fromData} and date <= #{endData}")
    ArrayList<Table1> find( @Param("fromData") String fromData, @Param("endData") String endData);



    @Select("select * from Table1 where id= #{id}")
    Table1 findById(@Param("id") String id);



    //汇总查询(查询所有公司)
    @Select("select  sum(col1) as col1,sum(col1_1) as col1_1,sum(col1_11) as col1_11,sum(col1_2) as col1_2,sum(col1_3) as col1_3,sum(col1_4) as col1_4,sum(col1_5) as col1_5,sum(col2) as col2,sum(col2_1) as col2_1,sum(col2_11) as col2_11,sum(col2_2) as col2_2,sum(col2_3) as col2_3,sum(col3) as col3,sum(col3_1) as col3_1,sum(col3_2) as col3_2,sum(col3_3) as col3_3,sum(col4) as col4,sum(col4_1) as col4_1,sum(col4_11) as col4_11,sum(col5) as col5,sum(col5_1) as col5_1,sum(col5_2) as col5_2,sum(col6) as col6,sum(col6_1) as col6_1,sum(col6_2) as col6_2,sum(col6_3) as col6_3,sum(col7) as col7,sum(col7_1) as col7_1,sum(col7_2) as col7_2,sum(col8) as col8,sum(col8_1) as col8_1,sum(col8_11) as col8_11,sum(col8_2) as col8_2,sum(col9) as col9,sum(col9_1) as col9_1,sum(col9_2) as col9_2,sum(col9_3) as col9_3,sum(col10_1) as col10_1,sum(col10_2) as col10_2,sum(col10_3) as col10_3,sum(col11_1) as col11_1,sum(col11_2) as col11_2,sum(col11_3) as col11_3,sum(col12_1) as col12_1,sum(col12_2) as col12_2,sum(col12_3) as col12_3,sum(col13) as col13,sum(col14) as col14,sum(col15) as col15 from Table1 where  date >= #{fromData} and date <= #{endData}")
    Table1 collectFind( @Param("fromData") String fromData, @Param("endData") String endData);

    //汇总查询(查询某个公司)
    @Select("select sum(col1) as col1,sum(col1_1) as col1_1,sum(col1_11) as col1_11,sum(col1_2) as col1_2,sum(col1_3) as col1_3,sum(col1_4) as col1_4,sum(col1_5) as col1_5,sum(col2) as col2,sum(col2_1) as col2_1,sum(col2_11) as col2_11,sum(col2_2) as col2_2,sum(col2_3) as col2_3,sum(col3) as col3,sum(col3_1) as col3_1,sum(col3_2) as col3_2,sum(col3_3) as col3_3,sum(col4) as col4,sum(col4_1) as col4_1,sum(col4_11) as col4_11,sum(col5) as col5,sum(col5_1) as col5_1,sum(col5_2) as col5_2,sum(col6) as col6,sum(col6_1) as col6_1,sum(col6_2) as col6_2,sum(col6_3) as col6_3,sum(col7) as col7,sum(col7_1) as col7_1,sum(col7_2) as col7_2,sum(col8) as col8,sum(col8_1) as col8_1,sum(col8_11) as col8_11,sum(col8_2) as col8_2,sum(col9) as col9,sum(col9_1) as col9_1,sum(col9_2) as col9_2,sum(col9_3) as col9_3,sum(col10_1) as col10_1,sum(col10_2) as col10_2,sum(col10_3) as col10_3,sum(col11_1) as col11_1,sum(col11_2) as col11_2,sum(col11_3) as col11_3,sum(col12_1) as col12_1,sum(col12_2) as col12_2,sum(col12_3) as col12_3,sum(col13) as col13,sum(col14) as col14,sum(col15) as col15 from Table1 where orgName = #{orgName}  and date >= #{fromData} and date <= #{endData}")
    Table1 collectFindByOrg(@Param("orgName") String orgName,@Param("fromData") String fromData, @Param("endData") String endData);

}




