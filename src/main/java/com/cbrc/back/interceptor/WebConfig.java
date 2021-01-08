package com.cbrc.back.interceptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    //String fullPath = System.getProperty("user.dir")+"/src/main/resources/static/download/";
    String fullPath = System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"static"+File.separator+"download"+File.separator;

    @Autowired
    LoginInterceptor loginInterceptor;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){

        //当在浏览器上访问localhost：//upload/xxx时会自动去访问后面配置的路径
        //registry.addResourceHandler("/download/**").addResourceLocations("file:"+fullPath,"classpath:/static/download/"); //添加这一行
        registry.addResourceHandler("/download/**").addResourceLocations("file:"+fullPath); //添加这一行

    }


    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        String[] excludes = new String[]{"/","/upload/**","/login/**","/sign/**","/error/**","/static/**","/css/**","/fonts/**","/img/**","/js/**","/ckeditor/**","/logout/**"
               };
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(excludes);

    }

}
