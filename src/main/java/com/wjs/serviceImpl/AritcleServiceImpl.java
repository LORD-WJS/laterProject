package com.wjs.serviceImpl;

import com.wjs.dao.ArticleDao;
import com.wjs.entity.Album;
import com.wjs.entity.Article;
import com.wjs.entity.Master;
import com.wjs.entity.PageBean;
import com.wjs.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AritcleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;


    @Override
    public Map<String, String> addArticle(Article article) {
        try{
            articleDao.insert(article);
            Map<String,String> map=new HashMap<>();
            map.put("status","true");
            map.put("message",article.getId());
            return map;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("添加文章失败");
        }
    }

    @Override
    public Map<String, String> remove(Article article) {
        try{
            articleDao.delete(article);
            Map<String,String> map=new HashMap<>();
            map.put("status","true");
            map.put("message",article.getId());
            return map;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("删除文章失败");
        }
    }

    @Override
    public Map<String, String> modify(Article article) {
        try{
            articleDao.updateByPrimaryKeySelective(article);
            Map<String,String> map=new HashMap<>();
            map.put("status","true");
            map.put("message",article.getId());
            return map;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("更新文章失败");
        }
    }

    @Override
    public Article findOne(Article article) {
        try{
            Article article1 = articleDao.selectOne(article);
            return article1;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("查询指定文章失败");
        }
    }

    @Override
    public PageBean<Article> findByPage(Integer page, Integer rows) {
        try{
            //1调用dao层查询总记录数
            Integer totalCount=articleDao.selectCount(new Article());
            if(totalCount==0){return null;}
            System.out.println("文章总数:"+totalCount);
            //2计算总页码
            System.out.println(page+"  "+rows);
            Integer totalPage=(totalCount%rows)==0 ? (totalCount/rows) : (totalCount/rows)+1;
            if(page>totalPage) page=totalPage;
            //3调用dao层 查询指定范围的数据List集合
            //计算当前页首条记录索引
            Integer start=(page-1)*rows;
            //Integer end=start+rows-1;
            List<Article> list = articleDao.queryByPage(start,rows);
            for(Article p:list) {
                System.out.println(p);
            }
            PageBean<Article> pageBean=new PageBean<>(totalCount,totalPage,list,page,rows);
            return pageBean;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("分页查询文章失败");
        }
    }

    public List<Article> findNew3(){
        try{
            List<Article> articles = articleDao.queryNew3();
            return articles;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("查询最新3篇文章失败");
        }
    }

}
