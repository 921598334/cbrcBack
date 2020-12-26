//model中放不用网络传输的类
package com.cbrc.back.model;


import lombok.Data;

@Data
public class TableStruct {

    private Integer cellid;

    private String cellname;
    private String row_id;
    private String col_id;
    private String celltype;
    private String repid;
    private String explain;
    private String mark;
    private String amount;


}
