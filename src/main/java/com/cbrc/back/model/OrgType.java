//model中放不用网络传输的类
package com.cbrc.back.model;


import lombok.Data;

import java.util.List;

@Data
public class OrgType {

    private Integer orgtype;
    private String typename;


    //该机构类型下的所有机构
    private List<OrgInfo> orgs;

}
