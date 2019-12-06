package com.wjs.dao;

import com.wjs.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ArticleDao extends Mapper<Article> {
    public List<Article> queryByPage(@Param("start") Integer start, @Param("rows")Integer rows);

    @Select("select a.*,m.id `master.id`,m.nickName `master.nickName`,m.face `master.face` " +
            "from article a left join master m on a.masterId=m.id order by publishDate desc limit 0,3")
    public List<Article> queryNew3();

    @Select("select a.*,m.id `master.id`,m.nickName `master.nickName`,m.face `master.face` " +
            "from article a left join master m on a.masterId=m.id where a.id=#{id}")
    public Article queryById(String id);

    @Select("select a.*,m.id `master.id`,m.nickName `master.nickName`,m.face `master.face` " +
            "from article a left join master m on a.masterId=m.id ")
    public List<Article> queryAll();
}
