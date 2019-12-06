package com.wjs.dao;

import com.wjs.entity.Counter;
import com.wjs.entity.Course;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CounterDao extends Mapper<Counter> {

    @Select("select * from counter where courseId=#{courseId}")
    public List<Counter> queryByCourseId(String courseId);

}
