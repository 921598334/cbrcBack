//model中放不用网络传输的类
package com.cbrc.back.model;


import lombok.Data;

@Data
public class Table3 {

    private Integer id;

    private String col1;
    private String col2;
    private String col3;
    private String col4;
    private String col5;
    private String col6;
    private String col7;
    private String col8;
    private String col9;
    private String col10;
    private String col11;


    private String date;
    private Integer userid;
    private String filetype;
    private Integer taskcompleteid;
    private Integer orgtype;

    //此外还需要通过orgtype得到机构名称，任务标题
    private String orgName;
    private String taskTitle;
    private String manager;
    private String period;
    private String userName;
    private String tel;
}
