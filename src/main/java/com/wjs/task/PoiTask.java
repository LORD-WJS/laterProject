package com.wjs.task;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.wjs.dao.MasterDao;
import com.wjs.entity.Master;
import com.wjs.util.DateTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by HIAPAD on 2019/12/3.
 * 本类为定时任务类
 * 通过访问POIController调用本类，开启定时器，定期生成excel
 */
@Component
public class PoiTask {
    private String path;
    @Autowired
    private MasterDao masterDao;
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        return new ThreadPoolTaskScheduler();
    }
    public void run(String path){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // 业务代码
                System.out.println("生成EXCEL");
                List<Master> masters = masterDao.selectAll();
                String date = DateTools.dateToStr(new Date(), "yyyy年MM月dd日HH时mm分ss秒");
//                String fileName = "C:/Users/admin.DESKTOP-5CQ2AJF/Desktop/后期项目/POI练习/"+date+"Master.xlsx";
                String fileName = path+"/"+date+"Master.xlsx";
                EasyExcel.write(fileName,Master.class).sheet().doWrite(masters);
            }
        };
        threadPoolTaskScheduler.schedule(runnable,new CronTrigger("0 0 0 * * Sun"));
    }
    public void shutdown(){
        threadPoolTaskScheduler.shutdown();
    }
}
