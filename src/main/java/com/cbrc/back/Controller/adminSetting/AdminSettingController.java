package com.cbrc.back.Controller.adminSetting;


import com.cbrc.back.model.OrgInfo;
import com.cbrc.back.model.OrgType;
import com.cbrc.back.model.Userinfo;
import com.cbrc.back.service.OrgInfoService;
import com.cbrc.back.service.OrgTypeService;
import com.cbrc.back.service.UserinfoService;
import com.sun.tools.corba.se.idl.constExpr.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@CrossOrigin("*")
@RestController
public class AdminSettingController {

    @Autowired
    OrgTypeService orgTypeService;


    @Autowired
    OrgInfoService orgInfoService;


    Random r = new Random(1);





    @PostMapping("/deleteOrgInfo")
    public Object deleteOrgInfo(
            @RequestParam(name="orgid",defaultValue="") String orgid,
            HttpServletRequest request,
            HttpServletResponse response
    ){


        OrgInfo orgInfo = new OrgInfo();
        orgInfo.setOrgid(orgid);

        orgInfoService.delete(orgInfo);


        return null;
    }







    @PostMapping("/updateOrgInfo")
    public Object updateOrgInfo(
            @RequestParam(name="updateManager",defaultValue="") String updateManager,
            @RequestParam(name="updateOrgid",defaultValue="") String updateOrgid,
            @RequestParam(name="updateOrgname",defaultValue="") String updateOrgname,
            @RequestParam(name="updateOrgtype",defaultValue="") String updateOrgtype,
            HttpServletRequest request,
            HttpServletResponse response
    ){


        OrgInfo orgInfo = new OrgInfo();
        orgInfo.setManager(updateManager);
        orgInfo.setOrgid(updateOrgid);
        orgInfo.setOrgname(updateOrgname);
        orgInfo.setOrgtype(updateOrgtype);



        orgInfoService.update(orgInfo);

        return  null;

    }








    @PostMapping("/insertOrgInfo")
    public Object insertOrgInfo(

            @RequestParam(name="newManagerName",defaultValue="") String newManagerName,
            @RequestParam(name="newOrgInfoName",defaultValue="") String newOrgInfoName,
            @RequestParam(name="newOrgType",defaultValue="") String newOrgType,
            HttpServletRequest request,
            HttpServletResponse response
    ){




        OrgInfo orgInfoTmp = new OrgInfo();
        orgInfoTmp.setOrgtype(newOrgType);
        orgInfoTmp.setManager(newManagerName);
        orgInfoTmp.setOrgname(newOrgInfoName);
        //id需要根据newOrgType生成，newOrgType—数组序号
        String orgid = newOrgType+"-"+System.currentTimeMillis()+r.nextInt(100);
        orgInfoTmp.setOrgid(orgid);

        orgInfoService.insert(orgInfoTmp);

        return null;
    }









    @PostMapping("/initOrgInfo")
    public Object initOrgInfo(
            HttpServletRequest request,
            HttpServletResponse response
    ){



        List<OrgInfo> orgInfoList = orgInfoService.query(new OrgInfo());

        return  orgInfoList;

    }










    @PostMapping("/initOrg")
    public Object initOrg(
            HttpServletRequest request,
            HttpServletResponse response
    ){

        OrgType orgType = new OrgType();
        List<OrgType> orgTypeList = orgTypeService.query(orgType);

        return  orgTypeList;

    }



    @PostMapping("/updateOrg")
    public Object updateOrg(
            @RequestParam(name="udateOrgType",defaultValue="") String id,
            @RequestParam(name="udateOrgName",defaultValue="") String orgName,
            HttpServletRequest request,
            HttpServletResponse response
    ){

        OrgType orgType = new OrgType();
        orgType.setOrgtype(Integer.parseInt(id));
        orgType.setTypename(orgName);

         orgTypeService.update(orgType);

        return  null;

    }






    @PostMapping("/deleteOrg")
    public Object deleteOrg(
            @RequestParam(name="id",defaultValue="") String id,
            HttpServletRequest request,
            HttpServletResponse response
    ){

        //删除机构时会删除该机构下的所有公司和用户
        orgTypeService.delete(id);
        return null;
    }




    @PostMapping("/insertOrg")
    public Object insertOrg(

            @RequestParam(name="orgName",defaultValue="") String orgName,
            HttpServletRequest request,
            HttpServletResponse response
    ){

        OrgType orgType = new OrgType();
        orgType.setTypename(orgName);

        orgTypeService.insert(orgType);



        return null;
    }


}








