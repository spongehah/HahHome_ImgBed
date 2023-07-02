package com.spongehah.hahhome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spongehah.hahhome.pojo.BlogTagRelation;

public interface BLogTagRelationService extends IService<BlogTagRelation> {
    Integer insertBLogTagRelation(BlogTagRelation blogTagRelation);
}
