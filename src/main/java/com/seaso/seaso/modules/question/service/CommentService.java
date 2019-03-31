package com.seaso.seaso.modules.question.service;

import com.seaso.seaso.modules.question.entity.Comment;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CommentService {

    Comment findByCommentId(Long commentId);

    List<Comment> findByAnswerId(Long answerId, int page, int size, Sort sort);

    List<Comment> findByAnswerIdAndReplyId(Long answerId, Long replyId, int page, int size, Sort sort);

    void deleteByCommentId(Long commentId);

    void createComment(Comment comment);
}
