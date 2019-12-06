package com.wjs.service;

import com.wjs.entity.Chapter;
import com.wjs.entity.Master;
import com.wjs.entity.PageBean;
import com.wjs.entity.User;

import java.util.Map;

public interface MasterService {
    public Map<String,String> addMaster(Master master);
    public Map<String,String> remove(Master master);
    public Map<String,String> modify(Master master);
    public Master findOne(Master master);
    public PageBean<Master> findByPage(Integer page, Integer rows);
}
