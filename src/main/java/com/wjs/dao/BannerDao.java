package com.wjs.dao;

import com.wjs.entity.Banner;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BannerDao extends Mapper<Banner> {

    public void updateBanner(Banner banner);

    @Select("select * from banner order by createDate limit 0,5")
    public List<Banner> queryNew5();

}
