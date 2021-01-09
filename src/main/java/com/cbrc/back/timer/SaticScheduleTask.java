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
import java.util.Timer;


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
    //@Scheduled(cron = "0 00 1 1 4,7,10,1 ?")


    //每天的0点、13点、18点、21点都执行一次
    @Scheduled(cron = "0 0 0,13,18,21 * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=50000)
    private void configureTasks() {
        System.err.println("定时任务开始执行，当前时间为: " + LocalDateTime.now());


        //生成任务
        List<TimerTask> timerTaskList = timerTaskService.query(new TimerTask());

        for(TimerTask timerTask:timerTaskList){
            //为每个定时任务（TimerTask）生成任务（Task）

            if(timerTask.getIsenable()==1){

                Task taskTmp = new Task();
                taskTmp.setFiletype(timerTask.getFiletype());
                taskTmp.setTasktitle("定时任务"+"("+LocalDateTime.now()+")"+timerTask.getTasktitle());
                taskTmp.setTaskdescribe(timerTask.getTaskdescribe());
                taskTmp.setUserid(Integer.parseInt( timerTask.getUserid()) );
                taskTmp.setCreatetime(dateformat.format(System.currentTimeMillis()));


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
                taskTmp.setPeriod(period.substring(1));

                taskTmp.setOrgtype(timerTask.getOrgtype());


                try{
                    taskService.insert(taskTmp);
                    //数据插入后生成taskcomplete表
                    taskService.createTaskComplete(taskTmp);

                }catch (Exception e){
                    e.printStackTrace();
                    throw  e;
                }
            }
        }
    }
}