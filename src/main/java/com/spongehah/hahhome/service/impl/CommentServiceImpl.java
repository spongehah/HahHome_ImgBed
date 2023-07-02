package com.spongehah.hahhome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spongehah.hahhome.mapper.CommentMapper;
import com.spongehah.hahhome.pojo.Comment;
import com.spongehah.hahhome.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentsByBlogId(Long blogId) {
        List<Comment> comments = commentMapper.getCommentsByBlogIdAndExtendsCommentIdIsNull(blogId);
        getSonComments(comments);
        return comments;
    }

    private void getSonComments(List<Comment> comments) {
        if (comments.size() > 0){
            for (Comment comment : comments) {
                List<Comment> sonComments = commentMapper.getSonCommentsByExtendsCommentId(comment.getId());
                if(sonComments.size() > 0){
                    List<Comment> sonCommentList = comment.getList();
                    sonCommentList.addAll(sonComments);
                    //递归调用获取子评论的子评论，全部装在父评论的list属性里
                    getSonComments(sonCommentList);
                }
            }
        }
    }

    @Override
    public List<Comment> getCommentsByBlogIdAndExtendsCommentIdIsNull(Long blogId) {
        return commentMapper.getCommentsByBlogIdAndExtendsCommentIdIsNull(blogId);
    }

    @Override
    public List<Comment> getCommentsByBlogIdWithoutSon(Long blogId) {
        return commentMapper.getCommentsByBlogIdWithoutSon(blogId);
    }

    /**
     * 在管理页删除评论
     * @param id
     * @return
     */
    @Override
    public int deleteComment(Long id) {
        //先获得这个评论
        Comment comment = commentMapper.selectById(id);
        //将这个评论下的子评论放到它的list属性里
        getSonComments(Arrays.asList(comment));
        //获得它的子评论
        List<Comment> sonComments = comment.getList();
        //递归删除子评论
        deleteSonComment(sonComments);
        //删除这个评论
        int i = commentMapper.deleteById(id);
        return i;
    }

    private void deleteSonComment(List<Comment> sonComments) {
        if(sonComments.size() > 0){
            for (Comment sonComment : sonComments) {
                if(sonComment.getList().size() > 0){
                    deleteSonComment(sonComment.getList());
                }
                commentMapper.deleteById(sonComment.getId());
            }
        }
    }

    @Override
    public int insertComment(Comment comment) {
        return commentMapper.insert(comment);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentMapper.getCommentById(id);
    }
}
