package com.cbrc.back.Controller;


import com.cbrc.back.model.OrgType;
import com.cbrc.back.model.Task;
import com.cbrc.back.model.Userinfo;
import com.cbrc.back.service.OrgService;
import com.cbrc.back.service.TaskService;
import com.cbrc.back.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@CrossOrigin("*")
@RestController
public class TaskController {

    @Autowired
    OrgService orgService;

    @Autowired
    TaskService taskService;

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

    //获取所有机构类型与其下的机构
    @PostMapping("/getOrg")
    public Object getOrg(
                          HttpServletRequest request,
                          HttpServletResponse response,
                          Model model) throws Exception {

        System.out.println("getOrg开始执行============================");

        try{
            List<OrgType> orgTypeList = orgService.findAll();
            return orgTypeList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    //发布任务
    @PostMapping("/publish")
    public Object publish(
            @RequestParam(name="fileType",defaultValue="") String fileType,
            @RequestParam(name="fromDate",defaultValue="") String fromDate,
            @RequestParam(name="endDate",defaultValue="") String endDate,
            @RequestParam(name="taskTitle",defaultValue="") String taskTitle,
            @RequestParam(name="taskDescribe",defaultValue="") String taskDescribe,
            @RequestParam(name="orgTypes",defaultValue="") String orgTypes,
            @RequestParam(name="userid",defaultValue="") String userid,


            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {

        System.out.println("publish 开始执行============================");

        //把数据存储在数据库中
        Task task = new Task();

        task.setFiletype(fileType);
        task.setFromdate(fromDate);
        task.setEnddate(endDate);
        task.setTasktitle(taskTitle);
        task.setTaskdescribe(taskDescribe);
        task.setOrgtype(orgTypes);
        task.setUserid(Integer.parseInt(userid) );
        task.setCreatetime(dateformat.format(System.currentTimeMillis()));



        try{
            taskService.insert(task);
        }catch (Exception e){
            e.printStackTrace();
        }



        //数据插入后生成taskcomplete表


        return  null;

    }



}








