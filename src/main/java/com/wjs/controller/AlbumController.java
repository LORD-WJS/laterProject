package com.wjs.controller;

import com.wjs.entity.Album;
import com.wjs.entity.PageBean;
import com.wjs.service.AlbumService;
import com.wjs.util.HttpFilePath;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    Logger logger = LoggerFactory.getLogger(getClass());


    @RequestMapping("showByPage")
    public Map<String,Object> showBanners(Integer rows, Integer page){
        PageBean<Album> pb = albumService.findByPage(page, rows);

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


    @RequestMapping("edit")
    public Map<String,String> edit(String oper, Album album, HttpServletRequest request){
        logger.info("进入album增删改Controller");
        //添加
        if(oper.equals("add")){
            album.setId(UUID.randomUUID().toString()).setCreateDate(new Date());
            logger.info("专辑添加："+album);
            logger.info(album.toString());
            Map<String, String> map = albumService.addAlbum(album);
            return map;
        }else if(oper.equals("edit")){
            Album b = albumService.findOne(new Album().setId(album.getId()));
            System.out.println("b:"+b);
            album.setCover(b.getCover()).setName(b.getName()).setCreateDate(b.getCreateDate());
            logger.info("专辑更新："+album);
            Map<String, String> map = albumService.modify(album);
            return map;
        }else{
            String realPath = request.getSession().getServletContext().getRealPath("/statics/audio/img");
            Album f1= albumService.findOne(new Album().setId(album.getId()));
            String filePath=realPath+"/"+f1.getName();
            File f2=new File(filePath);
            f2.delete();

            Map<String, String> map = albumService.remove(album);
            return map;
        }
    }

    @RequestMapping("upload")
    public void upload(String id, MultipartFile cover, HttpServletRequest request) throws IOException {
        System.out.println("cover:"+cover);
        if(!cover.getOriginalFilename().equals("")){
            logger.info("进入文件上传----------------------------------------------------------");
            String path="/statics/audio/img";
            //获取绝对路径
            String realPath=HttpFilePath.getRealPath(request,path);
            String newFileName = HttpFilePath.getNewFileName(cover);

            //创建日期目录
            String dateDirString =HttpFilePath.getDateDirString();
            File dateDir =HttpFilePath.getDateDir(realPath,dateDirString);
            logger.info(dateDirString);
            //网络路径
            String uri = HttpFilePath.getURI(request, path+"/" + dateDirString + "/" + newFileName);

            Album album1 = albumService.findOne(new Album().setId(id));
            album1.setCover(uri).setName(dateDirString+"/"+newFileName).setCreateDate(new Date());
            logger.info("文件上传："+album1);
            cover.transferTo(new File(dateDir,newFileName));
            albumService.modify(album1);
        }
    }


}
