package com.cbrc.back.Controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cbrc.back.model.OrgType;
import com.cbrc.back.model.Task;
import com.cbrc.back.model.TaskComplete;
import com.cbrc.back.model.Userinfo;
import com.cbrc.back.service.OrgService;
import com.cbrc.back.service.TaskCompleteService;
import com.cbrc.back.service.TaskService;
import com.cbrc.back.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;


@CrossOrigin("*")
@RestController
public class TaskController {

    @Autowired
    OrgService orgService;

    @Autowired
    TaskService taskService;

    @Autowired
    TaskCompleteService taskCompleteService;

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");










    //获取所有机构类型与其下的机构
    @PostMapping("/getOrg")
    public Object getOrg(
                          HttpServletRequest request,
                          HttpServletResponse response,
                          Model model) throws Exception {

        System.out.println("getOrg开始执行============================");

        try{
            //得到所有的机构类型
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
            @RequestParam(name="selectedValue",defaultValue="") String selectedValue,
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
        task.setUserid(Integer.parseInt(userid) );
        task.setCreatetime(dateformat.format(System.currentTimeMillis()));

        //selectedValue得到的是map类型，需要转换为list
        List<String> selectedValueList = new ArrayList<>();
        Map maps  = (Map)JSON.parse(selectedValue);
        for(int i=0;i<maps.size();i++) {
            String tmp = maps.get(i + "")+"";
            selectedValueList.add(tmp);
        }

        String str = JSON.toJSONString(selectedValueList);

        task.setOrgtype(str);




        try{
            taskService.insert(task);

        }catch (Exception e){
            e.printStackTrace();
        }





        //数据插入后生成taskcomplete表
        for(int i=0;i<selectedValueList.size();i++){
            TaskComplete taskComplete = new TaskComplete();
            taskComplete.setIscomplete(0);
            taskComplete.setTaskid(task.getId());
            taskComplete.setOrgid(selectedValueList.get(i));
            taskCompleteService.insert(taskComplete);
        }



        return  null;

    }



}








