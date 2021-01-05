package com.cbrc.back.service;


import com.cbrc.back.mapper.Table1Mapper;
import com.cbrc.back.mapper.Table3Mapper;
import com.cbrc.back.mapper.TaskCompleteMapper;
import com.cbrc.back.mapper.TaskMapper;
import com.cbrc.back.model.Task;
import com.cbrc.back.model.TaskComplete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskCompleteService {

    @Autowired
    Table1Mapper table1Mapper;

    @Autowired
    Table3Mapper table3Mapper;

    @Autowired
    TaskCompleteMapper taskCompleteMapper;

    @Autowired
    TaskMapper taskMapper;

    //添加
    public void insert(TaskComplete taskComplete){
        taskCompleteMapper.insert(taskComplete);
    }


    //更新
    public void update(TaskComplete taskComplete){
        taskCompleteMapper.update(taskComplete);
    }


    //查询，通过任务创建时间（普通用户查询）
    public List<TaskComplete> queryByCreateTime(TaskComplete taskComplete,String fromDate,String endDate){

        //查询到机构id下的所有任务
        List<TaskComplete> taskCompletes = taskCompleteMapper.query(taskComplete);

        //根据任务id获取任务详情
        for(TaskComplete taskCompleteTmp :taskCompletes){

            Task task = new Task();
            task.setId(taskCompleteTmp.getTaskid());
            Task taskTmp = taskMapper.query(task).get(0);

            taskCompleteTmp.setTaskinfo(taskTmp);
        }


        List<TaskComplete> returnTaskCompletes = new ArrayList<>();

        //判断任务创建时间是否在查询的开始时间与结束时间访问内
        for(TaskComplete taskCompleteTmp :taskCompletes){

            String createTime = taskCompleteTmp.getTaskinfo().getCreatetime();

            if(createTime.compareTo(fromDate)>=0 && createTime.compareTo(endDate)<=0){
                returnTaskCompletes.add(taskCompleteTmp);
            }
        }

        return returnTaskCompletes;

    }






    //查询，通过任务完成时间（管理员查询）
    public List<TaskComplete> queryByCompleteTime(TaskComplete taskComplete,String fromDate,String endDate){

        //查询到机构id下的所有任务
        List<TaskComplete> taskCompletes = taskCompleteMapper.query(taskComplete);

        //根据任务id获取任务详情
        for(TaskComplete taskCompleteTmp :taskCompletes){

            Task task = new Task();
            task.setId(taskCompleteTmp.getTaskid());
            Task taskTmp = taskMapper.query(task).get(0);

            taskCompleteTmp.setTaskinfo(taskTmp);
        }


        List<TaskComplete> returnTaskCompletes = new ArrayList<>();

        //判断任务创建时间是否在查询的开始时间与结束时间访问内
        for(TaskComplete taskCompleteTmp :taskCompletes){

            String completeTime = taskCompleteTmp.getCompletetime();


            //由于读取到到completeTime为：2002年第1季度 这种样式，所以需要解析
            String completeTimeTmp = "";
            String[] completeTimeTmpArr = completeTime.split("年");

            completeTimeTmp = completeTimeTmpArr[0];

            if(completeTimeTmpArr[1].contains("1")){
                completeTimeTmp+="-03-28";
            }else if(completeTimeTmpArr[1].contains("2")){
                completeTimeTmp+="-06-28";
            }else if(completeTimeTmpArr[1].contains("3")){
                completeTimeTmp+="-09-28";
            }else if(completeTimeTmpArr[1].contains("4")){
                completeTimeTmp+="-12-28";
            }



            if(completeTimeTmp.compareTo(fromDate)>=0 && completeTimeTmp.compareTo(endDate)<=0){
                returnTaskCompletes.add(taskCompleteTmp);
            }
        }

        return returnTaskCompletes;

    }




    public List<TaskComplete> query(TaskComplete taskComplete){
        return taskCompleteMapper.query(taskComplete);
    }






    //删除任务时，还需要删除table1，table3
    public void delete(TaskComplete taskComplete){
        //首先查询要删除的任务信息
        List<TaskComplete> deleteTaskCompletes = taskCompleteMapper.query(taskComplete);

        for(TaskComplete deleteTaskComplete:deleteTaskCompletes){

            //删除该deleteTaskComplete下的table1和table3
            table1Mapper.deleteByTaskCompleteId(deleteTaskComplete.getId()+"");
            table3Mapper.deleteByTaskCompleteId(deleteTaskComplete.getId()+"");
            //最后删除该taskComplete
            taskCompleteMapper.deleteById(deleteTaskComplete.getId()+"");
        }
    }


}
