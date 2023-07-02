package com.spongehah.hahhome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spongehah.hahhome.mapper.BlogTagRelationMapper;
import com.spongehah.hahhome.pojo.BlogTagRelation;
import com.spongehah.hahhome.service.BLogTagRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BLogTagServiceImpl extends ServiceImpl<BlogTagRelationMapper, BlogTagRelation> implements BLogTagRelationService {
    
    @Autowired
    private BlogTagRelationMapper blogTagRelationMapper;

    @Override
    public Integer insertBLogTagRelation(BlogTagRelation blogTagRelation) {
        return blogTagRelationMapper.insert(blogTagRelation);
    }
}
