package com.seaso.seaso.modules.question.service;

import com.seaso.seaso.modules.question.entity.Answer;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface AnswerService {

    void createAnswer(Answer answer);

    List<Answer> getAnswersByQuestionId(String questionId, int page, int size, Sort sort);

    void likeAnswerById(String answerId, boolean set);

    void dislikeAnswerById(String answerId, boolean set);

    Answer getAnswerById(String answerId);

    void deleteAnswerById(String answerId);
}
