package com.wjs.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class HttpFilePath {


    public static String getURI(HttpServletRequest request,String path){
        // 网络路径 : http://IP:端口/项目名/文件存放位置
        String http = request.getScheme();
        String localHost = null;
        try {
            localHost = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String uri = http+"://"+localHost.split("/")[1]+":"+serverPort+contextPath+path;
        return uri;
    }

    //获取文件上传的绝对路径
    public static String getRealPath(HttpServletRequest request,String path){
        //根据相对upload获取绝对upload路径
        String realPath = request.getSession().getServletContext().getRealPath(path);
        // 判断路径文件夹是否存在
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }

        return realPath;
    }

    //修改文件名  新的文件名生成策略  uuid   时间戳方式
    public static String getNewFileName(MultipartFile multipartFile){
        String newFileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                + UUID.randomUUID().toString().replace("-","");
        //String fileNameSuffix = aaa.getOriginalFilename().substring(aaa.getOriginalFilename().lastIndexOf("."));
        String fileNameSuffix = "."+ FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        //新的文件名
        String newFileName = newFileNamePrefix+fileNameSuffix;
        return newFileName;
    }

    //创建日期文件夹名
    public static String getDateDirString(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
    //创建日期目录
    public static File getDateDir(String realPath,String dateDirString){
        File dateDir = new File(realPath, dateDirString);
        if(!dateDir.exists()){
            dateDir.mkdirs();
        }
        return dateDir;
    }

    //完全版文件上传
    public static String transferFile(HttpServletRequest request,String path,MultipartFile file){
        //获取绝对路径
        String realPath= HttpFilePath.getRealPath(request,path);
        String newFileName = HttpFilePath.getNewFileName(file);

        //创建日期目录
        String dateDirString =HttpFilePath.getDateDirString();
        File dateDir =HttpFilePath.getDateDir(realPath,dateDirString);
        //网络路径
        String uri = HttpFilePath.getURI(request, path+"/" + dateDirString + "/" + newFileName);
        try {
            file.transferTo(new File(dateDir,newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uri;
    }







}
