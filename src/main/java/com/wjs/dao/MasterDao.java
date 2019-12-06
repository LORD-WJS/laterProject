package com.wjs.dao;

import com.wjs.entity.Master;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MasterDao extends Mapper<Master> {

    @Select("select m.* from user u left join attention a on u.id=a.userId left join master m " +
            "on a.masterId=m.id where u.id=#{userId}")
    public List<Master> queryByUserAttention(String userId);

}
