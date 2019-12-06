package com.wjs.controller;

import com.microsoft.schemas.office.visio.x2012.main.MastersDocument;
import com.wjs.dao.*;
import com.wjs.entity.*;
import com.wjs.service.*;
import com.wjs.util.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("main")
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private MasterService masterService;
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CounterService counterService;
    @Autowired
    private CounterDao counterDao;
    @Autowired
    private MasterDao masterDao;
    @Autowired
    private AttentionService attentionService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    Logger logger = LoggerFactory.getLogger(getClass());


    //5一级页面展示
    @RequestMapping("showMain")
    public Map<String,Object> showMain(String uid,String type,String sub_type){
        Map<String,Object> map=new HashMap<>();

        if(uid!=null){
            map.put("status","200");
            if("all".equals(type)){//查询轮播图 专辑  文章
                List<Banner> bannerList = bannerService.findNew5();
                List<Album> albumList = albumService.findNew6();
                List<Article> articleList = articleService.findNew3();
                map.put("bannerList",bannerList);
                map.put("albumList",albumList);
                map.put("articleList",articleList);
            }else if("wen".equals(type)){//吉祥妙音 专辑列表
                List<Album> albums = albumDao.selectAll();
                map.put("albumList",albums);
            }else if("si".equals(type)){//甘露妙宝  文章
                if("ssyj".equals(sub_type)){//上师言教  关注上师发的文章
                    List<Article> list=new ArrayList<>();
                    List<Master> masters = masterDao.queryByUserAttention(uid);
                    for (Master master : masters) {
                        List<Article> articles = articleDao.select(new Article().setMasterId(master.getId()));
                        list.addAll(articles);
                    }
                    map.put("articleList",list);
                }else if("xmfy".equals(sub_type)){
                    List<Article> articles = articleDao.queryAll();
                    map.put("articleList",articles);

                }
            }
        }//uid判空

        return map;
    }

    //6文章详情
    @RequestMapping("articleDetail")
    public Map<String,Object> articleDetail(String uid,String id){
        //参数 用户id  文章id
        Map<String,Object> map=new HashMap<>();

        try{
            Article article = articleDao.queryById(id);
            map.put("status","200");
            map.put("article",article);
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","网络故障");
        }

        return map;
    }
    //7专辑详情
    @RequestMapping("showAlbumDetail")
    public Map<String,Object> showAlbumDetail(String uid,String id){
        //UID 用户ID  id专辑
        Map<String,Object> map=new HashMap<>();

        try{
            Album album = albumService.findOne(new Album().setId(id));
            List<Chapter> chapters = chapterDao.queryByAlbumId(id);
            map.put("status","200");
            map.put("album",album);
            map.put("chapterList",chapters);
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","网络故障");
        }

        return map;
    }

    @RequestMapping("showCourse")
    public Map<String,Object> showCourse(String uid){
        Map<String,Object> map=new HashMap<>();

        try{
            List<Course> courses = courseDao.select(new Course().setUserId(uid));
            map.put("status","200");
            map.put("courseList",courses);
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","网络故障");
        }
        return map;
    }

    @RequestMapping("addCourse")
    public Map<String,Object> addCourse(String uid,String name){
        Map<String,Object> map=new HashMap<>();

        try{
            Course course = new Course().setId(UUID.randomUUID().toString()).setName(name).setType("private").setUserId(uid)
                    .setCreateDate(new Date());
            courseService.addCourse(course);
            List<Course> courses = courseDao.selectAll();
            map.put("status","200");
            map.put("courseList",courses);
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","网络故障");

        }
        return map;
    }
    //10删除功课
    @RequestMapping("dropCourse")
    public Map<String,Object> dropCourse(String uid,String id){
        Map<String,Object> map=new HashMap<>();

        try{
            courseService.remove(new Course().setId(id));
            List<Course> courses = courseDao.selectAll();
            map.put("status","200");
            map.put("courseList",courses);
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","网络故障");
        }
        return map;
    }

    //11展示计数器
    @RequestMapping("showCounter")
    public Map<String,Object> showCounter(String uid,String id){
        //id功课
        Map<String,Object> map=new HashMap<>();
        try{
            List<Counter> counters = counterDao.select(new Counter().setCourseId(id));
            map.put("status","200");
            map.put("counterList",counters);
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","网络故障");
        }
        return map;
    }


    //12添加计数器
    @RequestMapping("addCounter")
    public Map<String,Object> addCounter(String uid,String name,String courseId){
        Map<String,Object> map=new HashMap<>();

        try{
            Counter counter = new Counter().setId(UUID.randomUUID().toString()).setCourseId(courseId).setCount(0)
                    .setCreateDate(new Date()).setName(name).setUserId(uid);
            counterService.addCounter(counter);
            List<Counter> counters = counterDao.select(new Counter().setCourseId(courseId));
            map.put("status","200");
            map.put("counterList",counters);
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","网络故障");
        }
        return map;
    }

    //13删除计数器
    @RequestMapping("dropCounter")
    public Map<String,Object> dropCounter(String uid,String id,String courseId){
        Map<String,Object> map=new HashMap<>();

        try{
            counterService.remove(new Counter().setId(id));
            List<Counter> counters = counterDao.select(new Counter().setCourseId(courseId));
            map.put("status","200");
            map.put("counterList",counters);
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","网络故障");
        }
        return map;
    }

    //14更新计数器
    @RequestMapping("changeCounter")
    public Map<String,Object> changeCounter(String uid,String id,Integer count,String courseId){
        Map<String,Object> map=new HashMap<>();

        try{
            counterService.modify(new Counter().setId(id).setCount(count));
            List<Counter> counters = counterDao.select(new Counter().setCourseId(courseId));
            map.put("status","200");
            map.put("counterList",counters);
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","网络故障");
        }
        return map;
    }

    //15修改个人信息
    @RequestMapping("changePerson")
    public Map<String,Object> changePerson(User user){
        Map<String,Object> map=new HashMap<>();

        String salt = MD5Utils.getSalt();
        String password = MD5Utils.getPassword(salt + user.getPassword());
        user.setPassword(password).setSalt(salt);
        try{
            userService.modify(user);
            User u = userService.findOne(new User().setId(user.getId()));
            map.put("user",u);
            map.put("status","200");
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","网络故障");
        }

        return map;
    }


    //16 查询金刚道友  （查询与自己关注了同样数量身份的上师  的用户）
    @RequestMapping("showFriends")
    public Map<String,Object> showFriends(String uid){
        Map<String,Object> map=new HashMap<>();

        try{
            //获取该用户关注的上师id集合
            List<Master> masters = masterDao.queryByUserAttention(uid);
            ArrayList<String> masterIds = new ArrayList<>();
            for (Master master : masters) {
                masterIds.add(master.getId());
            }
            //得到操作set集合的对象
            SetOperations<String, String> set = stringRedisTemplate.opsForSet();
            String key1=masterIds.get(0);
            masterIds.remove(0);

            //根据上师的id得到用户id交集
            Set<String> uids = set.intersect(key1, masterIds);
            uids.remove(uid);
            if (uids.size()>0){
                //根据用户id查询得到用户
                List userList = new ArrayList();
                for (String s : uids) {
                    User user = userService.findOne(new User().setId(s));
                    userList.add(user);
                }
                map.put("status",200);
                map.put("message","查询正常");
                map.put("userList", userList);
                return map;
            }else {
                map.put("status",200);
                map.put("message","尚未关注上师");
                return null;
            }
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","网络故障");
        }
        return map;
    }

    //17展示上师列表
    @RequestMapping("showMasters")
    public Map<String,Object> showMasters(String uid){
        Map<String,Object> map=new HashMap<>();

        try{
            List<Master> masters = masterDao.selectAll();
            map.put("status","200");
            map.put("masterList",masters);
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","网络故障");
        }
        return map;
    }
    //18添加关注上师
    @RequestMapping("followMaster")
    public Map<String,Object> followMaster(String uid,String masterId){
        Map<String,Object> map=new HashMap<>();

        try{
            //得到操作set集合的对象
            SetOperations<String, String> set = stringRedisTemplate.opsForSet();
            set.add(masterId,uid);

            Attention attention=new Attention().setId(UUID.randomUUID().toString()).setMasterId(masterId)
                    .setUserId(uid);
            attentionService.addAttention(attention);
            List<Master> masters = masterDao.selectAll();
            List<Master> masters1 = masterDao.queryByUserAttention(uid);//该用户关注的上师集合
            map.put("status","200");
            map.put("masterList",masters);
            //需返回上师集合
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","网络故障");
        }
        return map;
    }


}
