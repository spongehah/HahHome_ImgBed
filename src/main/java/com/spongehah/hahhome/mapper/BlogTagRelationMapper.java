package com.spongehah.hahhome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spongehah.hahhome.pojo.BlogTagRelation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogTagRelationMapper extends BaseMapper<BlogTagRelation> {


    Integer deleteByBlogId(Long blogId);

    void deleteRelationByBlogId(Long id);
}
