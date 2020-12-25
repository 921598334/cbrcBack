package com.cbrc.back.tools;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ExcelTool {

    //fileName:模版名称
    //value:需要填充的直，其按照key，value存储，
    public void writeExcelPOI(String fileName,Map<String,String> value) {
        try {


            String fullPath = System.getProperty("user.dir")+"/src/main/resources/static/template/"+fileName;
            System.out.print(fullPath);

            //生成excel放入该路径下
            String outPath = System.getProperty("user.dir")+"/src/main/resources/static/download/";



            XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(fullPath));





            XSSFSheet xSheet = xwb.getSheetAt(0);  //获取excel表的第一个sheet

            for (int i = 0; i <= xSheet.getLastRowNum(); i++) {  //遍历所有的行

                if(xSheet.getRow(i)==null){ //这行为空执行下次循环
                    System.out.println("当前行为空");
                    continue;
                }

                for (int j = 0; j <=  xSheet.getRow(i).getPhysicalNumberOfCells(); j++) {  //遍历当前行的所有列
                    if(xSheet.getRow(i).getCell(j)==null){//为空执行下次循环
//                      System.out.println("当前单元格为空");
                        continue;
                    }

                    String  fillStr = (xSheet.getRow(i)).getCell(j).toString();//获取当前单元格的数据


                    if(fillStr.equals("")){
                        //(xSheet.getRow(i)).getCell(j).setCellValue();
                    }

                }
            }

            FileOutputStream out = new FileOutputStream(outPath+System.currentTimeMillis());
            xwb.write(out);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
