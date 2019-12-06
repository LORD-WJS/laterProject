package com.wjs.service;

import com.wjs.entity.Admin;

public interface AdminService {
    public Admin findByUsername(String username);
}
