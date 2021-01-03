package com.cbrc.back.service;


import com.cbrc.back.mapper.OrgInfoMapper;
import com.cbrc.back.mapper.OrgTypeMapper;
import com.cbrc.back.model.OrgInfo;
import com.cbrc.back.model.OrgType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgInfoService {

    @Autowired
    OrgInfoMapper orgInfoMapper;



    public List<OrgInfo> query(OrgInfo orgInfo) {

        return orgInfoMapper.query(orgInfo);

    }




}
