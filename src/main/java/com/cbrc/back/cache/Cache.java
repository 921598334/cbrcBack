package com.cbrc.back.cache;



import com.cbrc.back.model.Table1;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cache {

    public static String[] get(){

        String dbString="";
        String classString="";


        Class<?> clz = Table1.class;
        // 获取实体类的所有属性，返回Field数组
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            dbString  = dbString+ field.getName()+",";
            classString =classString+ "#{"+classString +"}," ;
        }


        return new String[]{dbString.substring(0,dbString.length()-1),classString.substring(0,classString.length()-1)};
    }




}
