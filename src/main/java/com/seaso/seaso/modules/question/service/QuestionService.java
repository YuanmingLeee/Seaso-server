package com.seaso.seaso.modules.question.service;

import com.seaso.seaso.common.exception.ServiceException;
import com.seaso.seaso.modules.question.entity.Question;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface QuestionService {

    Question findQuestionById(String questionId) throws ServiceException;

    List<Question> findAllQuestions(int page, int size, Sort sort) throws ServiceException;

    void createQuestion(Question question);

    void deleteQuestionById(String questionId) throws ServiceException;
}
