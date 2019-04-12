package com.seaso.seaso.modules.question.service;

import com.seaso.seaso.modules.question.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface CommentService {

    Comment findByCommentId(Long commentId);

    Page<Comment> findByAnswerId(Long answerId, int page, int size, Sort sort);

    Page<Comment> findByRootCommentId(Long commentId, int page, int size, Sort sort);

    void deleteByCommentId(Long commentId);

    void deleteByCommentIdAndCreator(Long commentId, Long creator);

    void createComment(Comment comment);
}
