package com.spongehah.hahhome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spongehah.hahhome.mapper.TagMapper;
import com.spongehah.hahhome.pojo.Tag;
import com.spongehah.hahhome.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    
    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> getTagsList() {
        return tagMapper.getTagsList();
    }

    @Override
    public List<Tag> getTagListAndCount() {
        return tagMapper.getTagListAndCount();
    }

    @Override
    public Integer getTagCount() {
        return tagMapper.getTagCount();
    }

    @Override
    public List<Tag> getTagsByBlogId(Long blogId) {
        return tagMapper.getTagsByBlogId(blogId);
    }

    @Override
    public List<Tag> getTagsByName(String tagName) {
        return tagMapper.getTagsByName(tagName);
    }

    @Override
    public int insert(Tag tag) {
        return tagMapper.insert(tag);
    }

    @Override
    public int updateTag(Tag tag) {
        return tagMapper.updateTag(tag);
    }
}
