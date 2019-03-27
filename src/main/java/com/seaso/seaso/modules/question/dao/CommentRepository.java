package com.seaso.seaso.modules.question.dao;

import com.seaso.seaso.modules.question.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByCommentId(String commentId);

    void deleteByCommentId(String commentId);

}
