package com.spongehah.hahhome.controller;

import com.spongehah.hahhome.pojo.Blog;
import com.spongehah.hahhome.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class LinkAndAboutAndArchieveController {
    
    @Autowired
    private BlogService blogService;

    /**
     * 跳转到友链页面
     * @param model
     * @return
     */
    @GetMapping("/toLink")
    public String toLink(Model model){
        Long blogId =  blogService.getLinkOrAboutBlogByType("1");
        Blog blog = blogService.getBlogById(blogId);
        model.addAttribute("blog",blog);
        return "blog/link";
    }

    /**
     * 跳转到关于我页面
     * @param model
     * @return
     */
    @GetMapping("/toAbout")
    public String toAbout(Model model){
        Long blogId =  blogService.getLinkOrAboutBlogByType("2");
        Blog blog = blogService.getBlogById(blogId);
        model.addAttribute("blog",blog);
        return "blog/link";
    }

    /**
     * 跳转到归档页面
     */
    @GetMapping("/toArchive")
    public String toArchive(Model model){
        Map<String, List<Blog>> ArchiveMap = blogService.getBLogArchive();
        model.addAttribute("ArchiveMap",ArchiveMap);
        return "blog/archive";
    }

}
