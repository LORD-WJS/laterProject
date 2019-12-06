package com.wjs.dao;

import com.wjs.entity.User;
import com.wjs.vo.MapVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User> {

    @Select("select count(*) from user where sex=#{sex} and date_sub(now(),interval ${time} day) < createDate  ")
    public Integer queryManByTime(@Param("sex") String sex,@Param("time") String time);

    @Select("select location name,count(*) `value` from user group by location   ")
    public List<MapVO> queryLocationRange();

}
