package com.wjs.controller;

import com.wjs.dao.MasterDao;
import com.wjs.entity.Album;
import com.wjs.entity.Master;
import com.wjs.entity.PageBean;
import com.wjs.service.MasterService;
import com.wjs.util.HttpFilePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("master")
public class MasterController {
    @Autowired
    private MasterService masterService;
    @Autowired
    private MasterDao masterDao;

    Logger logger = LoggerFactory.getLogger(getClass());


    @RequestMapping("showByPage")
    public Map<String,Object> showBanners(Integer rows, Integer page){
        PageBean<Master> pb = masterService.findByPage(page, rows);

        HashMap<String,Object> map=new HashMap<>();
        if(pb!=null){
            map.put("page",pb.getCurrentPage());
            map.put("total",pb.getTotalPage());
            map.put("records",pb.getTotalCount());
            map.put("rows",pb.getList());
            logger.info(pb.getList().toString());
        }
        return map;
    }

    @RequestMapping("searchAllMaster")
    public List<Master> searchAllMaster(){
        List<Master> masters = masterDao.selectAll();
        return masters;
    }

    @RequestMapping("edit")
    public Map<String,String> edit(String oper, Master master, HttpServletRequest request){
        logger.info("进入album增删改Controller");
        //添加
        if(oper.equals("add")){
            master.setId(UUID.randomUUID().toString()).setCreateDate(new Date());
            logger.info("上师添加："+master);
            logger.info(master.toString());
            Map<String, String> map = masterService.addMaster(master);
            return map;
        }else if(oper.equals("edit")){
            Master b = masterService.findOne(new Master().setId(master.getId()));
            System.out.println("b:"+b);
            master.setFace(b.getFace()).setName(b.getName()).setCreateDate(b.getCreateDate());
            logger.info("上师更新："+master);
            Map<String, String> map = masterService.modify(master);
            return map;
        }else{
            String realPath = request.getSession().getServletContext().getRealPath("/statics/master/face");
            Master f1= masterService.findOne(new Master().setId(master.getId()));
            String filePath=realPath+"/"+f1.getName();
            File f2=new File(filePath);
            f2.delete();

            Map<String, String> map = masterService.remove(master);
            return map;
        }
    }

    @RequestMapping("upload")
    public void upload(String id, MultipartFile face, HttpServletRequest request) throws IOException {
        System.out.println("cover:"+face);
        if(!face.getOriginalFilename().equals("")){
            logger.info("进入上师头像上传----------------------------------------------------------");
            String path="/statics/master/face";
            //获取绝对路径
            String realPath= HttpFilePath.getRealPath(request,path);
            String newFileName = HttpFilePath.getNewFileName(face);

            //创建日期目录
            String dateDirString =HttpFilePath.getDateDirString();
            File dateDir =HttpFilePath.getDateDir(realPath,dateDirString);
            logger.info(dateDirString);
            //网络路径
            String uri = HttpFilePath.getURI(request, path+"/" + dateDirString + "/" + newFileName);

            Master master1 = masterService.findOne(new Master().setId(id));
            master1.setFace(uri).setName(dateDirString+"/"+newFileName).setCreateDate(new Date());
            logger.info("文件上传："+master1);
            face.transferTo(new File(dateDir,newFileName));
            masterService.modify(master1);
        }
    }



}
