package com.cbrc.back.service;


import com.cbrc.back.mapper.OrgInfoMapper;
import com.cbrc.back.mapper.OrgTypeMapper;
import com.cbrc.back.model.*;
import com.cbrc.back.tools.ExcelTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrgService {

    @Autowired
    OrgInfoMapper orgInfoMapper;

    @Autowired
    OrgTypeMapper orgTypeMapper;


    //查询所有机构类型，机构信息
    public List<OrgType> findAll() {

        List<OrgType> orgTypeList = orgTypeMapper.find();

        for(OrgType orgType : orgTypeList){
            List<OrgInfo> orgInfoList = orgInfoMapper.findByOrgtype(orgType.getOrgtype());

            orgType.setOrgs(orgInfoList);
        }

        return orgTypeList;

    }




}
