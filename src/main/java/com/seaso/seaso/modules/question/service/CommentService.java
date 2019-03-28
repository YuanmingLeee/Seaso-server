package com.seaso.seaso.modules.question.service;

import com.seaso.seaso.modules.question.entity.Comment;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CommentService {

    Comment findByCommentId(String commentId);

    List<Comment> findByAnswerId(String answerId, int page, int size, Sort sort);

    List<Comment> findByAnswerIdAndReplyId(String answerId, String replyId, int page, int size, Sort sort);

    void deleteByCommentId(String commentId);

    void createComment(Comment comment);
}
