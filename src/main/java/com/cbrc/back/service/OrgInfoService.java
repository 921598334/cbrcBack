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

    @Autowired
    OrgTypeMapper orgTypeMapper;


    public List<OrgInfo> query(OrgInfo orgInfo) {


        List<OrgInfo> orgInfoList =orgInfoMapper.query(orgInfo);


        //根据机构类型的id获取机构类型的中文名称
        for(OrgInfo orgInfoTmp:orgInfoList){

            OrgType orgTypeTmp = new OrgType();
            orgTypeTmp.setOrgtype(Integer.parseInt(orgInfoTmp.getOrgtype()));
            orgInfoTmp.setOrgTypeName(orgTypeMapper.query(orgTypeTmp).get(0).getTypename());
        }


        return orgInfoList;

    }


    public void insert(OrgInfo orgInfo){
        orgInfoMapper.insert(orgInfo);
    }


    public void update(OrgInfo orgInfo){
        orgInfoMapper.update(orgInfo);
    }

    public void delete(OrgInfo orgInfo){
        orgInfoMapper.delete(orgInfo);
    }


}
