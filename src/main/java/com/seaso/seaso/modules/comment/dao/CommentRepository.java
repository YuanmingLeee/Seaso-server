package com.seaso.seaso.modules.comment.dao;

import com.seaso.seaso.modules.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByCommentId(String commentId);

    void deleteByCommentId(String commentId);

}
