package com.wjs.service;

import com.wjs.entity.Banner;
import com.wjs.entity.PageBean;

import java.util.List;
import java.util.Map;

public interface BannerService {
    public Map<String,String> addBanner(Banner banner);
    public Map<String,String> remove(Banner banner);
    public Map<String,String> modify(Banner banner);
    public Map<String,String> modifyBanner(Banner banner);
    public Banner findOne(Banner banner);
    public PageBean<Banner> findByPage(Integer page, Integer rows);
    public List<Banner> findNew5();

}
