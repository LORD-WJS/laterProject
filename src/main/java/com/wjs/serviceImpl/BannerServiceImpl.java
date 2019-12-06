package com.wjs.serviceImpl;

import com.wjs.dao.BannerDao;
import com.wjs.entity.Banner;
import com.wjs.entity.PageBean;
import com.wjs.service.BannerService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;


    @Override
    public Map<String,String> addBanner(Banner banner) {
        try{
            bannerDao.insert(banner);
            Map<String,String> map=new HashMap<>();
            map.put("status","true");
            map.put("message",banner.getId());
            return map;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("添加轮播图失败");
        }
    }

    @Override
    public Map<String,String> remove(Banner banner) {
        try{
            bannerDao.delete(banner);
            Map<String,String> map=new HashMap<>();
            map.put("status","true");
            map.put("message",banner.getId());
            return map;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("删除轮播图失败");
        }
    }

    @Override
    public Map<String,String> modify(Banner banner) {
        try{
            bannerDao.updateByPrimaryKey(banner);
            Map<String,String> map=new HashMap<>();
            map.put("status","true");
            map.put("message",banner.getId());
            return map;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("更新轮播图失败");
        }
    }

    @Override
    public Map<String,String> modifyBanner(Banner banner) {
        bannerDao.updateBanner(banner);
        Map<String,String> map=new HashMap<>();
        map.put("status","true");
        map.put("message",banner.getId());
        return map;
    }

    @Override
    public Banner findOne(Banner banner) {
        try{
            Banner banner1 = bannerDao.selectOne(banner);
            return banner1;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("查询指定轮播图失败");
        }
    }

    @Override
    public PageBean<Banner> findByPage(Integer page, Integer rows) {
        try{
            //1调用dao层查询总记录数
            Integer totalCount=bannerDao.selectCount(new Banner());
            if(totalCount==0){return null;}
            System.out.println("轮播图总数:"+totalCount);
            //2计算总页码
            System.out.println(page+"  "+rows);
            Integer totalPage=(totalCount%rows)==0 ? (totalCount/rows) : (totalCount/rows)+1;
            if(page>totalPage) page=totalPage;
            //3调用dao层 查询指定范围的数据List集合
            //计算当前页首条记录索引
            Integer start=(page-1)*rows;
            //Integer end=start+rows-1;
            List<Banner> list = bannerDao.selectByRowBounds(new Banner(),new RowBounds(start,rows));
            for(Banner p:list) {
                System.out.println(p);
            }
            PageBean<Banner> pageBean=new PageBean<>(totalCount,totalPage,list,page,rows);
            return pageBean;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("查询所有轮播图失败");
        }
    }

    //查询最新的五张轮播图
    public List<Banner> findNew5(){
        try{
            Example example = new Example(Banner.class);
            example.setOrderByClause("createDate desc");
            List<Banner> banners = bannerDao.selectByExampleAndRowBounds(example, new RowBounds(0, 5));
            return banners;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("查询最新5张轮播图失败");
        }
    }


}
