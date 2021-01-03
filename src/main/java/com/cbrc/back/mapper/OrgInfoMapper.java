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




    List<OrgInfo> query(OrgInfo orgInfo);


}




