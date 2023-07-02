package com.spongehah.hahhome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spongehah.hahhome.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    Integer getCommentCount();

    List<Comment> getCommentsByBlogIdAndExtendsCommentIdIsNull(@Param("blogId") Long blogId);

    List<Comment> getSonCommentsByExtendsCommentId(@Param("extendsCommentId") Long extendsCommentId);

    void deleteByBLogId(Long id);


    List<Comment> getCommentsByBlogIdWithoutSon(Long blogId);

    Comment getCommentById(Long id);
}
