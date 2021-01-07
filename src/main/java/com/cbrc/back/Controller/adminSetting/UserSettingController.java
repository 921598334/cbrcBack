package com.cbrc.back.Controller.adminSetting;


import com.cbrc.back.model.OrgInfo;
import com.cbrc.back.model.OrgType;
import com.cbrc.back.model.Userinfo;
import com.cbrc.back.service.OrgInfoService;
import com.cbrc.back.service.OrgTypeService;
import com.cbrc.back.service.UserinfoService;
import org.apache.catalina.User;
import org.apache.xmlbeans.impl.xb.xmlconfig.impl.UsertypeconfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Random;


@CrossOrigin("*")
@RestController
public class UserSettingController {

    @Autowired
    UserinfoService userinfoService;

    @Autowired
    OrgInfoService orgInfoService;





    @PostMapping("/insertUser")
    public Object insertUser(


            @RequestParam(name="username" ) String username,
            @RequestParam(name="truename" ) String truename,
            @RequestParam(name="telphone" ) String telphone,
            @RequestParam(name="password" ) String password,
            @RequestParam(name="orgid" ) String orgid,
            HttpServletRequest request,
            HttpServletResponse response
    ){


        Userinfo userinfo = new Userinfo();

        userinfo.setUsername(username);
        userinfo.setTruename(truename);
        userinfo.setTelphone(telphone);
        userinfo.setPassword(password);
        userinfo.setOrgid(orgid);

        userinfoService.insert(userinfo);

        return null;
    }




    @PostMapping("/deleteUser")
    public Object deleteOrgInfo(
            @RequestParam(name="id",defaultValue="") String id,
            HttpServletRequest request,
            HttpServletResponse response
    ){


        Userinfo userinfo = new Userinfo();
        userinfo.setUserid(Integer.parseInt(id));

        userinfoService.delete(userinfo);

        return null;
    }







    @PostMapping("/updateUserInfo")
    public Object updateUserInfo(
            @RequestParam(name="updateUserId" ) String userid,
            @RequestParam(name="updateUserName" ) String username,
            @RequestParam(name="updateTrueName" ) String truename,
            @RequestParam(name="updateTelphone" ) String telphone,
            @RequestParam(name="updatePassword" ) String password,
            @RequestParam(name="updateOrgType" ) String orgid,
            HttpServletRequest request,
            HttpServletResponse response
    ){


        Userinfo userinfo = new Userinfo();
        userinfo.setUserid(Integer.parseInt(userid) );
        userinfo.setUsername(username);
        userinfo.setTruename(truename);
        userinfo.setTelphone(telphone);
        userinfo.setPassword(password);
        userinfo.setOrgid(orgid);

        userinfoService.update(userinfo);

        return  null;

    }





    //得到所有的用户和机构信息
    @PostMapping("/initUsers")
    public Object InitUsers(
            HttpServletRequest request,
            HttpServletResponse response
    ){



        List<Userinfo> userinfoList = userinfoService.query(new Userinfo());


        //查询用户所属机构的中文名称
        for(Userinfo userinfo:userinfoList){
            //定时作业直接跳过

            OrgInfo orgInfoTmp = new OrgInfo();
            orgInfoTmp.setOrgid(userinfo.getOrgid());

            List<OrgInfo> queryResult = orgInfoService.query(orgInfoTmp);
            if(queryResult==null || queryResult.size()==0){
                userinfo.setOrgName("未知机构："+userinfo.getOrgid());
            }else{
                userinfo.setOrgName(queryResult.get(0).getOrgname());
            }

        }


        List<OrgInfo> orgInfoList = orgInfoService.query(new OrgInfo());

        Object[] returnList = new Object[]{userinfoList,orgInfoList};

        return  returnList;

    }











}








