package com.cbrc.back.service;


import com.cbrc.back.mapper.UserinfoMapper;
import com.cbrc.back.model.Table1;
import com.cbrc.back.model.Userinfo;
import com.cbrc.back.tools.ExcelTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class DownloadService {

    @Autowired
    ExcelTool excelTool;

    public String downloadExcel(String templateId,Table1 table1,HttpServletResponse response){


        return excelTool.writeExcelPOI(templateId,table1,response);

    }


}
