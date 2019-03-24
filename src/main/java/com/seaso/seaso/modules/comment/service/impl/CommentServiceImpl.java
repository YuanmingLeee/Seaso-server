package com.seaso.seaso.modules.comment.service.impl;

import com.seaso.seaso.modules.comment.dao.CommentRepository;
import com.seaso.seaso.modules.comment.entity.Comment;
import com.seaso.seaso.modules.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Optional<Comment> findByCommentId(String commentId) {
        return commentRepository.findByCommentId(commentId);
    }

    @Override
    public void deleteByCommentId(String commentId) {
        commentRepository.deleteByCommentId(commentId);
    }

    @Override
    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }


}
