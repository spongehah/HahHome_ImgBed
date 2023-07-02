package com.spongehah.hahhome.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spongehah.hahhome.pojo.Blog;
import com.spongehah.hahhome.pojo.Tag;
import com.spongehah.hahhome.service.BlogService;
import com.spongehah.hahhome.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TagController {
    
    @Autowired
    private TagService tagService;
    
    @Autowired
    private BlogService blogService;

    /**
     * 跳转到标签页面，并展示标签列表
     * @param TagId
     * @param model
     * @return
     */
    @GetMapping("/toTags")
    public String toTags(@RequestParam(value = "TagId",defaultValue = "0") Long TagId, Model model){
        List<Tag> tags = tagService.getTagListAndCount();
        Integer tagsCount = tagService.getTagCount();
        model.addAttribute("tags",tags);
        model.addAttribute("TagsCount",tagsCount);
        model.addAttribute("Id",TagId);
        return "blog/tags";
    }

    /**
     * 展示标签页面指定标签的博客列表
     * @param tagId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/blogTagsShowList/{tagId}")
    @ResponseBody
    public Object blogTagsShowList(@PathVariable("tagId") Long tagId,
                                   @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize",defaultValue = "3") Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogService.getBlogsByTagId(tagId);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        return blogPageInfo;
    }

    /**
     * 展示博客详情页该博客的标签有哪些
     * @param blogId
     * @return
     */
    @GetMapping("/blogTagById/{BlogId}")
    @ResponseBody
    public Object blogTagByBlogId(@PathVariable("BlogId") Long blogId){
        List<Tag> tags = tagService.getTagsByBlogId(blogId);
        return tags;
    }
    
    
}