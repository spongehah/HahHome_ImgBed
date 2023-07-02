package com.spongehah.hahhome.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spongehah.hahhome.pojo.Blog;
import com.spongehah.hahhome.pojo.Comment;
import com.spongehah.hahhome.service.BlogService;
import com.spongehah.hahhome.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private BlogService blogService;

    /**
     * 跳转到评论管理页面,并传入博客列表的id和标题
     */
    @GetMapping("/admin/tocomment")
    public String toComment(Model model){
        List<Blog> blogs = blogService.getBlogIdAndTitle();
        model.addAttribute("blogs",blogs);
        return "admin/comment";
    }

    /**
     * 查询评论的json数据
     */
    @GetMapping("/admin/comment")
    @ResponseBody
    public Object getComment(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                             @RequestParam(value = "pageSize",defaultValue = "3")Integer pageSize,
                             Long blogId){
        try {
            PageHelper.startPage(pageNum,pageSize);
            List<Comment> comments = commentService.getCommentsByBlogIdWithoutSon(blogId);
            PageInfo<Comment> pageInfo = new PageInfo<>(comments);
            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 删除评论DELETE方法
     */
    @DeleteMapping("/admin/comment")
    @ResponseBody
    public Object deleteComment(Long id){
        HashMap<Object, Object> map = new HashMap<>();
        try {
            int i = commentService.deleteComment(id);
            if (i > 0) {
                map.put("code",100);
            } else {
                map.put("code",200);
                map.put("message","删除失败");
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",200);
            map.put("message","系统暂忙,请稍后再试!");
            return map;
        }
    }
}
