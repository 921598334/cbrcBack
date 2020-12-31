package com.cbrc.back.service;


import com.cbrc.back.mapper.UserinfoMapper;
import com.cbrc.back.model.Table1;
import com.cbrc.back.model.Table3;
import com.cbrc.back.model.Userinfo;
import com.cbrc.back.tools.ExcelTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class DownloadService {

    @Autowired
    ExcelTool excelTool;

    public String downloadExcel1(Table1 table1,HttpServletResponse response){


        return excelTool.writeExcelPOI1(table1,response);

    }

    public String downloadExcel3(Table3 table3, HttpServletResponse response){


        return excelTool.writeExcelPOI3(table3,response);

    }


}
