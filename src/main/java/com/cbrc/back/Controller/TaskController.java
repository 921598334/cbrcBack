package com.cbrc.back.Controller;


import com.alibaba.fastjson.JSON;
import com.cbrc.back.model.*;
import com.cbrc.back.service.OrgTypeService;
import com.cbrc.back.service.TaskCompleteService;
import com.cbrc.back.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;


@CrossOrigin("*")
@RestController
public class TaskController {

    @Autowired
    OrgTypeService orgTypeService;

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
            List<OrgType> orgTypeList = orgTypeService.findAll();
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
            @RequestParam(name="period",defaultValue="") String period,


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
        //还需要选择该任务是那一年第几季度的任务
        String[] periodTmp = period.split("-");
        if(periodTmp[1].equals("01")){
            period = periodTmp[0]+"年第1季度";
        }else if(periodTmp[1].equals("04")){
            period = periodTmp[0]+"年第2季度";
        }else if(periodTmp[1].equals("07")){
            period = periodTmp[0]+"年第3季度";
        }else if(periodTmp[1].equals("10")){
            period = periodTmp[0]+"年第4季度";
        }

        task.setPeriod(period.substring(1));


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






    //管理员查询已经发布的任务
    @PostMapping("/queryTask")
    public Object queryTask(
            @RequestParam(name="taskid",defaultValue="") String taskid,
            @RequestParam(name="iscomplete",defaultValue="") String iscomplete,
            @RequestParam(name="completetime",defaultValue="") String completetime,
            @RequestParam(name="userid",defaultValue="") String userid,
            @RequestParam(name="orgid",defaultValue="") String orgid,
            @RequestParam(name="fromDate",defaultValue="") String fromDate,
            @RequestParam(name="endDate",defaultValue="") String endDate,


            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {

        System.out.println("queryTask 开始执行============================");



        if(fromDate.equals("")){
            fromDate = "2000-01-01";
        }

        if(endDate.equals("")) {
            endDate = "2099-12-12";
        }



        Task task = new Task();


        try{
            List<Task> tasks = taskService.query(task);

            //文件类型名称需要替换
            for(Task t :tasks){
                if(t.getFiletype().equals("1")){
                    t.setFiletype("重庆保险中介机构季度数据表-专业代理、经纪机构用表");
                }else if(t.getFiletype().equals("2")){
                    t.setFiletype("重庆保险中介机构季度数据表-公估机构用表");
                }else if(t.getFiletype().equals("3")){
                    t.setFiletype("重庆保险中介机构季度数据表-专业中介机构销售寿险公司长期保险产品统计表");
                }else if(t.getFiletype().equals("4")){
                    t.setFiletype("重庆保险中介机构季度数据表-银邮代理机构用表");
                }

            }

            return  tasks;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }







    //用户查询自己需要完成的任务,用户查询任务是根据任务的创建时间来查询
    @PostMapping("/queryTaskComplete")
    public Object queryTaskComplete(

            @RequestParam(name="taskStatus",defaultValue="") String taskStatus,

            @RequestParam(name="orgid",defaultValue="") String orgid,
            @RequestParam(name="fromDate",defaultValue="") String fromDate,
            @RequestParam(name="endDate",defaultValue="") String endDate,


            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {

        System.out.println("queryTaskComplete 开始执行============================");



        if(fromDate.equals("")){
            fromDate = "2000-01-01";
        }

        if(endDate.equals("")){
            endDate = "2099-12-12";
        }






//        //最后需要返回对对象
//        List<Map<String,String>> resultMap = new ArrayList<>();
//
//        TaskComplete taskComplete = new TaskComplete();
//        taskComplete.setIscomplete(Integer.parseInt(taskStatus) );
//        taskComplete.setOrgid(orgid);
//        //查询该机构id下已经完成的任务，在规定的时间范围了
//        List<TaskComplete> taskCompleteList = taskCompleteService.query(taskComplete,fromDate,endDate);
//
//        //开始查询任务标题，任务描述，任务发布时间，开始时间，结束时间信息
//        for(TaskComplete taskCompleteTmp : taskCompleteList){
//            Map<String,String> resultMapTmp = new HashMap<>();
//
//            Task task1 = taskCompleteTmp.getTaskinfo();
//
//            resultMapTmp.put("tasktitle",task1.getTasktitle());
//            resultMapTmp.put("taskdescribe",task1.getTaskdescribe());
//            resultMapTmp.put("createtime",task1.getCreatetime());
//            resultMapTmp.put("fromdate",task1.getFromdate());
//            resultMapTmp.put("enddate",task1.getEnddate());
//            resultMapTmp.put("taskcompleteid", taskCompleteTmp.getId()+"");
//            resultMapTmp.put("iscomplete",taskStatus);
//
//            resultMap.add(resultMapTmp);
//        }
//
//        return resultMap;





        TaskComplete taskComplete = new TaskComplete();
        taskComplete.setOrgid(orgid);
        taskComplete.setIscomplete(Integer.parseInt(taskStatus) );

        try{
            List<TaskComplete> taskCompletes = taskCompleteService.queryByCreateTime(taskComplete,fromDate,endDate);

            return  taskCompletes;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }



}








