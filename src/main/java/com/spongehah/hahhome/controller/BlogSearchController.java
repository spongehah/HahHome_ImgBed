package com.spongehah.hahhome.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spongehah.hahhome.pojo.Blog;
import com.spongehah.hahhome.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BlogSearchController {
    
    @Autowired
    private BlogService blogService;

    /**
     * 搜索博客页面
     * @param title
     * @param model
     * @return
     */
    @GetMapping("/toblogSearch")
    public String toblogSearch(String title, Model model){
        model.addAttribute("title",title);
        return "blog/search";
    }

    /**
     * 搜索博客列表展示
     */
    @GetMapping("/blogSearchResults")
    @ResponseBody
    public Object blogSearchResults(String title,
                                    @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                    @RequestParam(value = "pageSize",defaultValue = "3")Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogService.getSearchBlogs(title);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        return pageInfo;
    }
}
