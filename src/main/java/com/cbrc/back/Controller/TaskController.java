package com.cbrc.back.Controller;


import com.alibaba.fastjson.JSON;
import com.cbrc.back.model.*;
import com.cbrc.back.model.TimerTask;
import com.cbrc.back.service.*;
import org.apache.commons.collections4.CollectionUtils;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    TimerTaskService timerTaskService;


    @Autowired
    OrgInfoService orgInfoService;

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");



    //更新任务，如果选择对机构发生了改变，需要对taskcomplete表进行响应的调整
    @PostMapping("/update")
    public Object update(
            @RequestParam(name="id",defaultValue="") String id,
            @RequestParam(name="filetype",defaultValue="") String fileType,
            @RequestParam(name="fromdate",defaultValue="") String fromDate,
            @RequestParam(name="enddate",defaultValue="") String endDate,
            @RequestParam(name="tasktitle",defaultValue="") String taskTitle,
            @RequestParam(name="taskDescribe",defaultValue="") String taskDescribe,
            @RequestParam(name="selectedValue",defaultValue="") String selectedValue,
            @RequestParam(name="userid",defaultValue="") String userid,
            @RequestParam(name="period",defaultValue="") String period,


            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {

        System.out.println("update 开始执行============================");




        //根据id查询之前的任务
        Task taskTmp = new Task();
        taskTmp.setId(Integer.parseInt(id));
        Task oldTask  = taskService.query(taskTmp).get(0);
        String oldSelectValueString = oldTask.getOrgtype();
        List<String> oldSelectedValueList = JSON.parseArray(oldSelectValueString,String.class);



        //把最新上传的机构类型也转化为list
        //selectedValue得到的是map类型，需要转换为list
        List<String> selectedValueList = new ArrayList<>();
        Map maps  = (Map)JSON.parse(selectedValue);
        for(int i=0;i<maps.size();i++) {
            String tmp = maps.get(i + "")+"";
            selectedValueList.add(tmp);
        }


        //在新集合中不存在的（也就是需要删除的）
        Object[] deleteArr = CollectionUtils.subtract(oldSelectedValueList, selectedValueList).toArray();
        //旧集合中不存在（需要添加的）
        Object[] addArr = CollectionUtils.subtract(selectedValueList,oldSelectedValueList).toArray();

        //该任务中需要删除的机构
        for(Object deleteEle:deleteArr){
            //首先在 taskcomplete 表中根据testid与orgid找到taskcomplete的id，然后在table1与table3中进行删除，再删除taskcomplete
            TaskComplete taskComplete = new TaskComplete();
            taskComplete.setTaskid(Integer.parseInt(id));
            taskComplete.setOrgid(deleteEle+"");
            taskCompleteService.delete(taskComplete);
        }

        //该任务需要添加的机构，
        for(Object addEle:addArr){
            //首先在 taskcomplete 表中进行添加

            TaskComplete taskComplete = new TaskComplete();
            taskComplete.setIscomplete(0);
            taskComplete.setTaskid(Integer.parseInt(id));
            taskComplete.setOrgid(addEle+"");
            taskCompleteService.insert(taskComplete);
        }


        //完成上述操作后把最新的数据更新到task中

        //把数据存储在数据库中
        Task task = new Task();
        task.setId(Integer.parseInt(id));
        task.setFiletype(fileType);
        task.setFromdate(fromDate);
        task.setEnddate(endDate);
        task.setTasktitle(taskTitle);
        task.setTaskdescribe(taskDescribe);
//        task.setUserid(Integer.parseInt(userid) );
//        task.setCreatetime(dateformat.format(System.currentTimeMillis()));
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

        if(period.contains("\"")){
            task.setPeriod(period.substring(1));
        }else
        {
            task.setPeriod(period);
        }




        //selectedValue得到的是map类型，需要转换为list
        String str = JSON.toJSONString(selectedValueList);

        task.setOrgtype(str);


        try{
            taskService.update(task);

        }catch (Exception e){
            e.printStackTrace();
        }



        return  null;

    }







    @PostMapping("/updateTimerTask")
    public Object updateTimerTask(
            @RequestParam(name="id",defaultValue="") String id,
            @RequestParam(name="filetype",defaultValue="") String fileType,
            @RequestParam(name="fromdate",defaultValue="") String fromDate,
            @RequestParam(name="enddate",defaultValue="") String endDate,
            @RequestParam(name="tasktitle",defaultValue="") String taskTitle,
            @RequestParam(name="taskDescribe",defaultValue="") String taskDescribe,
            @RequestParam(name="selectedValue",defaultValue="") String selectedValue,
            @RequestParam(name="userid",defaultValue="") String userid,
            @RequestParam(name="period",defaultValue="") String period,
            @RequestParam(name="isenable",defaultValue="") String isenable,
            @RequestParam(name="cron",defaultValue="") String cron,


            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {

        System.out.println("updateTimerTask 开始执行============================");



        //把数据存储在数据库中
        TimerTask task = new TimerTask();
        task.setId(Integer.parseInt(id));
        task.setFiletype(fileType);
        task.setTasktitle(taskTitle);
        task.setTaskdescribe(taskDescribe);


        //验证cron的有效性
        boolean isVaild = CronExpression.isValidExpression(cron);

        if(!isVaild){
            Map<String,String> error = new HashMap<>();
            error.put("F","Cron非法，请检查");
            return error;
        }


        task.setCron(cron);


        if(isenable.equals("true") || isenable.equals("1")){
            task.setIsenable(1);
        }else{
            task.setIsenable(0);
        }


        List<String> selectedValueList = new ArrayList<>();
        Map maps  = (Map)JSON.parse(selectedValue);
        for(int i=0;i<maps.size();i++) {
            String tmp = maps.get(i + "")+"";
            selectedValueList.add(tmp);
        }

        String str = JSON.toJSONString(selectedValueList);


        task.setOrgtype(str);


        try{
            timerTaskService.update(task);

        }catch (Exception e){
            e.printStackTrace();
        }



        return  null;

    }













    //查询任务细节（更新用）
    @PostMapping("/queryTaskDetail")
    public Object queryTaskDetail(
            @RequestParam(name="id",defaultValue="") String id,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {

        System.out.println("queryTaskDetail 开始执行============================");

        Task taskTmp = new Task();
        taskTmp.setId(Integer.parseInt(id));

        Task queryTask = taskService.query(taskTmp).get(0);


        String completeTime = queryTask.getPeriod();
        //季度数据要拼凑为2021-Q1这种样式
        String completeTimeTmp = "";
        String[] completeTimeTmpArr = completeTime.split("年");

        completeTimeTmp = completeTimeTmpArr[0];

        if(completeTimeTmpArr[1].contains("1")){
            completeTimeTmp+="-01-01";
        }else if(completeTimeTmpArr[1].contains("2")){
            completeTimeTmp+="-04-01";
        }else if(completeTimeTmpArr[1].contains("3")){
            completeTimeTmp+="-07-01";
        }else if(completeTimeTmpArr[1].contains("4")){
            completeTimeTmp+="-10-01";
        }

        queryTask.setPeriod(completeTimeTmp);


        return queryTask;
    }





    //查询定时任务细节（更新用）
    @PostMapping("/queryTimerTaskDetail")
    public Object queryTimerTaskDetail(
            @RequestParam(name="id",defaultValue="") String id,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {

        System.out.println("queryTimerTaskDetail 开始执行============================");

        TimerTask taskTmp = new TimerTask();
        taskTmp.setId(Integer.parseInt(id));

        TimerTask queryTask = timerTaskService.query(taskTmp).get(0);



        return queryTask;
    }









    //删除任务
    @PostMapping("/deleteTask")
    public Object deleteTask(
            @RequestParam(name="id",defaultValue="") String id,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {

        System.out.println("deleteTask 开始执行============================");


        //删除任务同时会删除taskcomplete和table1、3的数据
        taskService.delete(id);

        return null;
    }











    //删除定时任务
    @PostMapping("/deleteTimerTask")
    public Object deleteTimerTask(
            @RequestParam(name="id",defaultValue="") String id,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {

        System.out.println("deleteTimerTask 开始执行============================");



        timerTaskService.delete(id);

        return null;
    }









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
    @Transactional(rollbackFor = Exception.class)
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

            //数据插入后生成taskcomplete表
            taskService.createTaskComplete(task);

        }catch (Exception e){
            e.printStackTrace();
            throw  e;
        }




        return  null;

    }





    //发布定时任务
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/publishTimerTask")
    public Object publishTimerTask(
            @RequestParam(name="fileType",defaultValue="") String fileType,
            @RequestParam(name="taskTitle",defaultValue="") String taskTitle,
            @RequestParam(name="taskDescribe",defaultValue="") String taskDescribe,
            @RequestParam(name="userid",defaultValue="") String userid,
            @RequestParam(name="selectedValue",defaultValue="") String selectedValue,
            @RequestParam(name="cron",defaultValue="") String cron,


            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {

        System.out.println("publishTimerTask 开始执行============================");



        //selectedValue得到的是map类型，需要转换为list
        List<String> selectedValueList = new ArrayList<>();
        Map maps  = (Map)JSON.parse(selectedValue);
        for(int i=0;i<maps.size();i++) {
            String tmp = maps.get(i + "")+"";
            selectedValueList.add(tmp);
        }

        String str = JSON.toJSONString(selectedValueList);



        TimerTask timerTaskTmp = new TimerTask();
        timerTaskTmp.setFiletype(fileType);
        timerTaskTmp.setTasktitle(taskTitle);
        timerTaskTmp.setTaskdescribe(taskDescribe);
        timerTaskTmp.setUserid(userid);
        timerTaskTmp.setOrgtype(str);
        timerTaskTmp.setIsenable(1);
        //验证cron的有效性
        boolean isVaild = CronExpression.isValidExpression(cron);

        if(!isVaild){
            Map<String,String> error = new HashMap<>();
            error.put("F","Cron非法，请检查");
            return error;
        }


        timerTaskTmp.setCron(cron);




        timerTaskService.insert(timerTaskTmp);



        return  null;

    }















    //管理员查询已经发布的任务列表
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






    //管理员查询已经发布的定时任务列表
    @PostMapping("/queryTimerTask")
    public Object queryTimerTask(
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

        System.out.println("queryTimerTask 开始执行============================");



        if(fromDate.equals("")){
            fromDate = "2000-01-01";
        }

        if(endDate.equals("")) {
            endDate = "2099-12-12";
        }



        TimerTask task = new TimerTask();


        try{
            List<TimerTask> tasks = timerTaskService.query(task);

            //文件类型名称需要替换
            for(TimerTask t :tasks){
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







    //管理者某个任务下已经完成的机构
    @PostMapping("/queryCompletedOrg")
    public Object queryCompletedOrg(

            @RequestParam(name="taskid",defaultValue="") String taskid,


            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {

        System.out.println("queryCompletedOrg 开始执行============================");

        Task task = new Task();
        task.setId(Integer.parseInt(taskid));
        String filetype = taskService.query(task).get(0).getFiletype();


        TaskComplete taskComplete = new TaskComplete();


        List<Map<String, String>> resultList = new ArrayList<>();

        //如果指明了id，那就是根据任务id查询完成的任务
        taskComplete.setTaskid(Integer.parseInt(taskid));
        taskComplete.setIscomplete(1);
        try {
            List<TaskComplete> taskCompletes = taskCompleteService.query(taskComplete);

            //然后查询机构信息
            for (TaskComplete taskCompleteTmp : taskCompletes) {
                OrgInfo orgInfo = new OrgInfo();
                orgInfo.setOrgid(taskCompleteTmp.getOrgid());
                OrgInfo orgInfoTmp = orgInfoService.query(orgInfo).get(0);

                Map<String, String> resultMap = new HashMap<>();
                resultMap.put("orgName", orgInfoTmp.getOrgname());
                resultMap.put("manager", orgInfoTmp.getManager());
                resultMap.put("typeName", orgInfoTmp.getOrgTypeName());
                resultMap.put("taskcompleteid", taskCompleteTmp.getId()+"");
                resultMap.put("fileType", filetype);
                resultList.add(resultMap);
            }

            return resultList;
        } catch (Exception e) {
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

        TaskComplete taskComplete = new TaskComplete();


        //查询某个机构下，某种状态的任务

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








