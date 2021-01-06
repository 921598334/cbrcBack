package com.cbrc.back.timer;

import com.alibaba.fastjson.JSON;
import com.cbrc.back.model.OrgInfo;
import com.cbrc.back.model.Task;
import com.cbrc.back.model.TaskComplete;
import com.cbrc.back.model.TimerTask;
import com.cbrc.back.service.OrgInfoService;
import com.cbrc.back.service.TaskCompleteService;
import com.cbrc.back.service.TaskService;
import com.cbrc.back.service.TimerTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;




//定时任务，在每季度最后一天23点59分59秒推送任务


@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {


    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");


    @Autowired
    TaskService taskService;

    @Autowired
    TaskCompleteService taskCompleteService;


    @Autowired
    TimerTaskService timerTaskService;


    @Autowired
    OrgInfoService orgInfoService;


    //每季度最后一天
    //@Scheduled(cron = "0 59 23 L 3,6,9,12 ?")

    //每季度第一天，1点发布任务
    @Scheduled(cron = "0 00 1 1 4,7,10,1 ?")

    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        System.err.println("定时任务开始执行，当前时间为: " + LocalDateTime.now());





        //生成任务
        //1.首先读取 pushtype,得到id，和filetypes，其中filetypes是一个json对象，里面存储了需要推送的数据
        //2.读取 orginfo 表，通过 pushtype 的id查询某个pushtype记录对应的需要推送的机构，得到机构的orgid
        //3.解析json对象，并且进行遍历，生成所有任务


        //1..首先读取 pushtype,得到id，和filetypes，其中filetypes是一个json对象，里面存储了需要推送的数据
        TimerTask timerTaskTmp = new TimerTask();
        List<TimerTask> timerTaskList = timerTaskService.query(timerTaskTmp);
        for(TimerTask timerTask:timerTaskList){

            //得到需要推送的机构
            Integer timerTaskId =  timerTask.getId();
            OrgInfo orgInfoTmp = new OrgInfo();
            orgInfoTmp.setPushtype(timerTaskId+"");
            List<OrgInfo> orgInfoList = orgInfoService.query(orgInfoTmp);
            List<String> selectedValueList = new ArrayList<>();
            for(OrgInfo orgInfo:orgInfoList){
                selectedValueList.add(orgInfo.getOrgid());
            }


            //得到该推送任务下，需要推送的文件类型

            String fileTypeString = timerTask.getFiletypes();
            List<String> fileTypes  = (List<String>) JSON.parse(fileTypeString);




            //开始生成任务
            for(String fileType:fileTypes){

                //把数据存储在数据库中
                Task task = new Task();
                task.setFiletype(fileType);
//              task.setFromdate(fromDate);
//              task.setEnddate(endDate);
                task.setTasktitle(LocalDateTime.now()+"的定时任务");
                task.setTaskdescribe(LocalDateTime.now()+"的定时任务");
                //定时任务的用户id为0
                task.setUserid(0);
                task.setCreatetime(dateformat.format(System.currentTimeMillis()));
                //还需要选择该任务是那一年第几季度的任务
                String period = dateformat.format(System.currentTimeMillis());
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

                task.setPeriod(period);


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

            }


        }







    }
}