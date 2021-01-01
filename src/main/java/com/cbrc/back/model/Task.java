//model中放不用网络传输的类
package com.cbrc.back.model;


import lombok.Data;

@Data
public class Task {

    private Integer id;
    private String tasktitle;
    private String taskdescribe;
    private String fromdate;
    private String enddate;
    private String filetype;
    private String orgtype;
    private Integer userid;
    private String createtime;


}
