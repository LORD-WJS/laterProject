package com.wjs.controller;

import com.wjs.dao.UserDao;
import com.wjs.entity.Album;
import com.wjs.entity.PageBean;
import com.wjs.entity.User;
import com.wjs.service.UserService;
import com.wjs.util.HttpFilePath;
import com.wjs.util.MD5Utils;
import com.wjs.util.MessageUtil;
import com.wjs.vo.MapVO;
import io.goeasy.GoEasy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    Logger logger = LoggerFactory.getLogger(getClass());


    @RequestMapping("showByPage")
    public Map<String,Object> showBanners(Integer rows, Integer page){
        PageBean<User> pb = userService.findByPage(page, rows);

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
    public Map<String,String> edit(String oper, User user, HttpServletRequest request){
        logger.info("进入user增删改Controller");
        //添加
        if(oper.equals("add")){
            user.setId(UUID.randomUUID().toString()).setCreateDate(new Date());
            logger.info("用户密码加密前："+user);
            //密码MD5加密
            String salt = MD5Utils.getSalt();
            String password = MD5Utils.getPassword(salt + user.getPassword());
            user.setPassword(password).setSalt(salt);
            logger.info("用户密码加密后："+user);
            Map<String, String> map = userService.addUser(user);

            return map;
        }else if(oper.equals("edit")){
            logger.info("用户更新："+user);
            Map<String, String> map = userService.modify(user);
            return map;
        }else{
            String realPath = request.getSession().getServletContext().getRealPath("/statics/user/face");
            User f1= userService.findOne(new User().setId(user.getId()));
            String filePath=realPath+"/"+f1.getName();
            File f2=new File(filePath);
            f2.delete();

            Map<String, String> map = userService.remove(user);
            return map;
        }
    }

    @RequestMapping("upload")
    public void upload(String id, MultipartFile face, HttpServletRequest request) throws IOException {
        System.out.println("face用户头像:"+face);
        if(!face.getOriginalFilename().equals("")){
            logger.info("进入用户头像上传----------------------------------------------------------");
            String path="/statics/user/face";
            //获取绝对路径
            String realPath= HttpFilePath.getRealPath(request,path);
            String newFileName = HttpFilePath.getNewFileName(face);

            //创建日期目录
            String dateDirString =HttpFilePath.getDateDirString();
            File dateDir =HttpFilePath.getDateDir(realPath,dateDirString);
            logger.info(dateDirString);
            //网络路径
            String uri = HttpFilePath.getURI(request, path+"/" + dateDirString + "/" + newFileName);

            User user1 = userService.findOne(new User().setId(id));
            user1.setFace(uri);
            logger.info("文件上传："+user1);
            face.transferTo(new File(dateDir,newFileName));
            userService.modify(user1);


        }
    }
    //查询所有用户 分男女，为用户注册时间分布图提供数据
    @RequestMapping("searchAllUser")
    public Map<String, Integer> searchAllUser(){
        Map<String, Integer> map = new HashMap<>();

        Integer mans365 = userDao.queryManByTime("男","365");
        Integer mans183 = userDao.queryManByTime("男","183");
        Integer mans30 = userDao.queryManByTime("男","30");
        Integer mans7 = userDao.queryManByTime("男","7");

        Integer womans365 = userDao.queryManByTime("女","365");
        Integer womans183 = userDao.queryManByTime("女","183");
        Integer womans30 = userDao.queryManByTime("女","30");
        Integer womans7 = userDao.queryManByTime("女","7");

        map.put("mans365",mans365);
        map.put("mans183",mans183);
        map.put("mans30",mans30);
        map.put("mans7",mans7);
        map.put("womans365",womans365);
        map.put("womans183",womans183);
        map.put("womans30",womans30);
        map.put("womans7",womans7);

        return map;
    }

    @RequestMapping("searchLocationRange")
    public List<MapVO> searchLocationRange(){
        List<MapVO> mapVOS = userDao.queryLocationRange();
        return mapVOS;
    }

    //用户登录
    @RequestMapping("login")
    public Map<String,Object> login(User user){
        Map<String,Object> map=new HashMap<>();

        User one = userService.findOne(new User().setPhone(user.getPhone()));
        String salt = one.getSalt();
        String password = MD5Utils.getPassword(salt + user.getPassword());
        if(password.equals(one.getPassword())){//登陆成功
            map.put("status","200");
            map.put("user",one);
        }else{
            map.put("status","-200");
            map.put("message","查无此人");
        }

        return map;
    }

    //注册时发送验证码
    @RequestMapping("sendCode")
    public Map<String,Object> sendCode(String phone){
        Map<String,Object> map=new HashMap<>();

        User one = userService.findOne(new User().setPhone(phone));
        if(one!=null){//手机号已存在
            map.put("status","-200");
            map.put("message","该手机号已存在");
        }else{//可以注册
            //生成验证码 6位
            String code = MD5Utils.getCode();
            try{
                //给用户手机发送验证码
                MessageUtil.sendMessage(phone,code);
                //验证码存入redis，五分钟有效  key:phone  value:验证码
                ValueOperations<String, String> s = stringRedisTemplate.opsForValue();
                s.set(phone,code);
                stringRedisTemplate.expire(phone,5*60, TimeUnit.SECONDS);

            }catch (Exception e){
                map.put("status","-200");
                map.put("message","网络故障");
            }

            map.put("status","200");
            map.put("phone",phone);
        }
        return map;
    }

    //验证码比对，注册
    @RequestMapping("regist")
    public Map<String,Object> regist(String phone,String code){
        Map<String,Object> map=new HashMap<>();

        //从redis中获取验证码进行比对   key:phone  value:验证码
        ValueOperations<String, String> s = stringRedisTemplate.opsForValue();
        String code1 = s.get(phone);

        //如果两个验证码相同 数据库添加用户
       if(code.equals(code1)){
           User u = userService.regist(new User().setPhone(phone).setId(UUID.randomUUID().toString()));
           map.put("status","200");
           map.put("user",u);
       }else{
           map.put("status","-200");
           map.put("message","验证码输入错误");
       }

        return map;
    }

    //补充个人信息
    @RequestMapping("addPersonInfo")
    public Map<String,Object> addPersonInfo(User user){
        Map<String,Object> map=new HashMap<>();

        user.setCreateDate(new Date()).setLastLoginTime(new Date());
        String salt = MD5Utils.getSalt();
        String password = MD5Utils.getPassword(salt + user.getPassword());
        user.setPassword(password).setSalt(salt);
        logger.info(user.toString());
        try{
            userService.modify(user);
            map.put("status","200");
            map.put("user",user);
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","网络故障");
        }

        return map;
    }





}
