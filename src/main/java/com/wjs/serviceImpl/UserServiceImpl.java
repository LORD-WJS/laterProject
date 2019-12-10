package com.wjs.serviceImpl;

import com.wjs.annotation.AddRedisCache;
import com.wjs.annotation.DelRedisCache;
import com.wjs.annotation.GoEasyAnnotation;
import com.wjs.dao.UserDao;
import com.wjs.entity.PageBean;
import com.wjs.entity.User;
import com.wjs.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @GoEasyAnnotation(value = "已刷新用户注册信息")
    @Override
    public Map<String, String> addUser(User user) {
        try{
            userDao.insert(user);
            Map<String,String> map=new HashMap<>();
            map.put("status","true");
            map.put("message",user.getId());
            return map;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("添加用户失败");
        }
    }
    @GoEasyAnnotation(value = "已刷新用户注册信息")
    @Override
    @DelRedisCache
    public Map<String, String> remove(User user) {
        try{
            userDao.delete(user);
            Map<String,String> map=new HashMap<>();
            map.put("status","true");
            map.put("message",user.getId());
            return map;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("删除用户失败");
        }
    }


    @Override
    @DelRedisCache
    public Map<String, String> modify(User user) {
        try{
            userDao.updateByPrimaryKeySelective(user);
            Map<String,String> map=new HashMap<>();
            map.put("status","true");
            map.put("message",user.getId());
            return map;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("更新用户失败");
        }
    }

    @Override
    public User findOne(User user) {
        try{
            User user1 = userDao.selectOne(user);
            return user1;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("查询指定用户失败");
        }
    }
    @Override
    @AddRedisCache
    public PageBean<User> findByPage(Integer page, Integer rows) {
        try{
            //1调用dao层查询总记录数
            Integer totalCount=userDao.selectCount(new User());
            if(totalCount==0){return null;}
            System.out.println("用户总数:"+totalCount);
            //2计算总页码
            System.out.println(page+"  "+rows);
            Integer totalPage=(totalCount%rows)==0 ? (totalCount/rows) : (totalCount/rows)+1;
            if(page>totalPage) page=totalPage;
            //3调用dao层 查询指定范围的数据List集合
            //计算当前页首条记录索引
            Integer start=(page-1)*rows;
            //Integer end=start+rows-1;
            List<User> list = userDao.selectByRowBounds(new User(),new RowBounds(start,rows));
            for(User p:list) {
                System.out.println(p);
            }
            PageBean<User> pageBean=new PageBean<>(totalCount,totalPage,list,page,rows);
            return pageBean;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("分页查询用户失败");
        }
    }

    @Override
    public User regist(User user) {
        try{
            userDao.insert(user);
            return user;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("注册用户失败");
        }
    }
}
