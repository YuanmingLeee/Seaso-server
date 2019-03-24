package com.seaso.seaso.modules.comment.service;

import com.seaso.seaso.modules.comment.entity.Comment;

import java.util.Optional;

public interface CommentService {

    Optional<Comment> findByCommentId(String commentId);

    void deleteByCommentId(String commentId);

    void createComment(Comment comment);
}
