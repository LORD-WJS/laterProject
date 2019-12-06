package com.wjs.dao;

import com.wjs.entity.Admin;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface AdminDao extends Mapper<Admin> {
    @Select("select * from admin where username=#{username}")
    public Admin queryByUsername(String username);

}
