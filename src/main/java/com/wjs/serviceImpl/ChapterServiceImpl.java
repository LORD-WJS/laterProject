package com.wjs.serviceImpl;

import com.wjs.dao.ChapterDao;
import com.wjs.entity.Album;
import com.wjs.entity.Chapter;
import com.wjs.entity.PageBean;
import com.wjs.service.ChapterService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;


    @Override
    public Map<String, String> addChapter(Chapter chapter) {
        try{
            chapterDao.insert(chapter);
            Map<String,String> map=new HashMap<>();
            map.put("status","true");
            map.put("message",chapter.getId());
            return map;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("添加章节失败");
        }
    }

    @Override
    public Map<String, String> remove(Chapter chapter) {
        try{
            chapterDao.delete(chapter);
            Map<String,String> map=new HashMap<>();
            map.put("status","true");
            map.put("message",chapter.getId());
            return map;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("删除章节失败");
        }
    }

    @Override
    public Map<String, String> modify(Chapter chapter) {
        try{
            chapterDao.updateByPrimaryKey(chapter);
            Map<String,String> map=new HashMap<>();
            map.put("status","true");
            map.put("message",chapter.getId());
            return map;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("更新章节失败");
        }
    }

    @Override
    public Chapter findOne(Chapter chapter) {
        try{
            Chapter chapter1 = chapterDao.selectOne(chapter);
            return chapter1;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("查询指定专辑的章节失败");
        }
    }

    @Override
    public PageBean<Chapter> findByPage(Integer page, Integer rows,String albumId) {
        try{
            //1调用dao层查询总记录数
            Integer totalCount=chapterDao.selectCount(new Chapter());
            if(totalCount==0){return null;}
            System.out.println("该专辑章节总数:"+totalCount);
            //2计算总页码
            System.out.println(page+"  "+rows);
            Integer totalPage=(totalCount%rows)==0 ? (totalCount/rows) : (totalCount/rows)+1;
            if(page>totalPage) page=totalPage;
            //3调用dao层 查询指定范围的数据List集合
            //计算当前页首条记录索引
            Integer start=(page-1)*rows;
            //Integer end=start+rows-1;
            List<Chapter> list = chapterDao.selectByRowBounds(new Chapter().setAlbumId(albumId),new RowBounds(start,rows));
            for(Chapter p:list) {
                System.out.println(p);
            }
            PageBean<Chapter> pageBean=new PageBean<>(totalCount,totalPage,list,page,rows);
            return pageBean;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("分页查询专辑失败");
        }
    }
}
