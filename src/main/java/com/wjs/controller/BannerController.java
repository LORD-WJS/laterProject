package com.wjs.controller;

import com.wjs.entity.Banner;
import com.wjs.entity.PageBean;
import com.wjs.service.BannerService;
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
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("showBanners")
    public Map<String,Object> showBanners(Integer rows, Integer page){
        PageBean<Banner> pb = bannerService.findByPage(page, rows);

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
    public Map<String,String> edit(String oper,Banner banner,MultipartFile cover,HttpServletRequest request){
        logger.info("进入增删改Controller");
        System.out.println(cover);
        //添加
        if(oper.equals("add")){
            banner.setId(UUID.randomUUID().toString()).setCreateDate(new Date());
            logger.info("轮播图添加："+banner);
            logger.info(banner.toString());
            Map<String, String> map = bannerService.addBanner(banner);
            return map;
        }else if(oper.equals("edit")){
            Banner b = bannerService.findOne(new Banner().setId(banner.getId()));
            System.out.println("b:"+b);
            banner.setCover(b.getCover()).setName(b.getName()).setCreateDate(b.getCreateDate());
            logger.info("轮播图更新："+banner);
            Map<String, String> map = bannerService.modify(banner);
            return map;
        }else{
            String realPath = request.getSession().getServletContext().getRealPath("/statics/img");
            Banner f1= bannerService.findOne(new Banner().setId(banner.getId()));
            String filePath=realPath+"/"+f1.getName();
            File f2=new File(filePath);
            f2.delete();

            Map<String, String> map = bannerService.remove(banner);
            return map;
        }
    }

    @RequestMapping("upload")
    public void upload(String id, MultipartFile cover, HttpServletRequest request) throws IOException {
        System.out.println("cover:"+cover);
        if(!cover.getOriginalFilename().equals("")){
            logger.info("进入文件上传----------------------------------------------------------");
            //处理文件上传
            //根据相对upload获取绝对upload路径
            String realPath = request.getSession().getServletContext().getRealPath("/statics/img");
            // 判断路径文件夹是否存在
            File file = new File(realPath);
            if(!file.exists()){
                file.mkdirs();
            }
            //修改文件名  新的文件名生成策略  uuid   时间戳方式
            String newFileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                    +UUID.randomUUID().toString().replace("-","");
            //String fileNameSuffix = aaa.getOriginalFilename().substring(aaa.getOriginalFilename().lastIndexOf("."));
            String fileNameSuffix = "."+ FilenameUtils.getExtension(cover.getOriginalFilename());

            //新的文件名
            String newFileName = newFileNamePrefix+fileNameSuffix;
            //创建日期目录
            String dateDirString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            File dateDir = new File(realPath, dateDirString);
            if(!dateDir.exists()){
                dateDir.mkdirs();
            }
            logger.info(dateDirString);

            // 相对路径 : ../XX/XX/XX.jpg
            // 网络路径 : http://IP:端口/项目名/文件存放位置
//            String http = request.getScheme();
//            String localHost = InetAddress.getLocalHost().toString();
//            int serverPort = request.getServerPort();
//            String contextPath = request.getContextPath();
//            String uri = http+"://"+localHost.split("/")[1]+":"+serverPort+contextPath+"/statics/img/"+dateDirString+"/"+newFileName;
            String uri = HttpFilePath.getURI(request, "/statics/img/" + dateDirString + "/" + newFileName);

            Banner banner1 = bannerService.findOne(new Banner().setId(id));
            banner1.setCover(uri).setName(dateDirString+"/"+newFileName).setCreateDate(new Date());
            logger.info("文件上传："+banner1);
            cover.transferTo(new File(dateDir,newFileName));
            //cover.transferTo(new File(dateDir,banner1.getName()+fileNameSuffix));
            bannerService.modify(banner1);
        }
    }


}
