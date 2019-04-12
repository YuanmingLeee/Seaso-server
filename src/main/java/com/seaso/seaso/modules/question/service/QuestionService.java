package com.seaso.seaso.modules.question.service;

import com.seaso.seaso.modules.question.entity.Question;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface QuestionService {

    Question findQuestionById(Long questionId);

    List<Question> findAllQuestions(int page, int size, Sort sort);

    void createQuestion(Question question);

    void deleteQuestionById(Long questionId);
}
