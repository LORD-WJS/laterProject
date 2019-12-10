package com.wjs.controller;

import com.wjs.entity.Admin;
import com.wjs.service.AdminService;
import com.wjs.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    public String login(HttpServletRequest request, String username, String password,String captchaCode){
        HttpSession session = request.getSession();
        String securityCode=(String)session.getAttribute("securityCode");
        System.out.println(securityCode);
        System.out.println(captchaCode);
        if(securityCode.equals(captchaCode)){//验证码正确
            Admin admin = adminService.findByUsername(username);
            if(admin!=null){
                String salt = admin.getSalt();
                password = MD5Utils.getPassword(salt + password);
                if(password.equals(admin.getPassword())) {
                    session.setAttribute("admin",username);
                    return "success";
                }
                else return "密码错误";
            }else{
                return "账号错误";
            }
        }else{//验证码错误返回的信息
            return "验证码错误";
        }
    }


}
