package com.seaso.seaso.modules.answer.service;

import com.seaso.seaso.modules.answer.entity.Answer;
import com.seaso.seaso.modules.comment.entity.Comment;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface AnswerService {
    Page<Comment> getCommentsByAnswerId(String answerId);

    void createAnswer (Answer answer);

    Page<Answer> getAnswersByQuestionId(String questionId);

    void likeAnswerById(String answerId);

    void dislikeAnswerById(String answerId);

    Optional<Answer> getAnswerById(String answerId);

}
