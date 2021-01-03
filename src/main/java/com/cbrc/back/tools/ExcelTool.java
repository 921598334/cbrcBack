package com.cbrc.back.tools;

import com.cbrc.back.mapper.TableStructMapper;
import com.cbrc.back.model.Table1;
import com.cbrc.back.model.Table3;
import com.cbrc.back.model.TableStruct;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Component
public class ExcelTool {


    @Autowired
    TableStructMapper tableStructMapper;


    //templateId:模版索引
    //value:需要填充的直，其按照key，value存储，
    public String writeExcelPOI1(Table1 table1,HttpServletResponse response) {
        try {

            String fileName = "";


            if(table1.getFiletype().equals("1")){
                fileName = "附件1：专业代理、经纪用表（2020修订版）.XLS";
            }else if(table1.getFiletype().equals("2")){
                fileName = "附件2：公估机构用表.xls";
            }else if(table1.getFiletype().equals("3")){
                fileName = "附件3：合作销售寿险公司产品统计表.xls";
            }else if(table1.getFiletype().equals("4")){
                fileName = "附件4：银邮代理机构用表（2020年3月修订版）.XLS";
            }



            String fullPath = System.getProperty("user.dir")+"/src/main/resources/static/template/"+fileName;




            HSSFWorkbook xwb = new HSSFWorkbook(new FileInputStream(fullPath));



            HSSFSheet xSheet = xwb.getSheetAt(0);  //获取excel表的第一个sheet



            //查询查询需要填充的值
            ArrayList<TableStruct> tableStructs = tableStructMapper.findByRepIdExcludeMark(table1.getFiletype());

            for(int i=0;i<tableStructs.size();i++){

                int rowId= Integer.parseInt(tableStructs.get(i).getRow_id()) ;
                int colId= Integer.parseInt(tableStructs.get(i).getCol_id()) ;
                String item = tableStructs.get(i).getCellname();


                //如果有这个符号，表示是金额，备注信息
                if(item.contains("（")){
                    String amountValue = "";
                    String markValue = "";

                    if(item.contains("（1）")){
                        amountValue = table1.getCol1();
                        markValue = table1.getColmark1();

                    }else if(item.contains("（1.1）")){
                        amountValue = table1.getCol1_1();
                        markValue = table1.getColmark1_1();
                    }
                    else if(item.contains("（1.11）")){
                        amountValue = table1.getCol1_11();
                        markValue = table1.getColmark1_1();
                    }
                    else if(item.contains("（1.2）")){
                        amountValue = table1.getCol1_2();
                        markValue = table1.getColmark1_2();
                    } else if(item.contains("（1.21）")){
                        amountValue = table1.getCol1_21();
                        markValue = table1.getColmark1_21();
                    }
                    else if(item.contains("（1.3）")){
                        amountValue =  table1.getCol1_3();
                        markValue = table1.getColmark1_3();
                    }else if(item.contains("（1.4）")){
                        amountValue = table1.getCol1_4();
                        markValue = table1.getColmark1_4();
                    }else if(item.contains("（1.5）")){
                        amountValue = table1.getCol1_5();
                        markValue = table1.getColmark1_5();
                    }


                    else if(item.contains("（2）")){
                        amountValue = table1.getCol2();
                        markValue = table1.getColmark2();
                    }else if(item.contains("（2.1）")){
                        amountValue = table1.getCol2_1();
                        markValue = table1.getColmark2_1();
                    }else if(item.contains("（2.11）")){
                        amountValue = table1.getCol2_11();
                        markValue = table1.getColmark2_11();
                    }else if(item.contains("（2.2）")){
                        amountValue = table1.getCol2_2();
                        markValue = table1.getColmark2_2();
                    }else if(item.contains("（2.3）")){
                        amountValue = table1.getCol2_3();
                        markValue = table1.getColmark2_3();
                    }


                    else if(item.contains("（3）")){
                        amountValue = table1.getCol3();
                        markValue = table1.getColmark3();
                    }else if(item.contains("（3.1）")){
                        amountValue = table1.getCol3_1();
                        markValue = table1.getColmark3_1();
                    }else if(item.contains("（3.2）")){
                        amountValue = table1.getCol3_2();
                        markValue = table1.getColmark3_2();
                    }else if(item.contains("（3.3）")){
                        amountValue = table1.getCol3_3();
                        markValue = table1.getColmark3_3();
                    }




                    else if(item.contains("（4）")){
                        amountValue = table1.getCol4();
                        markValue = table1.getColmark4();
                    }else if(item.contains("（4.1）")){
                        amountValue = table1.getCol4_1();
                        markValue = table1.getColmark4_1();
                    }else if(item.contains("（4.2）")){
                        amountValue = table1.getCol4_2();
                        markValue = table1.getColmark4_2();
                    }else if(item.contains("（4.3）")){
                        amountValue = table1.getCol4_3();
                        markValue = table1.getColmark4_3();
                    }

                    else if(item.contains("（4.11）")){
                        amountValue = table1.getCol4_11();
                        markValue = table1.getColmark4_11();
                    }


                    else if(item.contains("（5）")){
                        amountValue = table1.getCol5();
                        markValue = table1.getColmark5();
                    }else if(item.contains("（5.1）")){
                        amountValue = table1.getCol5_1();
                        markValue = table1.getColmark5_1();
                    }else if(item.contains("（5.2）")){
                        amountValue = table1.getCol5_2();
                        markValue = table1.getColmark5_2();
                    }else if(item.contains("（5.3）")){
                        amountValue = table1.getCol5_3();
                        markValue = table1.getColmark5_3();
                    }


                    else if(item.contains("（6）")){
                        amountValue = table1.getCol6();
                        markValue = table1.getColmark6();
                    }else if(item.contains("（6.1）")){
                        amountValue = table1.getCol6_1();
                        markValue = table1.getColmark6_1();
                    }else if(item.contains("（6.2）")){
                        amountValue = table1.getCol6_2();
                        markValue = table1.getColmark6_2();
                    }else if(item.contains("（6.3）")){
                        amountValue = table1.getCol6_3();
                        markValue = table1.getColmark6_3();
                    }



                    else if(item.contains("（7）")){
                        amountValue = table1.getCol7();
                        markValue = table1.getColmark7();
                    }else if(item.contains("（7.1）")){
                        amountValue = table1.getCol7_1();
                        markValue = table1.getColmark7_1();
                    }else if(item.contains("（7.2）")){
                        amountValue = table1.getCol7_2();
                        markValue = table1.getColmark7_2();
                    }



                    else if(item.contains("（8）")){
                        amountValue = table1.getCol8();
                        markValue = table1.getColmark8();
                    }else if(item.contains("（8.1）")){
                        amountValue = table1.getCol8_1();
                        markValue = table1.getColmark8_1();
                    }else if(item.contains("（8.11）")){
                        amountValue = table1.getCol8_11();
                        markValue = table1.getColmark8_11();
                    }else if(item.contains("（8.2）")){
                        amountValue = table1.getCol8_2();
                        markValue = table1.getColmark8_2();
                    }


                    else if(item.contains("（9）")){
                        amountValue = table1.getCol9();
                        markValue = table1.getColmark9();
                    }else if(item.contains("（9.1）")){
                        amountValue = table1.getCol9_1();
                        markValue = table1.getColmark9_1();
                    }else if(item.contains("（9.2）")){
                        amountValue = table1.getCol9_2();
                        markValue = table1.getColmark9_2();
                    }else if(item.contains("（9.3）")){
                        amountValue = table1.getCol9_3();
                        markValue = table1.getColmark9_3();
                    }


                    else if(item.contains("（10）")){
                        amountValue = table1.getCol10();
                        markValue = table1.getColmark10();
                    }
                    else if(item.contains("（10.1）")){
                        amountValue = table1.getCol10_1();
                        markValue = table1.getColmark10_1();
                    }else if(item.contains("（10.2）")){
                        amountValue = table1.getCol10_2();
                        markValue = table1.getColmark10_2();
                    }else if(item.contains("（10.3）")){
                        amountValue = table1.getCol10_3();
                        markValue = table1.getColmark10_3();
                    }


                    else if(item.contains("（11.1）")){
                        amountValue = table1.getCol11_1();
                        markValue = table1.getColmark11_1();
                    }else if(item.contains("（11.2）")){
                        amountValue = table1.getCol11_2();
                        markValue = table1.getColmark11_2();
                    }else if(item.contains("（11.3）")){
                        amountValue = table1.getCol11_3();
                        markValue = table1.getColmark11_3();
                    }else if(item.contains("（11.11）")){
                        amountValue = table1.getCol11_11();
                        markValue = table1.getColmark11_11();
                    }

                    else if(item.contains("（12.1）")){
                        amountValue = table1.getCol12_1();
                        markValue = table1.getColmark12_1();
                    }else if(item.contains("（12.2）")){
                        amountValue = table1.getCol12_2();
                        markValue = table1.getColmark12_2();
                    }else if(item.contains("（12.3）")){
                        amountValue = table1.getCol12_3();
                        markValue = table1.getColmark12_3();
                    }

                    else if(item.contains("（13）")){
                        amountValue = table1.getCol13();
                        markValue = table1.getColmark13();
                    }else if(item.contains("（14）")){
                        amountValue = table1.getCol14();
                        markValue = table1.getColmark14();
                    }else if(item.contains("（15）")){
                        amountValue = table1.getCol15();
                        markValue = table1.getColmark15();
                    } else{
                        System.out.println("没有匹配");
                    }

                    //设置金额
                    if((xSheet.getRow(rowId-1)).getCell(colId-1)!=null){
                        (xSheet.getRow(rowId-1)).getCell(colId-1).setCellValue(amountValue);
                    }
                    //设置备注
                    if((xSheet.getRow(rowId-1)).getCell(colId)!=null){
                        (xSheet.getRow(rowId-1)).getCell(colId).setCellValue(markValue);
                    }

                }else{

                    String cellValue = "";

                    if(item.contains("负责人")){
                        cellValue = table1.getManager();
                        if((xSheet.getRow(rowId-1)).getCell(colId-1)!=null){
                            (xSheet.getRow(rowId-1)).getCell(colId-1).setCellValue(cellValue);
                        }
                    }

                    if(item.contains("机构全称")){
                        cellValue = table1.getOrgName();
                        if((xSheet.getRow(rowId-1)).getCell(colId)!=null){
                            (xSheet.getRow(rowId-1)).getCell(colId).setCellValue(cellValue);
                        }
                    }

                    if(item.contains("填表人")){
                        cellValue = "填表人："+table1.getUserName()+"                            "+"联系电话："+table1.getTel();
                        if((xSheet.getRow(rowId-1)).getCell(colId-1)!=null){
                            (xSheet.getRow(rowId-1)).getCell(colId-1).setCellValue(cellValue);
                        }
                    }

                    if(item.contains("统计区间")){
                        cellValue = "统计区间："+table1.getDate().split("-")[0]+"年 "+ table1.getPeriod()+"季度";
                        if((xSheet.getRow(rowId-1)).getCell(colId-1)!=null){
                            (xSheet.getRow(rowId-1)).getCell(colId-1).setCellValue(cellValue);
                        }
                    }

                }


            }


            //生成excel放入该路径下
            String outPath = System.getProperty("user.dir")+"/src/main/resources/static/download/"+table1.getOrgName()+"-"+fileName+"-"+ System.currentTimeMillis()+".xls";

            //前端使用该路径下载
            String downloadPath = "127.0.0.1:8080/download/"+table1.getOrgName()+"-"+fileName+"-"+System.currentTimeMillis()+".xls";

            FileOutputStream out = new FileOutputStream(outPath);
            xwb.write(out);
            out.close();
            return downloadPath;



        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    public String writeExcelPOI3(List<Table3> table3s, HttpServletResponse response) {

        try {

            String fileName = "附件3：合作销售寿险公司产品统计表.xls";


            String fullPath = System.getProperty("user.dir")+"/src/main/resources/static/template/"+fileName;


            HSSFWorkbook xwb = new HSSFWorkbook(new FileInputStream(fullPath));


            HSSFSheet xSheet = xwb.getSheetAt(0);  //获取excel表的第一个sheet


            //需要技术是否需要移动最后行
            //14与15行需要移动
            //默认可以插入7条数据，如果超过7条需要移动
            if(table3s.size()>7){
                //需要移动几行
                int moveRow = table3s.size()-7;

                //从下标13开移动
                xSheet.shiftRows(13, moveRow+13,2,true,false);

            }








            int count =1;
            //一共有容器大小的行数
            for(int rowId=6;rowId<table3s.size()+6;rowId++){

                if(xSheet.getRow(rowId)==null){
                    xSheet.createRow(rowId);
                }

                //一共有12列
                for(int colId=0;colId<12;colId++){

                    if((xSheet.getRow(rowId)).getCell(colId)==null){
                        (xSheet.getRow(rowId)).createCell(colId);
                    }

                    if(colId==0){
                        if((xSheet.getRow(rowId)).getCell(colId)!=null){
                            (xSheet.getRow(rowId)).getCell(colId).setCellValue(count);
                        }
                    }else if(colId==1){
                        if((xSheet.getRow(rowId)).getCell(colId)!=null){
                            (xSheet.getRow(rowId)).getCell(colId).setCellValue(table3s.get(count-1).getCol1());
                        }
                    }else if(colId==11){
                        if((xSheet.getRow(rowId)).getCell(colId)!=null){
                            (xSheet.getRow(rowId)).getCell(colId).setCellValue(table3s.get(count-1).getCol11());
                        }
                    }else if(colId==2){
                        if((xSheet.getRow(rowId)).getCell(colId)!=null){
                            (xSheet.getRow(rowId)).getCell(colId).setCellValue(table3s.get(count-1).getCol2());
                        }
                    }else if(colId==3){
                        if((xSheet.getRow(rowId)).getCell(colId)!=null){
                            (xSheet.getRow(rowId)).getCell(colId).setCellValue(table3s.get(count-1).getCol3());
                        }
                    }else if(colId==4){
                        if((xSheet.getRow(rowId)).getCell(colId)!=null){
                            (xSheet.getRow(rowId)).getCell(colId).setCellValue(table3s.get(count-1).getCol4());
                        }
                    }else if(colId==5){
                        if((xSheet.getRow(rowId)).getCell(colId)!=null){
                            (xSheet.getRow(rowId)).getCell(colId).setCellValue(table3s.get(count-1).getCol5());
                        }
                    }else if(colId==6){
                        if((xSheet.getRow(rowId)).getCell(colId)!=null){
                            (xSheet.getRow(rowId)).getCell(colId).setCellValue(table3s.get(count-1).getCol6());
                        }
                    }else if(colId==7){
                        if((xSheet.getRow(rowId)).getCell(colId)!=null){
                            (xSheet.getRow(rowId)).getCell(colId).setCellValue(table3s.get(count-1).getCol7());
                        }
                    }else if(colId==8){
                        if((xSheet.getRow(rowId)).getCell(colId)!=null){
                            (xSheet.getRow(rowId)).getCell(colId).setCellValue(table3s.get(count-1).getCol8());
                        }
                    }else if(colId==9){
                        if((xSheet.getRow(rowId)).getCell(colId)!=null){
                            (xSheet.getRow(rowId)).getCell(colId).setCellValue(table3s.get(count-1).getCol9());
                        }
                    }else if(colId==10){
                        if((xSheet.getRow(rowId)).getCell(colId)!=null){
                            (xSheet.getRow(rowId)).getCell(colId).setCellValue(table3s.get(count-1).getCol10());
                        }
                    }

                }

                ++count;

            }


            //生成excel放入该路径下
            String outPath = System.getProperty("user.dir")+"/src/main/resources/static/download/"+table3s.get(0).getOrgName()+"-"+fileName+"-"+ System.currentTimeMillis()+".xls";

            //前端使用该路径下载
            String downloadPath = "127.0.0.1:8080/download/"+table3s.get(0).getOrgName()+"-"+fileName+"-"+System.currentTimeMillis()+".xls";

            FileOutputStream out = new FileOutputStream(outPath);
            xwb.write(out);
            out.close();
            return downloadPath;



        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }






}
