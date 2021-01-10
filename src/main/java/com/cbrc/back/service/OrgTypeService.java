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
import java.util.Map;

@Service
public class OrgTypeService {

    @Autowired
    OrgInfoMapper orgInfoMapper;

    @Autowired
    OrgTypeMapper orgTypeMapper;


    //查询所有机构类型，机构信息（需要优化）
    public List<OrgType> findAll() {

        //得到所有的机构类型
        List<OrgType> orgTypeList = orgTypeMapper.query(new OrgType());

        //查询该机构类别下的所有公司
        for(OrgType orgType : orgTypeList){
            OrgInfo orgInfo = new OrgInfo();
            orgInfo.setOrgtype( orgType.getOrgtype()+"");
            List<OrgInfo> orgInfoList = orgInfoMapper.query(orgInfo);

            orgType.setOrgs(orgInfoList);
        }


        return orgTypeList;
    }



    //查询某个机构类型下的所有机构信息
    public List<OrgType> findAllByOrgTpe(Integer orgTypeId) {

        //得到所有的机构类型
        OrgType orgTypeTmp = new OrgType();
        orgTypeTmp.setOrgtype(orgTypeId);
        List<OrgType> orgTypeList = orgTypeMapper.query(orgTypeTmp);

        //查询该机构类别下的所有公司
        for(OrgType orgType : orgTypeList){
            OrgInfo orgInfo = new OrgInfo();
            orgInfo.setOrgtype( orgType.getOrgtype()+"");
            List<OrgInfo> orgInfoList = orgInfoMapper.query(orgInfo);

            orgType.setOrgs(orgInfoList);
        }

        return orgTypeList;
    }



    //根据机构号得到名称
    public List<OrgType> query(OrgType orgType) {

        //得到所有的机构类型
        List<OrgType> orgTypeList = orgTypeMapper.query(orgType);


        return orgTypeList;

    }



    //根据机构号得到名称
    public void  update(OrgType orgType) {
       orgTypeMapper.update(orgType);
    }


    public void  delete(String orgtype) {
        orgTypeMapper.delete(orgtype);
    }



    public void  insert(OrgType orgType) {
         orgTypeMapper.insert(orgType);
    }




}
