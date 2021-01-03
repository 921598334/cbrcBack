//model中放不用网络传输的类
package com.cbrc.back.model;


import lombok.Data;

@Data
public class TaskComplete {

    private Integer id;
    private Integer taskid;
    private Integer iscomplete;
    private String completetime;
    private Integer userid;
    private String orgid;

    //任务详情
    private Task taskinfo;


}
