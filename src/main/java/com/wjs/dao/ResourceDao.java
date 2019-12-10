package com.wjs.dao;

import com.wjs.entity.Resource;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ResourceDao extends Mapper<Resource> {

    @Select("select re.* from admin a left join userRole uR on a.id=uR.userId left join role r on uR.roleId=r.id" +
            " left join roleResource rR on r.id=rR.roleId left join resource re on rR.resourceId=re.id " +
            " where a.username=#{username} ")
    public List<Resource> queryByUsername(String username);

}
