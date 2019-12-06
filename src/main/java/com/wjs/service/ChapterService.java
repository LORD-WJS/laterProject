package com.wjs.service;

import com.wjs.entity.Album;
import com.wjs.entity.Chapter;
import com.wjs.entity.PageBean;

import java.util.Map;

public interface ChapterService {
    public Map<String,String> addChapter(Chapter chapter);
    public Map<String,String> remove(Chapter chapter);
    public Map<String,String> modify(Chapter chapter);
    public Chapter findOne(Chapter chapter);
    public PageBean<Chapter> findByPage(Integer page, Integer rows,String albumId);

}
