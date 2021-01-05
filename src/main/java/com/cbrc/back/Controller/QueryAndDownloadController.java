package com.cbrc.back.Controller;


import com.alibaba.fastjson.JSON;
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


    @Autowired
    UserinfoService userinfoService;


    @Autowired
    OrgInfoService orgInfoService;




    //得到所有机构类型
    @PostMapping("/getOrgType")
    public Object getOrgType(

            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {

        System.out.println("getOrgType 开始执行============================");


        List<OrgType> orgTypeList = orgTypeService.findAll();


        return orgTypeList;


    }










    //汇总查询，需要查询某个机构类型下，特定任务类型，在时间范围内有多少季度任务
    @PostMapping("/collectqQuery")
    public Object collectqQuery(
            @RequestParam(name="orgType",defaultValue="") String orgType,
            @RequestParam(name="fromDate",defaultValue="") String fromDate,
            @RequestParam(name="endDate",defaultValue="") String endDate,
            @RequestParam(name="fileType",defaultValue="") String fileType,


            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {

        System.out.println("collectqQuery 开始执行============================");



        if(fromDate.equals("")){
            fromDate = "2000-01-01";
        }

        if(endDate.equals("")){
            endDate = "2099-12-12";
        }


        List<Map<String,Object>> resultList = new ArrayList<>();


        //根据orgType得到机构类型
       OrgType orgTypeTmp1 = new OrgType();
       orgTypeTmp1.setOrgtype(Integer.parseInt(orgType));
       String orgTypeName = orgTypeService.query(orgTypeTmp1).get(0).getTypename();


       //根据fileType得到文件类型
        String fileName = "";
        if(fileType.equals("1")){
            fileName = "附件1：专业代理、经纪用表（2020修订版）.XLS";
        }else if(fileType.equals("2")){
            fileName = "附件2：公估机构用表.xls";
        }else if(fileType.equals("3")){
            fileName = "附件3：合作销售寿险公司产品统计表.xls";
        }else if(fileType.equals("4")){
            fileName = "附件4：银邮代理机构用表（2020年3月修订版）.XLS";
        }






        //存储某个机构类型下的所有任务TaskComplete（包括任务类型，包括筛选时间）
        List<TaskComplete> allTaskComplete = new ArrayList<>();




        //根据orgType查询,得到机构id（orgid）
        OrgType orgTypeTmp = orgTypeService.findAllByOrgTpe(Integer.parseInt(orgType) ).get(0);
        //开始查询这些机构id下已经完成的任务
        for(OrgInfo orgInfo:orgTypeTmp.getOrgs()){

            TaskComplete taskComplete = new TaskComplete();
            //查询任务状态为完成
            taskComplete.setIscomplete(1);
            taskComplete.setOrgid(orgInfo.getOrgid());

            //查询该机构id下已经完成的任务，在规定的时间范围了
            List<TaskComplete> taskCompleteList = taskCompleteService.queryByCompleteTime(taskComplete,fromDate,endDate);

            //开始查询任务标题，任务描述，任务发布时间，开始时间，结束时间信息
            //一个机构下的所有任务
            for(TaskComplete taskCompleteTmp : taskCompleteList){

                Map<String,String> resultMapTmp = new HashMap<>();

                Task task = new Task();
                task.setId(taskCompleteTmp.getTaskid());
                Task task1 = taskService.query(task).get(0);

                //如果是要查询的任务类型就进行存储
                if(task1.getFiletype().equals(fileType)){
//                    resultMapTmp.put("tasktitle",task1.getTasktitle());
//                    resultMapTmp.put("taskdescribe",task1.getTaskdescribe());
//                    resultMapTmp.put("createtime",task1.getCreatetime());
//                    //resultMapTmp.put("fromdate",task1.getFromdate());
//                    //resultMapTmp.put("enddate",task1.getEnddate());
//                    resultMapTmp.put("taskcompleteid", taskCompleteTmp.getId()+"");
//                    resultMapTmp.put("orgName",orgInfo.getOrgname() );
//                    resultMapTmp.put("period",task1.getPeriod());

                    allTaskComplete.add(taskCompleteTmp);

                }
            }
        }





        //如果没有任何记录
        if(allTaskComplete.size()==0){
            System.out.println("没有查询到任何数据");
            return null;
        }


        //根据周期进行汇总,其中key存储的是每年的季度，value存储是当前季度下的taskcompleteid
        Map<String,List<String >> collectByperoid = new HashMap<>();

        for(TaskComplete taskComplete:allTaskComplete){
            //如果没有包含就添加
            if(!collectByperoid.containsKey(taskComplete.getCompletetime())){
                List<String> taskCompleteIds = new ArrayList<>();
                taskCompleteIds.add(  taskComplete.getId() + "");
                collectByperoid.put(taskComplete.getCompletetime(),taskCompleteIds);
            }else{

                List<String> taskCompleteIds = collectByperoid.get(taskComplete.getCompletetime());
                taskCompleteIds.add( taskComplete.getId() + "");
                collectByperoid.put(taskComplete.getCompletetime(),taskCompleteIds);
            }
        }




        //构造返回结果
        for (String key : collectByperoid.keySet()) {
            Map<String,Object> tmp = new HashMap<>();

            tmp.put("period",key);
            tmp.put("orgTypeName",orgTypeName);
            tmp.put("collect",collectByperoid.get(key));
            tmp.put("fileName",fileName);

            resultList.add(tmp);
        }

        return resultList;

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
            List<TaskComplete> taskCompleteList = taskCompleteService.queryByCompleteTime(taskComplete,fromDate,endDate);

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





        if(fileType.equals("3")){

            Table3 table3Tmp = new Table3();
            table3Tmp.setTaskcompleteid(Integer.parseInt(taskCompleteId));
            List<Table3> table3s = table3Service.query(table3Tmp,"2000-01-01","2099-12-12");


            //如果没查询到记录
            if(table3s==null || table3s.size()==0){
                System.out.println("没有查询到记录");
                return null;
            }


            //开始查询管理机构名称,管理员姓名,填表人姓名，填表人电话
            Userinfo userinfo = userinfoService.findById(table3s.get(0).getUserid());
            OrgInfo orgInfoTmp = new OrgInfo();
            orgInfoTmp.setOrgid(userinfo.getOrgid());
            OrgInfo orgInfo = orgInfoService.query(orgInfoTmp).get(0);
            String orgName = orgInfo.getOrgname();
            String manager = orgInfo.getManager();
            String userName = userinfo.getUsername();
            String tel = userinfo.getTelphone();


            //查询任务标题
            TaskComplete taskCompleteTmp = new TaskComplete();
            taskCompleteTmp.setId(table3s.get(0).getTaskcompleteid());
            Integer taskId = taskCompleteService.query(taskCompleteTmp).get(0).getTaskid();
            Task taskTmp = new Task();
            taskTmp.setId(taskId);
            Task task = taskService.query(taskTmp).get(0);
            String period = task.getPeriod();



            //开始生成表
            String filePath = downloadService.downloadExcel3(table3s,false,fileType,manager,orgName,period,userName,tel);


            return  filePath;

        }else{

            Table1 table1Tmp = new Table1();
            table1Tmp.setTaskcompleteid(Integer.parseInt(taskCompleteId));
            Table1 table1 = table1Service.query(table1Tmp,"2000-01-01","2099-12-12").get(0);


            //如果没查询到记录
            if(table1==null){
                System.out.println("没有查询到记录");
                return null;
            }


            //开始查询管理机构名称,管理员姓名,填表人姓名，填表人电话
            Userinfo userinfo = userinfoService.findById(table1.getUserid());
            OrgInfo orgInfoTmp = new OrgInfo();
            orgInfoTmp.setOrgid(userinfo.getOrgid());
            OrgInfo orgInfo = orgInfoService.query(orgInfoTmp).get(0);
            String orgName = orgInfo.getOrgname();
            String manager = orgInfo.getManager();
            String userName = userinfo.getUsername();
            String tel = userinfo.getTelphone();


            //查询任务标题
            TaskComplete taskCompleteTmp = new TaskComplete();
            taskCompleteTmp.setId(table1.getTaskcompleteid());
            Integer taskId = taskCompleteService.query(taskCompleteTmp).get(0).getTaskid();
            Task taskTmp = new Task();
            taskTmp.setId(taskId);
            Task task = taskService.query(taskTmp).get(0);
            String period = task.getPeriod();




            //开始生成表
            String filePath = downloadService.downloadExcel1(table1,false,fileType,manager,orgName,period,userName,tel);


            return  filePath;



        }
    }





    //汇总下载
    @PostMapping("/collectDownload")
    public Object collectDownload(
            @RequestParam(name="orgType",defaultValue="") String orgType,
            @RequestParam(name="period",defaultValue="") String period,
            @RequestParam(name="orgTypeName",defaultValue="") String orgName,
            @RequestParam(name="fileType",defaultValue="") String fileType,
            @RequestParam(name="collect",defaultValue="") String collect,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {

        System.out.println("collectDownload开始执行============================");





        //得到前端发送过来需要下载的taskcompleteid
        List<String> allTaskCompleteId = new ArrayList<>();

        Map maps  = (Map)JSON.parse(collect);
        for(int i=0;i<maps.size();i++){
            String tmp = maps.get(i+"")+"";
            allTaskCompleteId.add(tmp);
        }





        if(fileType.equals("3")){

            List<Table3> table3Result = table3Service.collectFind(allTaskCompleteId);

            //如果没查询到记录
            if(table3Result==null || table3Result.size()==0){
                System.out.println("没有查询到记录");
                return null;
            }



            //开始生成表
            String filePath = downloadService.downloadExcel3(table3Result,true,fileType,null,orgName,period,null,null);

            return  filePath;
        }else{

            Table1 table1Result = table1Service.collectFind(allTaskCompleteId);


            //如果没查询到记录
            if(table1Result==null){
                System.out.println("没有查询到记录");
                return null;
            }



            //开始生成表
            String filePath = downloadService.downloadExcel1(table1Result,true,fileType,null,orgName,period,null,null);

            return  filePath;
        }

    }


}










