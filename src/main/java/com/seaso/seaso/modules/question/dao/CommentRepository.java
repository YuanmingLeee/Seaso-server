package com.seaso.seaso.modules.question.dao;

import com.seaso.seaso.modules.question.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByCommentId(String commentId);

    Page<Comment> findByAnswerId(String answerId, Pageable pageable);

    Page<Comment> findByAnswerIdAndReplyId(String answerId, String replyId, Pageable pageable);

    void deleteByCommentId(String commentId);

    void deleteAllByAnswerId(String answerId);

    void deleteAllByReplyId(String replyId);

}
