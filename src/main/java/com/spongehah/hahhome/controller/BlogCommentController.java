package com.spongehah.hahhome.controller;

import com.spongehah.hahhome.pojo.Blog;
import com.spongehah.hahhome.pojo.Comment;
import com.spongehah.hahhome.pojo.QQ;
import com.spongehah.hahhome.pojo.User;
import com.spongehah.hahhome.service.BlogService;
import com.spongehah.hahhome.service.CommentService;
import com.spongehah.hahhome.utils.DateTimeFormatUtils;
import com.spongehah.hahhome.utils.MailDataUtils;
import com.spongehah.hahhome.utils.QQUtils;
import com.spongehah.hahhome.utils.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class BlogCommentController {
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private BlogService blogService;
    
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 展示博客详情页该博客的评论
     * @param blogId
     * @return
     */
    @GetMapping("/comment")
    @ResponseBody
    public Object getComment(Long blogId){
        List<Comment> comments = commentService.getCommentsByBlogId(blogId);
        return comments;
    }

    /**
     * 添加评论
     * @param comment
     * @param session
     * @return
     */
    @PostMapping("/comment")
    @ResponseBody
    public Object addComment(Comment comment, HttpSession session){
        HashMap<String, Object> map = new HashMap<>();
        try {
            User user = (User) session.getAttribute(SessionInfo.LOGIN_INFO);
            if(user != null){
                comment.setNickname(user.getNickname());
                comment.setAvatar(user.getAvatar());
                comment.setAdministrator(true);
            }else {
                comment.setAdministrator(false);
            }

            comment.setCreatetime(DateTimeFormatUtils.getDateTime(new Date()));
            int i = commentService.insertComment(comment);
            if (i > 0) {
                map.put("code",100);
                map.put("extendsCommentId",comment.getExtendsCommentid());
            }else {
                map.put("code",200);
                map.put("message","添加评论失败");
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",200);
            map.put("message","系统繁忙,请稍后再试");
            return map;
        }
    }

    /**
     * 评论邮件提醒
     */
    @PostMapping("/emailAlert")
    @ResponseBody
    public Object emailAlert(Comment comment){
        HashMap<String, Object> map = new HashMap<>();
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            Blog blog = blogService.getBlogSimpleById(comment.getBlogId());
            String url = MailDataUtils.DOMAIN_NAME + "/toBlog/" + blog.getId();
            if (blog.getType().equals("2")) {
                url = MailDataUtils.DOMAIN_NAME + "/toAbout";
            } else if (blog.getType().equals("1")) {
                url = MailDataUtils.DOMAIN_NAME + "/toLink";
            }
            if (comment.getExtendsCommentid() != null && comment.getExtendsCommentid() != 0) {
                Comment ExtendsComment = commentService.getCommentById(comment.getExtendsCommentid());
                message.setFrom(MailDataUtils.MAIL_SENDER);
                // 发件人
                message.setFrom(MailDataUtils.MAIL_SENDER);
                // 收件人
                message.setTo(ExtendsComment.getEmail());
                System.out.println("########################################################################");
                // 邮件标题
                message.setSubject(MailDataUtils.MAIL_TITLE);
                // 邮件内容
                message.setText("评论人:" + comment.getNickname() +
                        "\n评论内容:" + comment.getContent() +
                        "\n文章:" + blog.getTitle() +
                        "\n文章地址:" + url);
                mailSender.send(message);
            }else {
                // 发件人
                message.setFrom(MailDataUtils.MAIL_SENDER);
                // 抄送人
                message.setTo(MailDataUtils.MAIL_CC);
                // 邮件标题
                message.setSubject(MailDataUtils.MAIL_TITLE);
                // 邮件内容
                message.setText("评论人:" + comment.getNickname() +
                        "\n评论内容:" + comment.getContent() +
                        "\n文章:" + blog.getTitle() +
                        "\n文章地址:<a href=" + url + ">" + url + "</a>");
                mailSender.send(message);
            }
            map.put("code",100);
            return map;
        } catch (MailException e) {
            e.printStackTrace();
            map.put("code",200);
            map.put("message","系统繁忙,请稍后再试");
            return map;
        }
    }

    /**
     * 拉取qq信息
     */
    @GetMapping("/getQQInfo/{qq}")
    @ResponseBody
    public Object getQQInfo(@PathVariable("qq")String qq,HttpSession session){
        HashMap<String, Object> map = new HashMap<>();
        if (!StringUtils.hasLength(qq)){
            map.put("code",200);
            map.put("message","qq号为空");
            return map;
        }
        try {
            HashMap<Object, Object> resultMap = new HashMap<>(4);
            QQ qqInfo = QQUtils.getQQInfo(Long.parseLong(qq));
            resultMap.put("avatar",qqInfo.getAvatar());
            resultMap.put("nickname",qqInfo.getName());
            resultMap.put("email",qq + "@qq.com");
            map.put("code",100);
            map.put("message","拉取qq信息成功");
            map.put("hashMap",resultMap);
            return map;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            map.put("code",200);
            map.put("message","拉取qq信息失败");
            return map;
        }
    }


}
