package com.wjs.serviceImpl;

import com.wjs.dao.CourseDao;
import com.wjs.entity.Course;
import com.wjs.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;


    @Override
    public void addCourse(Course course) {
        try{
            courseDao.insert(course);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("添加功课分类失败");
        }
    }

    @Override
    public void remove(Course course) {
        try{
            courseDao.deleteByPrimaryKey(course);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("添加功课分类失败");
        }
    }
}
