package com.cbrc.back.Controller;


import com.alibaba.fastjson.JSON;
import com.cbrc.back.mapper.Table1Mapper;
import com.cbrc.back.mapper.TableStructMapper;
import com.cbrc.back.model.Table1;
import com.cbrc.back.model.TableStruct;
import com.cbrc.back.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@CrossOrigin("*")
@RestController
public class QueryAndDownloadController {


    @Autowired
    TableStructMapper tableStructMapper;

    @Autowired
    Table1Mapper table1Mapper;

    @Autowired
    DownloadService downloadService;

    @PostMapping("/query")
    public Object query(
                         @RequestParam(name="orgName",defaultValue="") String orgName,
                         @RequestParam(name="tableName",defaultValue="") String tableName,
                         @RequestParam(name="fromDate",defaultValue="") String fromDate,
                         @RequestParam(name="endDate",defaultValue="") String endDate,


                          HttpServletRequest request,
                          HttpServletResponse response,
                          Model model) throws Exception {

        System.out.println("query开始执行============================");

        System.out.println("fromData"+fromDate);

        if(fromDate.equals("")){
          fromDate = "2000-01-01";
        }

        if(endDate.equals("")){
            endDate = "2099-12-12";
        }

        System.out.println("fromData"+fromDate);
        System.out.println("endDate"+endDate);

        ArrayList<Table1> table1s = null;

        //根据不同的表名查询不同的表
        if(tableName.equals("1")){

            if(orgName.equals("")){
                table1s   = table1Mapper.find(fromDate,endDate);
            }else{
                table1s   = table1Mapper.findByOrgName(orgName,fromDate,endDate);
            }





            return  table1s;

        }else if(tableName.equals("2")){

        }else if(tableName.equals("3")){

        }else if(tableName.equals("4")){

        }



       return  null;



     }


    @PostMapping("/download")
    public Object download(
            @RequestParam(name="id",defaultValue="") String id,

            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {

        System.out.println("download开始执行============================");


        Table1 table1 = table1Mapper.findById(id);

        //开始生成表
        String filePath = downloadService.downloadExcel("1",table1,response);



        return  filePath;



    }






    @PostMapping("/collectDownload")
    public Object collectDownload(
            @RequestParam(name="orgName",defaultValue="") String orgName,
            @RequestParam(name="tableName",defaultValue="") String tableName,
            @RequestParam(name="fromDate",defaultValue="") String fromDate,
            @RequestParam(name="endDate",defaultValue="") String endDate,

            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {

        System.out.println("collectDownload开始执行============================");


        System.out.println("fromData"+fromDate);

        if(fromDate.equals("")){
            fromDate = "2000-01-01";
        }

        if(endDate.equals("")){
            endDate = "2099-12-12";
        }



        Table1 table1 = null;


        //根据不同的表名查询不同的表
        if(tableName.equals("1")){


            if(orgName.equals("")){
                table1 = table1Mapper.collectFind(fromDate,endDate);
            }else{
                table1 = table1Mapper.collectFindByOrg(orgName,fromDate,endDate);
            }


        }else if(tableName.equals("2")){

        }else if(tableName.equals("3")){

        }else if(tableName.equals("4")){

        }







        //开始生成表
        String filePath = downloadService.downloadExcel("1",table1,response);



        return  filePath;



    }








}










