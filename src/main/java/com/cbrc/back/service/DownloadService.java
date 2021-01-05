package com.cbrc.back.service;


import com.cbrc.back.mapper.UserinfoMapper;
import com.cbrc.back.model.Table1;
import com.cbrc.back.model.Table3;
import com.cbrc.back.model.Userinfo;
import com.cbrc.back.tools.ExcelTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class DownloadService {

    @Autowired
    ExcelTool excelTool;

    public String downloadExcel1(Table1 table1,boolean isCollect,String fileType,String manager,String orgName,String period,String user,String tel){


        return excelTool.writeExcelPOI1(table1,isCollect,fileType,manager,orgName,period,user,tel);

    }

    public String downloadExcel3(List<Table3> table3s,boolean isCollect,String fileType,String manager,String orgName,String period,String user,String tel){


        return excelTool.writeExcelPOI3(table3s,isCollect,fileType,manager,orgName,period,user,tel);

    }


}
