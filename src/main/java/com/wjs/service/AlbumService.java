package com.wjs.service;

import com.wjs.entity.Album;
import com.wjs.entity.PageBean;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    public Map<String,String> addAlbum(Album album);
    public Map<String,String> remove(Album album);
    public Map<String,String> modify(Album album);
    public Album findOne(Album album);
    public PageBean<Album> findByPage(Integer page, Integer rows);
    public List<Album> findNew6();

}
