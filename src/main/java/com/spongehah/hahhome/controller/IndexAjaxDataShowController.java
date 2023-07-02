package com.spongehah.hahhome.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spongehah.hahhome.pojo.Blog;
import com.spongehah.hahhome.service.BlogService;
import com.spongehah.hahhome.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class IndexAjaxDataShowController {
    
    @Autowired
    private BlogService blogService;
    
    @Autowired
    private TagService tagService;

    /**
     * 博客主页博客列表显示
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/BlogsShowList")
    public Object blogsShowList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogService.getBlogsPublishedList();
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        return blogPageInfo;
    }

    /**
     * 返回博客右侧访问量、博客数量等数据
     * @return
     */
    @GetMapping("/BlogsCount")
    public Object blogsCount(){
        Map<String,Object> map = blogService.getAllDataCount();
        return map;
    }

    /**
     * 博客首页的标签展示
     * @return
     */
    @GetMapping("/TagShowList")
    public Object tagShowList(){
        return tagService.getTagsList();
    }
    
    
}
