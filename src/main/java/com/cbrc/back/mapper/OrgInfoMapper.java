package com.cbrc.back.mapper;


import com.cbrc.back.model.OrgInfo;
import com.cbrc.back.model.OrgType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrgInfoMapper {





    //查询所有机构
    @Select("select * from orginfo ")
    List<OrgInfo> find();


    //根据机构类型，查询所有机构
    @Select("select * from orginfo where orgtype = #{orgtype}")
    List<OrgInfo> findByOrgtype(@Param("orgtype") Integer orgtype);




}




