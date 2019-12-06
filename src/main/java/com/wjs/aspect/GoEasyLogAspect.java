package com.wjs.aspect;

import com.wjs.annotation.GoEasyAnnotation;
import com.wjs.annotation.LogAnnotation;
import com.wjs.util.DateTools;
import io.goeasy.GoEasy;
import org.apache.commons.io.FileUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HIAPAD on 2019/11/28.
 */
@Aspect
@Configuration
public class GoEasyLogAspect {
    @Autowired
    private HttpServletRequest request;

    @Around("@annotation(com.wjs.annotation.GoEasyAnnotation)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint){
        HttpSession session = request.getSession();
        //session.setAttribute("admin","admin");
        String admin = (String) session.getAttribute("admin");
        // 时间
        String date = DateTools.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
        // 获取方法名
        String name = proceedingJoinPoint.getSignature().getName();
        // 获取注解信息
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        GoEasyAnnotation annotation = signature.getMethod().getAnnotation(GoEasyAnnotation.class);
        String value = annotation.value();
        String status=null;
        String line=null;
        try {
            Object proceed = proceedingJoinPoint.proceed();
            status = "success";
            line=admin+" "+date+" "+name+" "+status+"   操作:"+value;
            System.out.println(line);
            //通知用户注册趋势 分布界面刷新
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-96f06067e18b4e4f83fec4a0315833f1");
            goEasy.publish("cmfz", "数据已更新!");//content : json字符串

            return proceed;
        } catch (Throwable throwable) {
            String realPath = request.getSession().getServletContext().getRealPath("/statics");
            status = "error";
            line=admin+" "+date+" "+name+" "+status+"   操作:"+value;
            System.out.println(line);
            throwable.printStackTrace();
            return null;
        }finally {
            //持久化日志信息
            List<String> lines=new ArrayList<>();
            lines.add(line);
            String realPath = request.getSession().getServletContext().getRealPath("/statics");
            try {
                FileUtils.writeLines(new File(realPath+"/log/管理员操作日志.txt"),lines,true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
