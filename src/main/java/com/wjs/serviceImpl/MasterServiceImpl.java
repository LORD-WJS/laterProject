package com.wjs.serviceImpl;

import com.wjs.dao.MasterDao;
import com.wjs.entity.Album;
import com.wjs.entity.Master;
import com.wjs.entity.PageBean;
import com.wjs.service.MasterService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MasterServiceImpl implements MasterService {
    @Autowired
    private MasterDao masterDao;


    @Override
    public Map<String, String> addMaster(Master master) {
        try{
            masterDao.insert(master);
            Map<String,String> map=new HashMap<>();
            map.put("status","true");
            map.put("message",master.getId());
            return map;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("添加上师失败");
        }
    }

    @Override
    public Map<String, String> remove(Master master) {
        try{
            masterDao.delete(master);
            Map<String,String> map=new HashMap<>();
            map.put("status","true");
            map.put("message",master.getId());
            return map;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("删除上师失败");
        }
    }

    @Override
    public Map<String, String> modify(Master master) {
        try{
            masterDao.updateByPrimaryKeySelective(master);
            Map<String,String> map=new HashMap<>();
            map.put("status","true");
            map.put("message",master.getId());
            return map;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("更新上师失败");
        }
    }

    @Override
    public Master findOne(Master master) {
        try{
            Master album1 = masterDao.selectOne(master);
            return album1;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("查询指定上师失败");
        }
    }

    @Override
    public PageBean<Master> findByPage(Integer page, Integer rows) {
        try{
            //1调用dao层查询总记录数
            Integer totalCount=masterDao.selectCount(new Master());
            if(totalCount==0){return null;}
            System.out.println("上师总数:"+totalCount);
            //2计算总页码
            System.out.println(page+"  "+rows);
            Integer totalPage=(totalCount%rows)==0 ? (totalCount/rows) : (totalCount/rows)+1;
            if(page>totalPage) page=totalPage;
            //3调用dao层 查询指定范围的数据List集合
            //计算当前页首条记录索引
            Integer start=(page-1)*rows;
            //Integer end=start+rows-1;
            List<Master> list = masterDao.selectByRowBounds(new Master(),new RowBounds(start,rows));
            for(Master p:list) {
                System.out.println(p);
            }
            PageBean<Master> pageBean=new PageBean<>(totalCount,totalPage,list,page,rows);
            return pageBean;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("分页查询上师失败");

        }
    }
}
