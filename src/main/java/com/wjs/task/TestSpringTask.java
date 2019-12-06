package com.wjs.task;//package com.baizhi.task;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.wjs.dao.MasterDao;
import com.wjs.entity.Master;
import com.wjs.util.DateTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 本类启动项目自动调用  异步执行
 * Created by HIAPAD on 2019/12/3.
 * fixedDelay: 当定时任务执行完毕时开始计时// * fixedRate: 当定时任务开启时计时
 */
@Component
@Async
public class TestSpringTask {
    @Autowired
    private MasterDao masterDao;

//    @Scheduled(cron = "0/10 * * * * ? ")
//    public void task01() throws InterruptedException {
//        System.out.println("生成EXCEL");
//        List<Master> masters = masterDao.selectAll();
//        String date = DateTools.dateToStr(new Date(), "yyyy年MM月dd日HH时mm分ss秒");
//        String fileName = "C:/Users/admin.DESKTOP-5CQ2AJF/Desktop/后期项目/POI练习/"+date+"Master.xlsx";
//        EasyExcel.write(fileName,Master.class).sheet().doWrite(masters);
//
//    }


//    @Scheduled(fixedDelay = 3000)
//    public void task01() throws InterruptedException {
//        System.out.println(Thread.currentThread().getName());
//        Thread.sleep(5000);
//        System.out.println(Thread.currentThread().getName());
//        System.out.println("task1"+new Date());
//    }

//    @Scheduled(fixedRate = 3000)
//    public void task02() throws InterruptedException {
//        Thread.sleep(5000);
//        //System.out.println(Thread.currentThread().getName());
//        System.out.println("task2"+new Date());
//    }
}
