package com.cbrc.back.Controller;


import com.alibaba.fastjson.JSON;
import com.cbrc.back.model.OrgType;
import com.cbrc.back.model.Task;
import com.cbrc.back.model.TaskComplete;
import com.cbrc.back.service.OrgTypeService;
import com.cbrc.back.service.TaskCompleteService;
import com.cbrc.back.service.TaskService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Controller
public class IndexController {





    //删除任务
    @GetMapping("/")
    public String index(

            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {

        System.out.println("index 开始执行============================");



        return "index";
    }













}








