package com.wjs.serviceImpl;

import com.wjs.dao.AttentionDao;
import com.wjs.entity.Attention;
import com.wjs.service.AttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AttentionServiceImpl implements AttentionService {
    @Autowired
    private AttentionDao attentionDao;



    @Override
    public void addAttention(Attention attention) {
        try{
            attentionDao.insert(attention);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("添加用户关注信息失败");
        }
    }
}
