package com.spongehah.hahhome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spongehah.hahhome.pojo.Tag;

import java.util.List;

public interface TagService extends IService<Tag> {
    List<Tag> getTagsList();

    List<Tag> getTagListAndCount();

    Integer getTagCount();

    List<Tag> getTagsByBlogId(Long blogId);

    List<Tag> getTagsByName(String tagName);

    int insert(Tag tag);

    int updateTag(Tag tag);
}
