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
import java.util.Map;

//机构类型
@Mapper
public interface OrgTypeMapper {

    List<Map<String,String>> findAll();

    List<OrgType> query(OrgType orgType);

    void update(OrgType orgType);

    void delete(String orgtype);


    void insert(OrgType orgType);
}




