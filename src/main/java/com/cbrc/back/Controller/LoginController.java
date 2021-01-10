package com.cbrc.back.Controller;



import javax.servlet.http.Cookie;
import com.cbrc.back.model.Userinfo;
import com.cbrc.back.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@CrossOrigin("*")
@RestController
public class LoginController {

    @Autowired
    UserinfoService userinfoService;




    @PostMapping("/login")
    public Object login(@RequestParam(name="username",defaultValue="") String username,
                          @RequestParam(name="password",defaultValue="") String password,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          Model model) throws Exception {

        System.out.println("login开始执行============================");

        Userinfo userinfoTmp = new Userinfo();
        userinfoTmp.setUsername(username);
        userinfoTmp.setPassword(password);


        Userinfo userinfo = userinfoService.checkUser(userinfoTmp);

        if(userinfo == null){
            Map<String,String> error = new HashMap<>();
            error.put("F","用户名不存在或者密码错误");
            return error;
        }else{
            System.out.println("成功查询到用户============================");

            //随机生成一个token
            String token = UUID.randomUUID().toString();

            userinfo.setToken(token);

            userinfoService.update(userinfo);


            //把用户信息存储到cookie中
//            response.addCookie(new Cookie("token",userinfo.getToken()));
//            response.addCookie(new Cookie("userName",userinfo.getUsername()));


            return userinfo;
        }




    }



    @GetMapping("/logout")
    public String logout(
            HttpServletRequest request,
            HttpServletResponse response
    ){

        request.getSession().removeAttribute("admin");

        //java删除cookie的方法
        Cookie cookie = new Cookie("token",null);
        response.addCookie(cookie);
        cookie.setMaxAge(0);
        //删除session

        request.getSession().removeAttribute("admin");



        System.out.println("已经退出登陆");
        return  "redirect:/";
    }


}








