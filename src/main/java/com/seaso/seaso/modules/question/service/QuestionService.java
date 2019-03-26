package com.seaso.seaso.modules.question.service;

import com.seaso.seaso.modules.question.entity.Question;

import java.util.Optional;

public interface QuestionService {

    Optional<Question> findQuestionById (String questionId);

    void createQuestion(Question question);
}
