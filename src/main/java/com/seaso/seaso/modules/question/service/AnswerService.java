package com.seaso.seaso.modules.question.service;

import com.seaso.seaso.modules.question.entity.Answer;
import com.seaso.seaso.modules.question.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AnswerService {
    Page<Comment> getCommentsByAnswerId(String answerId);

    void createAnswer (Answer answer);

    Page<Answer> getAnswersByQuestionId(String questionId, Pageable pageable);

    void likeAnswerById(String answerId);

    void dislikeAnswerById(String answerId);

    Optional<Answer> getAnswerById(String answerId);

}
