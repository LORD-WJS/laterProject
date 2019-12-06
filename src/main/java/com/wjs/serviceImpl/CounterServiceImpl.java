package com.wjs.serviceImpl;

import com.wjs.dao.CounterDao;
import com.wjs.entity.Counter;
import com.wjs.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CounterServiceImpl implements CounterService {
    @Autowired
    private CounterDao counterDao;


    @Override
    public void addCounter(Counter counter) {
        try{
            counterDao.insert(counter);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("添加计数器失败");
        }
    }

    @Override
    public void remove(Counter counter) {
        try{
            counterDao.deleteByPrimaryKey(counter);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("删除计数器失败");
        }
    }

    @Override
    public void modify(Counter counter) {
        try{
            counterDao.updateByPrimaryKeySelective(counter);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("更新计数器失败");
        }
    }
}
