package com.spongehah.hahhome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spongehah.hahhome.pojo.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {

    List<Comment> getCommentsByBlogIdAndExtendsCommentIdIsNull(Long blogId);

    List<Comment> getCommentsByBlogId(Long blogId);

    List<Comment> getCommentsByBlogIdWithoutSon(Long blogId);

    int deleteComment(Long id);

    int insertComment(Comment comment);

    Comment getCommentById(Long id);
}
