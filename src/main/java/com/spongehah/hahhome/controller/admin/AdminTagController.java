package com.spongehah.hahhome.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spongehah.hahhome.pojo.Tag;
import com.spongehah.hahhome.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminTagController {
    
    @Autowired
    private TagService tagService;

    /**
     * 跳转到标签管理页面
     */
    @GetMapping("/admin/toTags")
    public String adminToTags(){
        return "admin/tags";
    }

    /**
     * 查询标签的json数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/admin/Tags")
    @ResponseBody
    public Object getAdminTags(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize){
        try {
            Page<Tag> page = new Page<>(pageNum,pageSize);
            Page<Tag> tagPage = tagService.page(page);
            return tagPage;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 跳转到添加标签页
     */
    @GetMapping("/admin/toTagsInput")
    public String amdinToTagsInput(){
        return "admin/tags-input";
    }

    /**
     * 标签添加POST方法
     */
    @PostMapping("/admin/Tags")
    @ResponseBody
    public Object inputTag(String tagName, RedirectAttributes redirectAttributes){
        Map<String, Object> hashMap = new HashMap<>();
        try {
            List<Tag> tags = tagService.getTagsByName(tagName);
            if (tags.size() > 0) {
                hashMap.put("code", 200);
                hashMap.put("message", "name已经被使用了");
                return hashMap;
            }
            Tag tag = new Tag();
            tag.setName(tagName);
            int i = tagService.insert(tag);
            if (i > 0) {
                hashMap.put("code", 100);
            } else {
                hashMap.put("code", 200);
                hashMap.put("message", "添加失败");
            }
            return hashMap;
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("code",200);
            hashMap.put("message","系统暂忙,请稍后再试!");
            return hashMap;
        }
    }

    /**
     * 跳转到标签修改页面
     */
    @GetMapping("/admin/toTagsEdit")
    public String adminToTagEdit(Long id ,Model model){
        Tag tag = tagService.getById(id);
        model.addAttribute("Tag",tag);
        return "admin/tags-edit";
    }

    /**
     * 标签修改PUT方法
     */
    @PutMapping("/admin/Tags")
    public String updateTag(Tag tag,RedirectAttributes redirectAttributes){
        int i = tagService.updateTag(tag);
        if(i > 0){
            redirectAttributes.addFlashAttribute("msg","修改成功");
        }else {
            redirectAttributes.addFlashAttribute("msg","修改失败");
        }
        return "redirect:/admin/toTags";
    }

    /**
     * 标签删除DELETE方法
     */
    @DeleteMapping("/admin/Tags")
    @ResponseBody
    public Object deleteTag(Long id){
        HashMap<String, Object> map = new HashMap<>();
        try {
            boolean i = tagService.removeById(id);
            if (i){
                map.put("code",100);
            }else {
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
