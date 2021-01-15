package com.cbrc.back.timer;

import com.cbrc.back.model.Task;
import com.cbrc.back.model.TimerTask;
import com.cbrc.back.service.TaskService;
import com.cbrc.back.service.TimerTaskService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronTrigger;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务

public class DynamicScheduleTask implements SchedulingConfigurer {



    @Autowired
    TimerTaskService timerTaskService;


    @Autowired
    TaskService taskService;


    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");









    /**
     * 执行定时任务.
     */

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {


        //生成任务
        List<TimerTask> timerTaskList = timerTaskService.query(new TimerTask());

        for(TimerTask timerTask:timerTaskList){
            //为每个定时任务（TimerTask）生成任务（Task）


                taskRegistrar.addTriggerTask(
                        //1.添加任务内容(Runnable)
                        () ->{
                            System.out.println(timerTask.getTasktitle()+" 执行动态定时任务: " + LocalDateTime.now().toLocalTime());


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
                                taskTmp.setPeriod(period);

                                taskTmp.setOrgtype(timerTask.getOrgtype());


                                try{
                                    taskService.insert(taskTmp);
                                    //数据插入后生成taskcomplete表
                                    taskService.createTaskComplete(taskTmp);

                                }catch (Exception e){
                                    e.printStackTrace();
                                    throw  e;
                                }
                            }else{
                                System.out.println("任务："+timerTask.getTasktitle()+"被禁用了");
                            }




                        },

                        //2.设置执行周期(Trigger)
                        triggerContext -> {
                            //2.1 从数据库获取执行周期(每次都需要查询数据库获取最新都数据)
                            Integer timerTaskId = timerTask.getId();
                            TimerTask t = new  TimerTask();
                            t.setId(timerTaskId);
                            TimerTask newTimerTask = timerTaskService.query(t).get(0);


                            String cron = newTimerTask.getCron();

                            System.out.println(cron+" 设置的执行动态周期为："+newTimerTask.getCron());
                            //2.2 合法性校验.

                            //2.3 返回执行周期(Date)
                            return new CronTrigger(cron).nextExecutionTime(triggerContext);


                        }
                );

        }
    }

}