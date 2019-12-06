package com.wjs.dao;

import com.wjs.entity.Chapter;
import com.wjs.entity.Course;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CourseDao extends Mapper<Course> {


}
