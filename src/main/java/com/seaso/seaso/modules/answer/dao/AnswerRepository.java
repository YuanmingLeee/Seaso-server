package com.seaso.seaso.modules.answer.dao;

import com.seaso.seaso.modules.answer.entity.Answer;
import com.seaso.seaso.modules.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AnswerRepository extends JpaRepository {

    Page<Comment> getCommentsByAnswerId(String answerId);

    Page<Answer> getAnswersByQuestionId(String questionId);

    Optional<Answer> getAnswerById(String answerId);

}
