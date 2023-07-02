package com.spongehah.hahhome.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId
    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 内容
     */
    private String content;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 创建时间
     */
    private String createtime;

    /**
     * 博客id
     */
    private Long blogId;

    /**
     * 继承父类评论的id
     */
    private Long extendsCommentid;

    /**
     * 是否是管理员的评论
     */
    private Boolean administrator;

    /**
     * 子评论列表
     */
    @TableField(exist = false)
    private List<Comment> list = new ArrayList<>();



}
