package com.spongehah.hahhome.controller;

import com.spongehah.hahhome.pojo.Blog;
import com.spongehah.hahhome.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {
    
    @Autowired
    private BlogService blogService;

    /**
     * 首页视图定位
     * @return
     */
    @GetMapping({"/toIndex","/index"})
    public  String toIndex(){
        return "blog/index";
    }

    /**
     * 跳转到博客详情页
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/toBlog/{id}")
    public String toBlog(@PathVariable("id") Long id, Model model){
        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog",blog);
        return "blog/blog";
    }
    

}
    