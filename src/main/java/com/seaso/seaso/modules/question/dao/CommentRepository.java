package com.seaso.seaso.modules.question.dao;

import com.seaso.seaso.modules.question.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByCommentId(Long commentId);

    Page<Comment> findByAnswerId(Long answerId, Pageable pageable);

    Page<Comment> findByAnswerIdAndReplyId(Long answerId, Long replyId, Pageable pageable);

    void deleteByCommentId(Long commentId);

    void deleteAllByAnswerId(Long answerId);

    void deleteAllByAnswerIdIn(Collection<Long> answerIds);

    void deleteAllByReplyId(Long replyId);

}
