package com.spongehah.hahhome.pojo;


import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@TableName("t_blog")  开启了MyBatis-plus的table-prefix
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 首图
     */
    private String firstPicture;

    /**
     * 标签/原创/转载/引用
     */
    private String flag;

    /**
     * 浏览量
     */
    private Integer views;

    /**
     * 是否可评论
     */
    private Boolean commentabled;

    /**
     * 是否发表
     */
    private Boolean published;

    /**
     * 是否推荐
     */
    private Boolean recommendned;

    /**
     * 创建时间
     */
    private String createtime;

    /**
     * 修改时间
     */
    private String edittime;

    /**
     * userid
     */
    private Long userId;

    /**
     * username
     */
    private String userName;

    /**
     * 评论数
     */
    private  Integer commentCount;

    /**
     * 博客描述
     */
    private String description;

    /**
     * 博客类型
     */
    private String type;
    
    
    
    
    
}
