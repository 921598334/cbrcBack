//model中放不用网络传输的类
package com.cbrc.back.model;


import lombok.Data;

@Data
public class TimerTask {

    private Integer id;


    private String tasktitle;
    private String taskdescribe;
    private String orgtype;
    private String userid;
    private String filetype;
    private Integer isenable;




}
