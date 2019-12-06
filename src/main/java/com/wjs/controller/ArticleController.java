package com.wjs.controller;


import com.wjs.dao.ArticleDao;
import com.wjs.entity.Album;
import com.wjs.entity.Article;
import com.wjs.entity.PageBean;
import com.wjs.service.ArticleService;
import com.wjs.util.HttpFilePath;
import com.wjs.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleDao articleDao;

    Logger logger = LoggerFactory.getLogger(getClass());


    @RequestMapping("showByPage")
    public Map<String,Object> showBanners(Integer rows, Integer page){
        PageBean<Article> pb = articleService.findByPage(page, rows);

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

    @RequestMapping("searchArticleById")
    public Article searchArticleById(Article article){
        Article article1 = articleDao.selectOne(new Article().setId(article.getId()));
        return article1;
    }
    /*
        2. 完成上传文件操作
            注意 : 上传文件默认为 imgFile 可以通过 KindEditor中的filePostName 进行修改
        3. 返回值类型指定
        //成功时
{
        "error" : 0,
        "url" : "http://www.example.com/path/to/file.ext"
}
//失败时
{
        "error" : 1,
        "message" : "错误信息"
}
     */
    @RequestMapping("uploadImg")
    public Map uploadImg(MultipartFile imgFile, HttpSession session, HttpServletRequest request){
        HashMap hashMap = new HashMap();
        String dir = "/upload/articleImg/";
        try {
            String httpUrl = HttpUtil.getHttpUrl(imgFile, request, session, dir);
            hashMap.put("error",0);
            hashMap.put("url",httpUrl);
        } catch (Exception e) {
            hashMap.put("error",1);
            hashMap.put("message","上传错误");
            e.printStackTrace();
        }
        return hashMap;
    }
    @RequestMapping("showAllImgs")
    public Map showAllImgs(HttpSession session,HttpServletRequest request){
        // 1. 获取文件夹绝对路径
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
        // 2. 准备返回的Json数据
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        // 3. 获取目标文件夹
        File file = new File(realPath);
        File[] files = file.listFiles();
        // 4. 遍历文件夹中的文件
        for (File file1 : files) {
            // 5. 文件属性封装
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir",false);
            fileMap.put("has_file",false);
            fileMap.put("filesize",file1.length());
            fileMap.put("is_photo",true);
            // 获取文件后缀 | 文件类型
            String extension = FilenameUtils.getExtension(file1.getName());
            fileMap.put("filetype",extension);
            fileMap.put("filename",file1.getName());
            // 获取文件上传时间 1. 截取时间戳 2. 创建格式转化对象 3. 格式类型转换
            String s = file1.getName().split("_")[0];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(s)));
            fileMap.put("datetime",format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list",arrayList);
        hashMap.put("total_count",arrayList.size());
        // 返回路径为 项目名 + 文件夹路径
        hashMap.put("current_url",request.getContextPath()+"/upload/articleImg/");
        return hashMap;
    }

    @RequestMapping("insertArticle")
    public void insertArticle(MultipartFile cover, Article article,HttpServletRequest request){
        //封面上传
        String path="/upload/articleCover/";
        String uri = HttpFilePath.transferFile(request, path, cover);
        article.setPicpath(uri).setId(UUID.randomUUID().toString()).setCreateDate(new Date())
                .setPublishDate(new Date());
        logger.info("新文章添加："+article);

        articleService.addArticle(article);
    }
    @RequestMapping("dropArticle")
    public void dropArticle(Article article){
        articleService.remove(article);
    }

    @RequestMapping("changeArticle")
    public void changeArticle(MultipartFile cover, Article article,HttpServletRequest request){
        String uri=null;
        if(!cover.getOriginalFilename().equals("")){
            //封面上传
            String path="/upload/articleCover/";
            uri = HttpFilePath.transferFile(request, path, cover);
        }
        article.setPicpath(uri);
        articleService.modify(article);

    }


}
