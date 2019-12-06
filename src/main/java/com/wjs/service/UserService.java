package com.wjs.service;

import com.wjs.entity.Chapter;
import com.wjs.entity.PageBean;
import com.wjs.entity.User;

import java.util.Map;

public interface UserService {
    public Map<String,String> addUser(User user);
    public Map<String,String> remove(User user);
    public Map<String,String> modify(User user);
    public User findOne(User user);
    public PageBean<User> findByPage(Integer page, Integer rows);

    public User regist(User user);
}
