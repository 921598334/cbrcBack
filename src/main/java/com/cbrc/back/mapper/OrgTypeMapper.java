package com.cbrc.back.mapper;


import com.cbrc.back.model.OrgInfo;
import com.cbrc.back.model.OrgType;
import com.cbrc.back.model.Table1;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

//机构类型
@Mapper
public interface OrgTypeMapper {



//    //查询所有机构类型
//   // @Select("select * from dic_orgtype ")
    List<OrgType> findAll();


}




