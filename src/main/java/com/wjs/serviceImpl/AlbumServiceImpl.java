package com.wjs.serviceImpl;

import com.wjs.annotation.LogAnnotation;
import com.wjs.dao.AlbumDao;
import com.wjs.entity.Album;
import com.wjs.entity.PageBean;
import com.wjs.service.AlbumService;
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
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;


    @Override
    public Map<String, String> addAlbum(Album album) {
        try{
            albumDao.insert(album);
            Map<String,String> map=new HashMap<>();
            map.put("status","true");
            map.put("message",album.getId());
            return map;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("添加专辑失败");
        }
    }

    @Override
    public Map<String, String> remove(Album album) {
        try{
            albumDao.delete(album);
            Map<String,String> map=new HashMap<>();
            map.put("status","true");
            map.put("message",album.getId());
            return map;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("删除专辑失败");
        }
    }

    @Override
    public Map<String, String> modify(Album album) {
        try{
            albumDao.updateByPrimaryKey(album);
            Map<String,String> map=new HashMap<>();
            map.put("status","true");
            map.put("message",album.getId());
            return map;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("更新专辑失败");
        }
    }

    @Override
    public Album findOne(Album album) {
        try{
            Album album1 = albumDao.selectOne(album);
            return album1;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("查询指定专辑失败");
        }
    }

    @LogAnnotation(value = "分页查询专辑")
    @Override
    public PageBean<Album> findByPage(Integer page, Integer rows) {
        try{
            //1调用dao层查询总记录数
            Integer totalCount=albumDao.selectCount(new Album());
            if(totalCount==0){return null;}
            System.out.println("专辑总数:"+totalCount);
            //2计算总页码
            System.out.println(page+"  "+rows);
            Integer totalPage=(totalCount%rows)==0 ? (totalCount/rows) : (totalCount/rows)+1;
            if(page>totalPage) page=totalPage;
            //3调用dao层 查询指定范围的数据List集合
            //计算当前页首条记录索引
            Integer start=(page-1)*rows;
            //Integer end=start+rows-1;
            List<Album> list = albumDao.selectByRowBounds(new Album(),new RowBounds(start,rows));
            for(Album p:list) {
                System.out.println(p);
            }
            PageBean<Album> pageBean=new PageBean<>(totalCount,totalPage,list,page,rows);
            return pageBean;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("分页查询专辑失败");
        }
    }

    @Override
    public List<Album> findNew6() {
        try{
            Example example = new Example(Album.class);
            example.setOrderByClause("createDate desc");
            List<Album> albums = albumDao.selectByExampleAndRowBounds(example, new RowBounds(0, 6));
            return albums;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("查询6个最新专辑失败");
        }
    }
}
