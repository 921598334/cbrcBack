package com.cbrc.back.mapper;


import com.cbrc.back.model.Table1;
import com.cbrc.back.model.Table3;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface Table3Mapper {


    //添加
//    @Insert("insert into Table3(col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,col11,userid) " +
//            "values" +
//            " (#{col1},#{col2},#{col3},#{col4},#{col5},#{col6},#{col7},#{col8},#{col9},#{col10},#{col11},#{userid})")
    void insert(Table3 table3);


    //查询某个公司
//    @Select("select * from Table where orgName= #{orgName} and date >= #{fromData} and date <= #{endData} and filetype = #{filetype}")
    ArrayList<Table3> findByOrgName(@Param("orgName") String orgName, @Param("fromData") String fromData, @Param("endData") String endData, @Param("filetype") String filetype);




    //根据表类型查询,所有数据
//    @Select("select * from Table3 ")
    ArrayList<Table3> find();


    //单个下载
//    @Select("select * from Table3 where id= #{id}")
    Table3 findById(@Param("id") String id);



    //汇总查询(查询所有公司)
//    @Select("select  sum(col1) as col1,sum(col1_1) as col1_1,sum(col1_11) as col1_11,sum(col1_2) as col1_2,sum(col1_3) as col1_3,sum(col1_4) as col1_4,sum(col1_5) as col1_5,sum(col1_21) as col1_21,sum(col2) as col2,sum(col2_1) as col2_1,sum(col2_11) as col2_11,sum(col2_2) as col2_2,sum(col2_3) as col2_3,sum(col3) as col3,sum(col3_1) as col3_1,sum(col3_2) as col3_2,sum(col3_3) as col3_3,sum(col4) as col4,sum(col4_1) as col4_1,sum(col4_11) as col4_11,sum(col4_2) as col4_2,sum(col4_3) as col4_3,sum(col5) as col5,sum(col5_1) as col5_1,sum(col5_2) as col5_2,sum(col5_3) as col5_3,sum(col6) as col6,sum(col6_1) as col6_1,sum(col6_2) as col6_2,sum(col6_3) as col6_3,sum(col7) as col7,sum(col7_1) as col7_1,sum(col7_2) as col7_2,sum(col8) as col8,sum(col8_1) as col8_1,sum(col8_11) as col8_11,sum(col8_2) as col8_2,sum(col9) as col9,sum(col9_1) as col9_1,sum(col9_2) as col9_2,sum(col9_3) as col9_3,sum(col10) as col10,sum(col10_1) as col10_1,sum(col10_2) as col10_2,sum(col10_3) as col10_3,sum(col11_1) as col11_1,sum(col11_2) as col11_2,sum(col11_3) as col11_3,sum(col11_11) as col11_11,sum(col12_1) as col12_1,sum(col12_2) as col12_2,sum(col12_3) as col12_3,sum(col13) as col13,sum(col14) as col14,sum(col15) as col15 from Table1 where  date >= #{fromData} and date <= #{endData}")
//    Table1 collectFind(@Param("fromData") String fromData, @Param("endData") String endData);
//
//    //汇总查询(查询某个公司)
//    @Select("select  sum(col1) as col1,sum(col1_1) as col1_1,sum(col1_11) as col1_11,sum(col1_2) as col1_2,sum(col1_3) as col1_3,sum(col1_4) as col1_4,sum(col1_5) as col1_5,sum(col1_21) as col1_21,sum(col2) as col2,sum(col2_1) as col2_1,sum(col2_11) as col2_11,sum(col2_2) as col2_2,sum(col2_3) as col2_3,sum(col3) as col3,sum(col3_1) as col3_1,sum(col3_2) as col3_2,sum(col3_3) as col3_3,sum(col4) as col4,sum(col4_1) as col4_1,sum(col4_11) as col4_11,sum(col4_2) as col4_2,sum(col4_3) as col4_3,sum(col5) as col5,sum(col5_1) as col5_1,sum(col5_2) as col5_2,sum(col5_3) as col5_3,sum(col6) as col6,sum(col6_1) as col6_1,sum(col6_2) as col6_2,sum(col6_3) as col6_3,sum(col7) as col7,sum(col7_1) as col7_1,sum(col7_2) as col7_2,sum(col8) as col8,sum(col8_1) as col8_1,sum(col8_11) as col8_11,sum(col8_2) as col8_2,sum(col9) as col9,sum(col9_1) as col9_1,sum(col9_2) as col9_2,sum(col9_3) as col9_3,sum(col10) as col10,sum(col10_1) as col10_1,sum(col10_2) as col10_2,sum(col10_3) as col10_3,sum(col11_1) as col11_1,sum(col11_2) as col11_2,sum(col11_3) as col11_3,sum(col11_11) as col11_11,sum(col12_1) as col12_1,sum(col12_2) as col12_2,sum(col12_3) as col12_3,sum(col13) as col13,sum(col14) as col14,sum(col15) as col15 from Table1 where orgName = #{orgName}  and date >= #{fromData} and date <= #{endData}")
//    Table1 collectFindByOrg(@Param("orgName") String orgName, @Param("fromData") String fromData, @Param("endData") String endData);

}




