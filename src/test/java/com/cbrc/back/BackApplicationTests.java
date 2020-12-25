package com.cbrc.back;

import com.cbrc.back.cache.Cache;
import com.cbrc.back.model.Table1;
import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;

@SpringBootTest
class BackApplicationTests {

    @Test
    void contextLoads() {



        String dbString="";
        String classString="";


        Class<?> clz = Table1.class;
        // 获取实体类的所有属性，返回Field数组
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            //dbString  = dbString+ field.getName()+",";
            classString =classString+ "#{"+field.getName() +"}," ;
        }

        //System.out.println(dbString);
        System.out.println(classString);
    }

}
