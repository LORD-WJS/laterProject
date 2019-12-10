package com.wjs.dao;

import com.wjs.entity.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleDao {

    @Select("select r.* from admin a left join userRole uR on a.id=uR.userId left join role r " +
            " on r.id=uR.roleId where a.username=#{username}")
    public List<Role> queryByUsername(String username);

}
