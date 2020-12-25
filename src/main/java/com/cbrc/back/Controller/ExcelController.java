package com.cbrc.back.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@CrossOrigin("*")
@RestController
public class ExcelController {




    //导出个人的积分明细
    @GetMapping("/ex")
    public String exportPrivateScoreDetailPrivate(@RequestParam(name="id") Integer id,
                                        Model model,
                                        HttpServletResponse response, HttpServletRequest request

    ) throws IOException {



        System.out.print("调用了"+id);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return "success";


    }












}



