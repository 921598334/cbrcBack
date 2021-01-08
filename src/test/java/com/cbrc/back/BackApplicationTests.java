package com.cbrc.back;

import com.cbrc.back.model.Table3;

import com.cbrc.back.service.TaskService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@SpringBootTest
class BackApplicationTests {


    @Autowired
    TaskService taskService;

    @Test
    void contextLoads() {



        String dbString="";
        String classString="";
        String sumString = "";

        Class<?> clz = Table3.class;
        // 获取实体类的所有属性，返回Field数组
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            dbString  = dbString+ field.getName()+",";
            classString =classString+ "#{"+field.getName() +"}," ;
            sumString = sumString + "sum("+field.getName() +") as "+field.getName()+",";
        }

        //System.out.println(dbString);
        System.out.println(classString);
        //System.out.println(sumString);
    }






    @Test
    void testMapper() {


        String fullPath = System.getProperty("user.dir");

        System.out.println(fullPath);


    }

}
