package com.seaso.seaso.modules.question.dao;

import com.seaso.seaso.modules.question.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByCommentId(Long commentId);

    Optional<Comment> findByCommentIdAndCreator(Long commendId, Long creator);

    boolean existsByCommentId(Long commentId);

    /**
     * Paged root {@link Comment} (not reply) given answer ID.
     *
     * @param answerId answer ID.
     * @param pageable pageable instance with page number, page size, and sorting method
     * @return a paged root comments.
     * @see Page
     * @see Pageable
     */
    Page<Comment> findByAnswerIdAndReplyIdIsNull(Long answerId, Pageable pageable);

    /**
     * Paged replies given root comment ID.
     *
     * @param rootCommentId root comment ID.
     * @param pageable      pageable instance with page number, page size, and sorting method
     * @return a paged replies.
     * @see Page
     * @see Pageable
     */
    Page<Comment> findByRootCommentIdAndReplyIdNotNull(Long rootCommentId, Pageable pageable);

    void deleteAllByAnswerId(Long answerId);

    void deleteAllByAnswerIdIn(Collection<Long> answerIds);
}
