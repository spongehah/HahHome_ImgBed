package com.spongehah.hahhome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spongehah.hahhome.pojo.Blog;

import java.util.List;
import java.util.Map;

public interface BlogService extends IService<Blog> {
    List<Blog> getBlogsPublishedList();

    Map<String, Object> getAllDataCount();

    Blog getBlogById(Long id);

    List<Blog> getBlogsByTagId(Long tagId);

    Long getLinkOrAboutBlogByType(String s);

    List<Blog> getBlogsByCondition(Map<String, Object> hashMap);

    Integer insertBlog(Blog blog);

    Integer editBlog(Blog blog, String tagIds);

    Blog getBlogSimpleById(Long id);

    int deleteBlogAndTagRelationById(Long id);

    List<Blog> getBlogIdAndTitle();


    Map<String, List<Blog>> getBLogArchive();


    List<Blog> getSearchBlogs(String title);

    Boolean updateNumById(Blog blog);
}
