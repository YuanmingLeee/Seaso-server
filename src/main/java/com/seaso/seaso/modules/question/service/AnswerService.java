package com.seaso.seaso.modules.question.service;

import com.seaso.seaso.modules.question.entity.Answer;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface AnswerService {

    void createAnswer(Answer answer);

    List<Answer> getAnswersByQuestionId(Long questionId, int page, int size, Sort sort);

    void likeAnswerById(Long answerId, boolean set);

    void dislikeAnswerById(Long answerId, boolean set);

    Answer getAnswerById(Long answerId);

    void deleteAnswerById(Long answerId);
}
