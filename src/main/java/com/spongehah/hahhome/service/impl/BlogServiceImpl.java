package com.spongehah.hahhome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spongehah.hahhome.mapper.BlogMapper;
import com.spongehah.hahhome.mapper.BlogTagRelationMapper;
import com.spongehah.hahhome.mapper.CommentMapper;
import com.spongehah.hahhome.mapper.TagMapper;
import com.spongehah.hahhome.pojo.Blog;
import com.spongehah.hahhome.pojo.BlogTagRelation;
import com.spongehah.hahhome.service.BlogService;
import com.spongehah.hahhome.utils.MarkDownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    
    @Autowired
    private BlogMapper blogMapper;
    
    @Autowired
    private TagMapper tagMapper;
    
    @Autowired
    private CommentMapper commentMapper;
    
    @Autowired
    private BlogTagRelationMapper blogTagRelationMapper;

    @Override
    public List<Blog> getBlogsPublishedList() {
        return blogMapper.getBlogsPublishedList();
    }

    @Override
    public Map<String, Object> getAllDataCount() {
        Map<String,Object> map = new HashMap<>();
        Integer blogCount = blogMapper.getBlogsPublishedCount();
        Integer  commentCount = commentMapper.getCommentCount();
        Integer tagCount = tagMapper.getTagCount();
        Integer viewCount = blogMapper.getBlogViewCount();
        map.put("BlogCount",blogCount);
        map.put("CommentCount",commentCount);
        map.put("TagCount",tagCount);
        map.put("ViewCount",viewCount);
        return map;
    }

    @Override
    public Blog getBlogById(Long id) {
        Blog blog = blogMapper.getBlogById(id);
        String content = blog.getContent();
        blog.setContent(MarkDownUtils.markdownToHtmlExtensions(content));
        return blog;
    }

    @Override
    public List<Blog> getBlogsByTagId(Long tagId) {
        return blogMapper.getBlogsByTagId(tagId);
    }

    @Override
    public Long getLinkOrAboutBlogByType(String type) {
        return blogMapper.getLinkOrAboutBlogByType(type );
    }

    @Override
    public List<Blog> getBlogsByCondition(Map<String, Object> hashMap) {
        return blogMapper.getBlogsByCondition(hashMap);
    }

    @Override
    public Integer insertBlog(Blog blog) {
        return blogMapper.insert(blog);
    }

    @Override
    public Integer editBlog(Blog blog, String tagIds) {
        Integer i = blogTagRelationMapper.deleteByBlogId(blog.getId());
        Integer j = blogMapper.updateById(blog);
        if(j > 0){
            if(tagIds != null && tagIds != ""){
                String[] ids = tagIds.split(",");
                for (String id : ids) {
                    int k = blogTagRelationMapper.insert(new BlogTagRelation(null, blog.getId(), Long.parseLong(id)));
                    if(k <= 0){
                        return k;
                    }
                }
            }
        }
        return j;
    }

    @Override
    public Blog getBlogSimpleById(Long id) {
        return blogMapper.getBlogSimpleById(id);
    }

    @Override
    public int deleteBlogAndTagRelationById(Long id) {
        blogTagRelationMapper.deleteRelationByBlogId(id);
        commentMapper.deleteByBLogId(id);
        int i = blogMapper.deleteById(id);
        return i;
    }

    @Override
    public List<Blog> getBlogIdAndTitle() {
        return blogMapper.getBlogIdAndTitle();
    }

    @Override
    public Map<String, List<Blog>> getBLogArchive() {
        Map<String, List<Blog>> map = new HashMap<>();
        List<String> years = blogMapper.getBLogYear();
        for (String year : years) {
            List<Blog> blogs = blogMapper.getBlogArchive(year);
            map.put(year,blogs);
        }
        return map;
    }

    @Override
    public List<Blog> getSearchBlogs(String title) {
        return blogMapper.getSearchBlogs(title);
    }

    @Override
    public Boolean updateNumById(Blog blog) {
        return blogMapper.updateNumById(blog.getViews(),blog.getId()) > 1;
    }
}
