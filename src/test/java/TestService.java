import com.wjs.dao.*;
import com.wjs.entity.*;
import com.wjs.laterProjectApplication;
import com.wjs.service.AdminService;
import com.wjs.service.AlbumService;
import com.wjs.service.ArticleService;
import com.wjs.service.BannerService;
import com.wjs.util.DateTools;
import com.wjs.util.MD5Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = laterProjectApplication.class)
@RunWith(SpringRunner.class)
public class TestService {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ResourceDao resourceDao;


    @Test
    public void queryResourceByUsername() {
        List<Resource> admin = resourceDao.queryByUsername("admin");
        System.out.println(admin);
    }

    @Test
    public void queryRoleByUsername() {
        List<Role> admin = roleDao.queryByUsername("admin");
        System.out.println(admin);
    }

    @Test
    public void queryNewArticle(){
        List<Article> new3 = articleService.findNew3();
        System.out.println(new3);
    }

    @Test
    public void queryNewAlbum(){
        List<Album> new6 = albumService.findNew6();
        System.out.println(new6);
    }

    @Test
    public void queryNewBanner(){
        List<Banner> banners = bannerService.findNew5();
        System.out.println(banners);
    }


    @Test
    public void queryMan(){
        Integer users = userDao.queryManByTime("男","365");
        System.out.println(users);
//        for (User user : users) {
//            System.out.println(user);
//        }
    }


    @Test
    public void addUser(){
        String img="http://192.168.106.1:8989/laterProject/statics/user/face/2019-12-01/201912011531361106ae0d831c2cc495ba0eda9c6835b2d51.jpg";
        Date date= DateTools.strToDate("2019-11-15","yyyy-MM-dd");
        for (int i=0;i<3;i++){
            User user = new User(UUID.randomUUID().toString(), "翻车" + i, "袁青" + i, "ce8c16d3f87ac63a5b6eebd54aa003c6", "FfQ9uFkK", "21465464",
                    img, "男", "山东", "铁头娃", date, date, "正常");
            userDao.insert(user);
        }
    }

    @Test public void sdkfjsd(){
        String code = MD5Utils.getCode();
        System.out.println(code);
    }






    @Test
    public void adminlogin(){
        //Admin admin = adminService.findByUsername("admin");
        Admin admin = adminDao.selectOne(new Admin().setUsername("admin"));
        System.out.println(admin);
    }


    @Test
    public void login(){
        List<Admin> admins = adminDao.selectAll();
        admins.forEach(admin -> System.out.println(admin));
    }

    @Test
    public void showBanners(){
        PageBean<Banner> banners = bannerService.findByPage(0, 2);
        banners.getList().forEach(banner -> System.out.println(banner));
    }

    @Test
    public void findArticleByPage(){
        PageBean<Article> pb = articleService.findByPage(1, 2);
    }


}
