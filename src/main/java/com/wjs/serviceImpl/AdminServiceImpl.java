package com.wjs.serviceImpl;

import com.wjs.dao.AdminDao;
import com.wjs.entity.Admin;
import com.wjs.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    public Admin findByUsername(String username) {
        return adminDao.queryByUsername(username);
    }
}
