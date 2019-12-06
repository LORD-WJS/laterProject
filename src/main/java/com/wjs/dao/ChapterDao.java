package com.wjs.dao;

import com.wjs.entity.Chapter;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ChapterDao extends Mapper<Chapter> {

    @Select("select * from chapter where albumId=#{albumId}")
    public List<Chapter> queryByAlbumId(String albumId);

}
