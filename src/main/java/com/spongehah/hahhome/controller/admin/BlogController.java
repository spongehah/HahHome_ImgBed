package com.spongehah.hahhome.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spongehah.hahhome.pojo.Blog;
import com.spongehah.hahhome.pojo.BlogTagRelation;
import com.spongehah.hahhome.pojo.Tag;
import com.spongehah.hahhome.pojo.User;
import com.spongehah.hahhome.service.BLogTagRelationService;
import com.spongehah.hahhome.service.BlogService;
import com.spongehah.hahhome.service.TagService;
import com.spongehah.hahhome.utils.DateTimeFormatUtils;
import com.spongehah.hahhome.utils.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BLogTagRelationService bLogTagRelationService;

    /**
     * 跳转到admin博客管理页面，并传入标签列表
     *
     * @param model
     * @return
     */
    @GetMapping("/admin/toBlogs")
    public String adminToBLogs(Model model) {
        List<Tag> tagsList = tagService.getTagsList();
        model.addAttribute("tags", tagsList);
        return "admin/blogs";
    }

    /**
     * 传入博客管理页面的博客列表json数据
     *
     * @param tagId
     * @param title
     * @param recommend
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/admin/Blogs")
    @ResponseBody
    public Object adminBlogList(Long tagId, String title, Boolean recommend,
                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        try {
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("tagIdConditions", tagId);
            hashMap.put("titleConditions", title);
            hashMap.put("recommendConditions", recommend ? 1 : 0);

            PageHelper.startPage(pageNum, pageSize);
            List<Blog> blogs = blogService.getBlogsByCondition(hashMap);
            PageInfo<Blog> pageInfo = new PageInfo<>(blogs);

            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 跳转到admin博客添加页，并传入标签列表
     *
     * @param model
     * @return
     */
    @GetMapping("/admin/toBlogsInput")
    public String toBlogInput(Model model) {
        List<Tag> tags = tagService.getTagsList();
        model.addAttribute("tags", tags);
        return "admin/blogs-input.html";
    }

    /**
     * 添加博客post方法
     *
     * @param blog
     * @param tagIds
     * @param session
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/admin/Blogs")
    public String insertBlog(Blog blog, String tagIds, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            //判断添加的博客类型是否是友链页或者关于我页
            if (!"0".equals(blog.getType())) {
                if (blogService.getLinkOrAboutBlogByType(blog.getType()) != null) {
                    redirectAttributes.addFlashAttribute("msg", "1".equals(blog.getType()) ? "友链页已经存在,请勿重复添加" : "关于我页已经存在,请勿重复添加");
                    return "redirect:/admin/toBlogs";
                }
            }
            blog.setRecommendned(blog.getRecommendned() == null ? false : true);
            blog.setCommentabled(blog.getCommentabled() == null ? false : true);
            blog.setCreatetime(DateTimeFormatUtils.getDateTime(new Date()));
            User user = (User) session.getAttribute(SessionInfo.LOGIN_INFO);
            blog.setUserId(user.getId());
            blog.setViews(0);
            Integer i = blogService.insertBlog(blog);
            if (i > 0) {
                if (tagIds != null && tagIds != "") {
                    String[] ids = tagIds.split(",");
                    for (String id : ids) {
                        Integer j = bLogTagRelationService.insertBLogTagRelation(new BlogTagRelation(null, blog.getId(), Long.parseLong(id)));
                        if (j <= 0) {
                            redirectAttributes.addFlashAttribute("msg", "添加标签失败");
                            return "redirect:/admin/toBlogs";
                        }
                    }
                }
                redirectAttributes.addFlashAttribute("msg", "添加成功");
            } else {
                redirectAttributes.addFlashAttribute("msg", "添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("msg", "系统繁忙，添加失败，请稍后再试");
            return "redirect:/admin/toBlogs";
        }
        return "redirect:/admin/toBlogs";
    }

    /**
     * 跳转到博客修改页
     */
    @GetMapping("/admin/toEditBlogs")
    public String adminToEditBlogs(Long id, Model model) {
        //获取要修改的博客信息
        Blog blog = blogService.getBlogSimpleById(id);
        model.addAttribute("blogs", blog);

        //如果是普通博客，则需要获取标签信息
        if (blog.getType().equals("0")) {
            List<Tag> tags = tagService.getTagsByBlogId(id);
            String ids = "";
            if(tags.size() != 0){
                for (Tag t : tags) {
                    ids += t.getId() + ",";
                }
            }
            if (ids != null && ids != "") {
                String substring = ids.substring(0, ids.lastIndexOf(","));
                model.addAttribute("ids", substring);
            }
            List<Tag> tagsList = tagService.getTagsList();
            model.addAttribute("tags", tagsList);
        }
        return "admin/blogs-edit";
    }

    /**
     * 修改博客PUT方法
     */
    @PutMapping("/admin/Blogs")
    public String adminEditBlog(Blog blog, String tagIds, RedirectAttributes redirectAttributes) {
        try {
            blog.setRecommendned(blog.getRecommendned() == null ? false : true);
            blog.setCommentabled(blog.getCommentabled() == null ? false : true);
            blog.setEdittime(DateTimeFormatUtils.getDateTime(new Date()));
            Integer i = blogService.editBlog(blog, tagIds);
            if (i > 0) {
                redirectAttributes.addFlashAttribute("msg", "修改成功");
            } else {
                redirectAttributes.addFlashAttribute("msg", "修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("msg", "系统暂忙,修改失败,请稍后再试");
            return "redirect:/admin/toBlogs";
        }
        return "redirect:/admin/toBlogs";
    }

    /**
     * 删除博客DELETE方法
     */
    @DeleteMapping("/admin/Blogs")
    @ResponseBody
    public Object deleteBlog(Long id,RedirectAttributes redirectAttributes){
        Map<String,Object> hashMap = new HashMap<>();
        try {
            int i = blogService.deleteBlogAndTagRelationById(id);
            if (i > 0){
                hashMap.put("code",100);
            }else {
                hashMap.put("code",200);
                hashMap.put("message","删除失败");
            }
            return hashMap;
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("code",200);
            hashMap.put("message","系统暂忙,删除失败,请稍后再试");
            return hashMap;
        }
    }
    
}
    