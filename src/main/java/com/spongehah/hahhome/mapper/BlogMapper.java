package com.spongehah.hahhome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spongehah.hahhome.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    List<Blog> getBlogsPublishedList();

    Integer getBlogsPublishedCount();

    Integer getBlogViewCount();

    Blog getBlogById(@Param("id") Long id);

    List<Blog> getBlogsByTagId(Long tagId);

    Long getLinkOrAboutBlogByType(String type);

    List<Blog> getBlogsByCondition(Map<String, Object> map);

    Blog getBlogSimpleById(Long id);

    List<Blog> getBlogIdAndTitle();

    List<String> getBLogYear();

    List<Blog> getBlogArchive(String year);

    List<Blog> getSearchBlogs(String title);

    Integer updateNumById(@Param("views") Integer views,@Param("id") Long id);
}
