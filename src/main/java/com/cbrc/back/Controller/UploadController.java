package com.cbrc.back.Controller;




import com.alibaba.fastjson.JSON;

import com.cbrc.back.mapper.Table1Mapper;
import com.cbrc.back.mapper.Table3Mapper;
import com.cbrc.back.mapper.TableStructMapper;
import com.cbrc.back.model.Table1;
import com.cbrc.back.model.Table3;
import com.cbrc.back.model.TableStruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;





@CrossOrigin("*")
@RestController
public class UploadController {


    @Autowired
    TableStructMapper tableStructMapper;

    @Autowired
    Table1Mapper table1Mapper;
    @Autowired
    Table3Mapper table3Mapper;


    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");


    @PostMapping("/upload")
    public Object upload(@RequestParam(name="dataSourceTmp",defaultValue="") String uploadInfoStr,
                         @RequestParam(name="orgName",defaultValue="") String orgName,
                         @RequestParam(name="managerName",defaultValue="") String managerName,
                         @RequestParam(name="creator",defaultValue="") String creator,
                         @RequestParam(name="tel",defaultValue="") String tel,
                         @RequestParam(name="period",defaultValue="") String period,
                         @RequestParam(name="fileType",defaultValue="") String fileType,
                         @RequestParam(name="userid",defaultValue="") String userid,


                          HttpServletRequest request,
                          HttpServletResponse response,
                          Model model) throws Exception {

        System.out.println("upload开始执行============================");


        //其中文件类型1，2，4是相同的操作，
        if(fileType.equals("3")){

            Table3 table3 = new Table3();

            Map maps  = (Map)JSON.parse(uploadInfoStr);

            for(int i=0;i<maps.size();i++){
                Map<String,String> tmp = (Map<String,String>)maps.get(i+"");

                table3.setCol1(tmp.get("col1"));
                table3.setCol2(tmp.get("col2"));
                table3.setCol3(tmp.get("col3"));
                table3.setCol4(tmp.get("col4"));
                table3.setCol5(tmp.get("col5"));
                table3.setCol6(tmp.get("col6"));
                table3.setCol7(tmp.get("col7"));
                table3.setCol8(tmp.get("col8"));
                table3.setCol9(tmp.get("col9"));
                table3.setCol10(tmp.get("col10"));
                table3.setCol11(tmp.get("col11"));
                table3.setUserid(Integer.parseInt(userid) );

                table3Mapper.insert(table3);
            }



        }else{

            Table1 table1 = new Table1();
            table1.setUserid(Integer.parseInt(userid) );
            table1.setOrgName(orgName);
            table1.setManagerName(managerName);
            table1.setCreator(creator);
            table1.setTel(tel);
            table1.setPeriod(period);
            table1.setDate(dateformat.format(System.currentTimeMillis()));
            table1.setFiletype(fileType);

            Map maps  = (Map)JSON.parse(uploadInfoStr);

            for(int i=0;i<maps.size();i++){
                Map<String,String> tmp = (Map<String,String>)maps.get(i+"");
                String amount = tmp.get("amount");
                String mark = tmp.get("mark");
                String item = tmp.get("cellname");

                if(item.contains("（1）")){
                    table1.setCol1(amount);
                    table1.setColmark1(mark);
                }else if(item.contains("（1.1）")){
                    table1.setCol1_1(amount);
                    table1.setColmark1_1(mark);
                }
                else if(item.contains("（1.11）")){
                    table1.setCol1_11(amount);
                    table1.setColmark1_11(mark);
                }
                else if(item.contains("（1.2）")){
                    table1.setCol1_2(amount);
                    table1.setColmark1_2(mark);
                } else if(item.contains("（1.21）")){
                    table1.setCol1_21(amount);
                    table1.setColmark1_21(mark);
                } else if(item.contains("（1.3）")){
                    table1.setCol1_3(amount);
                    table1.setColmark1_3(mark);
                }else if(item.contains("（1.4）")){
                    table1.setCol1_4(amount);
                    table1.setColmark1_4(mark);
                }else if(item.contains("（1.5）")){
                    table1.setCol1_5(amount);
                    table1.setColmark1_5(mark);
                }


                else if(item.contains("（2）")){
                    table1.setCol2(amount);
                    table1.setColmark2(mark);
                }else if(item.contains("（2.1）")){
                    table1.setCol2_1(amount);
                    table1.setColmark2_1(mark);
                }else if(item.contains("（2.11）")){
                    table1.setCol2_11(amount);
                    table1.setColmark2_11(mark);
                }else if(item.contains("（2.2）")){
                    table1.setCol2_2(amount);
                    table1.setColmark2_2(mark);
                }else if(item.contains("（2.3）")){
                    table1.setCol2_3(amount);
                    table1.setColmark2_3(mark);
                }


                else if(item.contains("（3）")){
                    table1.setCol3(amount);
                    table1.setColmark3(mark);
                }else if(item.contains("（3.1）")){
                    table1.setCol3_1(amount);
                    table1.setColmark3_1(mark);
                }else if(item.contains("（3.2）")){
                    table1.setCol3_2(amount);
                    table1.setColmark3_2(mark);
                }else if(item.contains("（3.3）")){
                    table1.setCol3_3(amount);
                    table1.setColmark3_3(mark);
                }




                else if(item.contains("（4）")){
                    table1.setCol4(amount);
                    table1.setColmark4(mark);
                }else if(item.contains("（4.1）")){
                    table1.setCol4_1(amount);
                    table1.setColmark4_1(mark);
                }else if(item.contains("（4.11）")){
                    table1.setCol4_11(amount);
                    table1.setColmark4_11(mark);
                }else if(item.contains("（4.2）")){
                    table1.setCol4_2(amount);
                    table1.setColmark4_2(mark);
                }else if(item.contains("（4.3）")){
                    table1.setCol4_3(amount);
                    table1.setColmark4_3(mark);
                }


                else if(item.contains("（5）")){
                    table1.setCol5(amount);
                    table1.setColmark5(mark);
                }else if(item.contains("（5.1）")){
                    table1.setCol5_1(amount);
                    table1.setColmark5_1(mark);
                }else if(item.contains("（5.2）")){
                    table1.setCol5_2(amount);
                    table1.setColmark5_2(mark);
                }else if(item.contains("（5.3）")){
                    table1.setCol5_3(amount);
                    table1.setColmark5_3(mark);
                }


                else if(item.contains("（6）")){
                    table1.setCol6(amount);
                    table1.setColmark6(mark);
                }else if(item.contains("（6.1）")){
                    table1.setCol6_1(amount);
                    table1.setColmark6_1(mark);
                }else if(item.contains("（6.2）")){
                    table1.setCol6_2(amount);
                    table1.setColmark6_2(mark);
                }else if(item.contains("（6.3）")){
                    table1.setCol6_3(amount);
                    table1.setColmark6_3(mark);
                }



                else if(item.contains("（7）")){
                    table1.setCol7(amount);
                    table1.setColmark7(mark);
                }else if(item.contains("（7.1）")){
                    table1.setCol7_1(amount);
                    table1.setColmark7_1(mark);
                }else if(item.contains("（7.2）")){
                    table1.setCol7_2(amount);
                    table1.setColmark7_2(mark);
                }



                else if(item.contains("（8）")){
                    table1.setCol8(amount);
                    table1.setColmark8(mark);
                }else if(item.contains("（8.1）")){
                    table1.setCol8_1(amount);
                    table1.setColmark8_1(mark);
                }else if(item.contains("（8.11）")){
                    table1.setCol8_11(amount);
                    table1.setColmark8_11(mark);
                }else if(item.contains("（8.2）")){
                    table1.setCol8_2(amount);
                    table1.setColmark8_2(mark);
                }


                else if(item.contains("（9）")){
                    table1.setCol9(amount);
                    table1.setColmark9(mark);
                }else if(item.contains("（9.1）")){
                    table1.setCol9_1(amount);
                    table1.setColmark9_1(mark);
                }else if(item.contains("（9.2）")){
                    table1.setCol9_2(amount);
                    table1.setColmark9_2(mark);
                }else if(item.contains("（9.3）")){
                    table1.setCol9_3(amount);
                    table1.setColmark9_3(mark);
                }

                else if(item.contains("（10）")){
                    table1.setCol10(amount);
                    table1.setColmark10(mark);
                }
                else if(item.contains("（10.1）")){
                    table1.setCol10_1(amount);
                    table1.setColmark10_1(mark);
                }else if(item.contains("（10.2）")){
                    table1.setCol10_2(amount);
                    table1.setColmark10_2(mark);
                }else if(item.contains("（10.3）")){
                    table1.setCol10_3(amount);
                    table1.setColmark10_3(mark);
                }


                else if(item.contains("（11.1）")){
                    table1.setCol11_1(amount);
                    table1.setColmark11_1(mark);
                }else if(item.contains("（11.2）")){
                    table1.setCol11_2(amount);
                    table1.setColmark11_2(mark);
                }else if(item.contains("（11.3）")){
                    table1.setCol11_3(amount);
                    table1.setColmark11_3(mark);
                }else if(item.contains("（11.11）")){
                    table1.setCol11_11(amount);
                    table1.setColmark11_11(mark);
                }

                else if(item.contains("（12.1）")){
                    table1.setCol12_1(amount);
                    table1.setColmark12_1(mark);
                }else if(item.contains("（12.2）")){
                    table1.setCol12_2(amount);
                    table1.setColmark12_2(mark);
                }else if(item.contains("（12.3）")){
                    table1.setCol12_3(amount);
                    table1.setColmark12_3(mark);
                }

                else if(item.contains("（13）")){
                    table1.setCol13(amount);
                    table1.setColmark13(mark);
                }else if(item.contains("（14）")){
                    table1.setCol14(amount);
                    table1.setColmark14(mark);
                }else if(item.contains("（15）")){
                    table1.setCol15(amount);
                    table1.setColmark15(mark);
                } else{
                    System.out.println("没有匹配");
                }


                try{
                    table1Mapper.insert(table1);
                }catch (Exception e){
                    e.printStackTrace();
                    Map<String,String> error = new HashMap<>();
                    error.put("F","数据插入错误");
                    return  error;
                }



            }


        }




        return  null;

    }







    @PostMapping("/getCellRequest")
    public Object getCellRequest(
                         HttpServletRequest request,
                         HttpServletResponse response,
                         Model model) throws Exception {

        System.out.println("getCellRequest开始执行============================");


        try{
            ArrayList<TableStruct> tableStructs = tableStructMapper.findAll();


            return  tableStructs;

        }catch (Exception e){
            e.printStackTrace();
            Map<String,String> error = new HashMap<>();
            error.put("F","查询错误");
            return error;
        }

    }




    }








