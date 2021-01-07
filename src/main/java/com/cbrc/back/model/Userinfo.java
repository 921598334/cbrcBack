//model中放不用网络传输的类
package com.cbrc.back.model;


import lombok.Data;

@Data
public class Userinfo {

    private Integer userid;
    private String username;
    private String truename;
    private String telphone;
    private String password;
    private String orgid;
    private Integer state;

    private String token;
    private String modified;
    private String orgName;

}
