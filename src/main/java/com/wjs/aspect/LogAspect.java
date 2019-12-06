package com.wjs.aspect;

import com.wjs.annotation.LogAnnotation;
import com.wjs.util.DateTools;
import org.apache.commons.io.FileUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
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
public class LogAspect {
    @Autowired
    private HttpServletRequest request;

    @Around("@annotation(com.wjs.annotation.LogAnnotation)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint){
        /*
            谁 时间 事件 成功与否
         */
        // 谁
        HttpSession session = request.getSession();
        //session.setAttribute("admin","admin");
        String admin = (String) session.getAttribute("admin");
        // 时间
        String date = DateTools.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
        // 获取方法名
        String name = proceedingJoinPoint.getSignature().getName();
        // 获取注解信息
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        LogAnnotation annotation = signature.getMethod().getAnnotation(LogAnnotation.class);
        String value = annotation.value();
        String status=null;
        String line=null;
        try {
            Object proceed = proceedingJoinPoint.proceed();
            status = "success";
            line=admin+" "+date+" "+name+" "+status+"   操作:"+value;
            System.out.println(line);
            return proceed;
        } catch (Throwable throwable) {
            String realPath = request.getSession().getServletContext().getRealPath("/statics");
            status = "error";
            line=admin+" "+date+" "+name+" "+status+"   操作:"+value;
            System.out.println(line);
            throwable.printStackTrace();
            return null;
        }
        finally {
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
