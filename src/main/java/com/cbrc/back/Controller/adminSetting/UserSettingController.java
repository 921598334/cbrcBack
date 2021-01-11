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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


@CrossOrigin("*")
@RestController
public class UserSettingController {

    @Autowired
    UserinfoService userinfoService;

    @Autowired
    OrgInfoService orgInfoService;




    @PostMapping("/updateUserInfo")
    public Object updateUserInfo(
            @RequestParam(name="updateUserId",defaultValue="" ) String userid,
            @RequestParam(name="updateUserName",defaultValue="" ) String username,
            @RequestParam(name="updateTrueName",defaultValue="" ) String truename,
            @RequestParam(name="updateTelphone",defaultValue="" ) String telphone,
            @RequestParam(name="updatePassword",defaultValue="" ) String password,
            @RequestParam(name="updateOrgType" ,defaultValue="") String orgid,
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

        String status = userinfoService.update(userinfo);

        if(status.equals("S")){
            return null;
        }else{
            Map<String,String> result = new HashMap<>();
            result.put("F",status);
            return  result;
        }


    }

    @PostMapping("/insertUser")
    public Object insertUser(


            @RequestParam(name="newOrgType",defaultValue="" ) String newOrgType,
            @RequestParam(name="newPassword",defaultValue="" ) String newPassword,
            @RequestParam(name="newTel",defaultValue="" ) String newTel,
            @RequestParam(name="newTrueUserName" ,defaultValue="") String newTrueUserName,
            @RequestParam(name="newUserName",defaultValue="" ) String newUserName,
            HttpServletRequest request,
            HttpServletResponse response
    ){


        Userinfo userinfo = new Userinfo();

        userinfo.setUsername(newUserName);
        userinfo.setTruename(newTrueUserName);
        userinfo.setTelphone(newTel);
        userinfo.setPassword(newPassword);
        userinfo.setOrgid(newOrgType);

        String status = userinfoService.insert(userinfo);
        if(status.equals("S")){
            return null;
        }else{
            Map<String,String> result = new HashMap<>();
            result.put("F",status);
            return  result;
        }


    }







    @PostMapping("/deleteUser")
    public Object deleteOrgInfo(
            @RequestParam(name="userid",defaultValue="") String id,
            HttpServletRequest request,
            HttpServletResponse response
    ){


        Userinfo userinfo = new Userinfo();
        userinfo.setUserid(Integer.parseInt(id));

        userinfoService.delete(userinfo);

        return null;
    }













    //得到所有的用户和机构信息
    @PostMapping("/initUsers")
    public Object InitUsers(
            @RequestParam(name="userid",defaultValue="") String userid,
            HttpServletRequest request,
            HttpServletResponse response
    ){


        Userinfo userinfoTmp = new Userinfo();

        if(!userid.equals("")){
            userinfoTmp.setUserid(Integer.parseInt(userid) );
        }




        List<Userinfo> userinfoList = userinfoService.query(userinfoTmp);


        int deleteIndex = -1;
        //查询用户所属机构的中文名称
        for(int i=0;i<userinfoList.size();i++){
            //超级用户（orgid=10-x）直接跳过
            if(userinfoList.get(i).getOrgid().equals("10-x")){
               // userinfoList.remove(i);
                deleteIndex = i;
                continue;
            }

            OrgInfo orgInfoTmp = new OrgInfo();
            orgInfoTmp.setOrgid(userinfoList.get(i).getOrgid());

            List<OrgInfo> queryResult = orgInfoService.query(orgInfoTmp);
            if(queryResult==null || queryResult.size()==0){
                userinfoList.get(i).setOrgName("未知机构："+userinfoList.get(i).getOrgid());
            }else{
                userinfoList.get(i).setOrgName(queryResult.get(0).getOrgname());
            }
        }

        //删除超级管理员
        if(deleteIndex!=-1){
            userinfoList.remove(deleteIndex);
        }





        List<OrgInfo> orgInfoList = orgInfoService.query(new OrgInfo());

        Object[] returnList = new Object[]{userinfoList,orgInfoList};

        return  returnList;

    }











}








