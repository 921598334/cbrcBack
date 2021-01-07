//model中放不用网络传输的类
package com.cbrc.back.model;


import lombok.Data;

@Data
public class OrgInfo {

    private String orgid;
    private String orgname;
    private String parentorgid;
    private String isreal;
    private String state;
    private String orgtype;
    private String pushtype;


    private String  manager;
    private String orgTypeName;




}
