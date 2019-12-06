package com.wjs.service;

import com.wjs.entity.Article;
import com.wjs.entity.Master;
import com.wjs.entity.PageBean;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    public Map<String,String> addArticle(Article article);
    public Map<String,String> remove(Article article);
    public Map<String,String> modify(Article article);
    public Article findOne(Article article);
    public PageBean<Article> findByPage(Integer page, Integer rows);
    public List<Article> findNew3();
}
