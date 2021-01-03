package com.cbrc.back.Controller;


import com.cbrc.back.mapper.TableStructMapper;
import com.cbrc.back.model.*;
import com.cbrc.back.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




//管理员查询用户上传的报表（监管数据查看），同时下载

@CrossOrigin("*")
@RestController
public class QueryAndDownloadController {


    @Autowired
    TableStructMapper tableStructMapper;



    @Autowired
    Table1Service table1Service;

    @Autowired
    Table3Service table3Service;

    @Autowired
    DownloadService downloadService;

    @Autowired
    TaskService taskService;

    @Autowired
    TaskCompleteService taskCompleteService;

    @Autowired
    OrgTypeService orgTypeService;



    //单个文件下载
    @PostMapping("/getOrgType")
    public Object getOrgType(

            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {

        System.out.println("getOrgType 开始执行============================");


        List<OrgType> orgTypeList = orgTypeService.findAll();


        return orgTypeList;


    }







    //报表某类机构下，一定时间范围，某个文件类型的任务提交记录
    //需要显示机构名称，任务标题，
    @PostMapping("/query")
    public Object query(
                         @RequestParam(name="orgType",defaultValue="") String orgType,
                         @RequestParam(name="fromDate",defaultValue="") String fromDate,
                         @RequestParam(name="endDate",defaultValue="") String endDate,
                         @RequestParam(name="fileType",defaultValue="") String fileType,


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


        List<Map<String,String>> resultMap = new ArrayList<>();



        //查询思路：
        // 首先根据根据orgType查询,得到该类型下的所有机构id（orgid）
        // 首先根据完成情况，时间，机构类型查询taskcompete表，
        // 通过taskcompete的可以向下查询所有的用户填写的table细节
        // 也可以通过taskid查询task的任务标题等信息
        // 查询到结果后，返回taskcomplete的id，然后下载通过这个id（在table表中为taskcompleteid）来锁定要下载的数据


        //根据orgType查询,得到机构id（orgid）
        OrgType orgTypeTmp = orgTypeService.findAllByOrgTpe(Integer.parseInt(orgType) ).get(0);
        //开始查询这些机构id下已经完成的任务
        for(OrgInfo orgInfo:orgTypeTmp.getOrgs()){

            TaskComplete taskComplete = new TaskComplete();
            taskComplete.setIscomplete(1);
            taskComplete.setOrgid(orgInfo.getOrgid());
            //查询该机构id下已经完成的任务，在规定的时间范围了
            List<TaskComplete> taskCompleteList = taskCompleteService.query(taskComplete,fromDate,endDate);

            //开始查询任务标题，任务描述，任务发布时间，开始时间，结束时间信息
            //一个机构下的所有任务
            for(TaskComplete taskCompleteTmp : taskCompleteList){

                Map<String,String> resultMapTmp = new HashMap<>();

                Task task = new Task();
                task.setId(taskCompleteTmp.getTaskid());
                Task task1 = taskService.query(task).get(0);

                if(task1.getFiletype().equals(fileType)){
                    resultMapTmp.put("tasktitle",task1.getTasktitle());
                    resultMapTmp.put("taskdescribe",task1.getTaskdescribe());
                    resultMapTmp.put("createtime",task1.getCreatetime());
                    //resultMapTmp.put("fromdate",task1.getFromdate());
                    //resultMapTmp.put("enddate",task1.getEnddate());
                    resultMapTmp.put("taskcompleteid", taskCompleteTmp.getId()+"");
                    resultMapTmp.put("orgName",orgInfo.getOrgname() );
                    resultMapTmp.put("period",task1.getPeriod());

                    //其中一个机构已经完成的一个任务
                    resultMap.add(resultMapTmp);
                }


            }



        }



        return  resultMap;






        //根据不同的表名查询不同的表
//        if(fileType.equals("3")){
//
//            //查询思路：
//            // 首先根据根据orgType查询,得到该类型下的所有机构id（orgid）
//            // 首先根据完成情况，时间，机构类型查询taskcompete表，
//            // 通过taskcompete的可以向下查询所有的用户填写的table细节
//            // 也可以通过taskid查询task的任务标题等信息
//            // 查询到结果后，返回taskcomplete的id，然后下载通过这个id（在table表中为taskcompleteid）来锁定要下载的数据
//
//
//            //根据orgType查询,得到机构id（orgid）
//            OrgType orgTypeTmp = orgTypeService.findAllByOrgTpe(Integer.parseInt(orgType) ).get(0);
//            //开始查询这些机构id下已经完成的任务
//            for(OrgInfo orgInfo:orgTypeTmp.getOrgs()){
//
//                TaskComplete taskComplete = new TaskComplete();
//                taskComplete.setIscomplete(1);
//                taskComplete.setOrgid(orgInfo.getOrgid());
//                //查询该机构id下已经完成的任务，在规定的时间范围了
//                List<TaskComplete> taskCompleteList = taskCompleteService.query(taskComplete,fromDate,endDate);
//
//                //开始查询任务标题，任务描述，任务发布时间，开始时间，结束时间信息
//                //一个机构下的所有任务
//                for(TaskComplete taskCompleteTmp : taskCompleteList){
//
//                    Map<String,String> resultMapTmp = new HashMap<>();
//
//                    Task task = new Task();
//                    task.setId(taskCompleteTmp.getTaskid());
//                    Task task1 = taskService.query(task).get(0);
//
//                    resultMapTmp.put("tasktitle",task1.getTasktitle());
//                    resultMapTmp.put("taskdescribe",task1.getTaskdescribe());
//                    resultMapTmp.put("createtime",task1.getCreatetime());
//                    //resultMapTmp.put("fromdate",task1.getFromdate());
//                    //resultMapTmp.put("enddate",task1.getEnddate());
//                    resultMapTmp.put("taskcompleteid", taskCompleteTmp.getId()+"");
//                    resultMapTmp.put("orgName",orgInfo.getOrgname() );
//                    resultMapTmp.put("period",task1.getPeriod());
//
//                    //其中一个机构已经完成的一个任务
//                    resultMap.add(resultMapTmp);
//                }
//
//
//
//            }
//
//
////            Table3 table3Tmp = new Table3();
////            table3Tmp.setFiletype(fileType);
////            table3Tmp.setOrgtype(Integer.parseInt(orgType));
////            List<Table3> table3s = table3Service.query(table3Tmp,fromDate,endDate);
//
//
//            return  resultMap;
//
//        }else{
//
//            Table1 table1Tmp = new Table1();
//            table1Tmp.setFiletype(fileType);
//            table1Tmp.setOrgtype(Integer.parseInt(orgType));
//            List<Table1> table1s = table1Service.query(table1Tmp,fromDate,endDate);
//
//
//            return  table1s;
//        }


     }


     //单个文件下载
    @PostMapping("/download")
    public Object download(
            @RequestParam(name="id",defaultValue="") String taskCompleteId,
            @RequestParam(name="fileType",defaultValue="") String fileType,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {

        System.out.println("download开始执行============================");



        //通过taskCompleteId获取在table1表或者table3表中的数据



        if(fileType.equals("3")){

            Table3 table3Tmp = new Table3();
            table3Tmp.setTaskcompleteid(Integer.parseInt(taskCompleteId));
            List<Table3> table3s = table3Service.query(table3Tmp,"2000-01-01","2099-12-12");


            //开始生成表
            String filePath = downloadService.downloadExcel3(table3s,response);


            return  filePath;

        }else{

            Table1 table1Tmp = new Table1();
            table1Tmp.setTaskcompleteid(Integer.parseInt(taskCompleteId));
            Table1 table1 = table1Service.query(table1Tmp,"2000-01-01","2099-12-12").get(0);

            //开始生成表
            String filePath = downloadService.downloadExcel1(table1,response);


            return  filePath;



        }
    }






    @PostMapping("/collectDownload")
    public Object collectDownload(
            @RequestParam(name="orgName",defaultValue="") String orgName,
            @RequestParam(name="tableName",defaultValue="") String tableName,
            @RequestParam(name="fromDate",defaultValue="") String fromDate,
            @RequestParam(name="endDate",defaultValue="") String endDate,
            @RequestParam(name="fileType",defaultValue="") String fileType,
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


//            if(orgName.equals("")){
//                table1 = table1Service.collectFind(fromDate,endDate);
//            }else{
//                table1 = table1Service.collectFindByOrg(orgName,fromDate,endDate);
//            }


        }else if(tableName.equals("2")){

        }else if(tableName.equals("3")){

        }else if(tableName.equals("4")){

        }




        //开始生成表
        String filePath = downloadService.downloadExcel1(table1,response);



        return  filePath;



    }








}










