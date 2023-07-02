package com.spongehah.hahhome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spongehah.hahhome.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    Integer getTagCount();

    List<Tag> getTagsList();

    List<Tag> getTagListAndCount();

    List<Tag> getTagsByBlogId(@Param("blogId") Long blogId);

    List<Tag> getTagsByName(String tagName);

    int updateTag(@Param("tag")Tag tag);
}
