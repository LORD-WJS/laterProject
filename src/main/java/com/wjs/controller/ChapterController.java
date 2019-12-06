package com.wjs.controller;

import com.wjs.entity.Album;
import com.wjs.entity.Chapter;
import com.wjs.entity.PageBean;
import com.wjs.service.ChapterService;
import com.wjs.util.HttpFilePath;
import com.wjs.util.MusicUtil;
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
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("showByPage")
    public Map<String,Object> showBanners(Integer rows, Integer page,String albumId){
        logger.info("进入章节分页查询-------------------------");
        PageBean<Chapter> pb = chapterService.findByPage(page, rows,albumId);

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
    public Map<String,String> edit(String oper, Chapter chapter, HttpServletRequest request,String albumId){
        logger.info("进入album增删改Controller");
        logger.info(albumId);
        //添加
        if(oper.equals("add")){
            chapter.setId(UUID.randomUUID().toString()).setCreateDate(new Date()).setAlbumId(albumId);
            logger.info("章节添加："+chapter);
            logger.info(chapter.toString());
            Map<String, String> map = chapterService.addChapter(chapter);
            return map;
        }else if(oper.equals("edit")){
            Chapter b = chapterService.findOne(new Chapter().setId(chapter.getId()));
            System.out.println("b:"+b);
            chapter.setAudio(b.getAudio()).setName(b.getName()).setCreateDate(b.getCreateDate())
                    .setAudioSize(b.getAudioSize()).setAlbumId(b.getAlbumId());
            logger.info("章节更新："+chapter);
            Map<String, String> map = chapterService.modify(chapter);
            return map;
        }else{
            String realPath = request.getSession().getServletContext().getRealPath("/statics/audio/mp3");
            Chapter f1= chapterService.findOne(new Chapter().setId(chapter.getId()));
            String filePath=realPath+"/"+f1.getName();
            File f2=new File(filePath);
            f2.delete();

            Map<String, String> map = chapterService.remove(chapter);
            return map;
        }
    }

    @RequestMapping("upload")
    public void upload(String id, MultipartFile audio, HttpServletRequest request) throws IOException {
        System.out.println("audio:"+audio);
        if(!audio.getOriginalFilename().equals("")){
            logger.info("进入文件上传----------------------------------------------------------");
            //处理文件上传
            //根据相对upload获取绝对upload路径
            String realPath = request.getSession().getServletContext().getRealPath("/statics/audio/mp3");
            // 判断路径文件夹是否存在
            File file = new File(realPath);
            if(!file.exists()){
                file.mkdirs();
            }
            //修改文件名  新的文件名生成策略  uuid   时间戳方式
            String newFileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                    + UUID.randomUUID().toString().replace("-","");
            //String fileNameSuffix = aaa.getOriginalFilename().substring(aaa.getOriginalFilename().lastIndexOf("."));
            String fileNameSuffix = "."+ FilenameUtils.getExtension(audio.getOriginalFilename());

            //新的文件名
            String newFileName = newFileNamePrefix+fileNameSuffix;
            //创建日期目录
            String dateDirString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            File dateDir = new File(realPath, dateDirString);
            if(!dateDir.exists()){
                dateDir.mkdirs();
            }
            logger.info(dateDirString);
            //网络路径
            String uri = HttpFilePath.getURI(request, "/statics/audio/mp3/" + dateDirString + "/" + newFileName);

            Chapter chapter1 = chapterService.findOne(new Chapter().setId(id));
            chapter1.setAudio(uri).setName(dateDirString+"/"+newFileName).setCreateDate(new Date())
                    .setAudioSize(""+String.format("%.1f",audio.getSize()/1024.0/1024.0)+"MB");
            audio.transferTo(new File(dateDir,newFileName));
            //计算音乐时长
            String time = MusicUtil.getMp3TrackLength(new File(realPath,chapter1.getName()));
            chapter1.setTime(time);
            logger.info("文件上传："+chapter1);
            chapterService.modify(chapter1);
        }
    }


}
