package com.seaso.seaso.modules.question.service.impl;

import com.seaso.seaso.common.exception.ResourceNotFoundException;
import com.seaso.seaso.common.exception.ServiceException;
import com.seaso.seaso.modules.question.dao.AnswerRepository;
import com.seaso.seaso.modules.question.dao.CommentRepository;
import com.seaso.seaso.modules.question.dao.QuestionRepository;
import com.seaso.seaso.modules.question.entity.Answer;
import com.seaso.seaso.modules.question.entity.Question;
import com.seaso.seaso.modules.question.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, AnswerRepository answerRepository,
                               CommentRepository commentRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Question findQuestionById(Long questionId) {
        return questionRepository.findByQuestionId(questionId).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional
    public List<Question> findAllQuestions(int page, int size, Sort sort) throws ServiceException {
        Pageable pageable = PageRequest.of(page, size, sort);
        return questionRepository.findAll(pageable).getContent();
    }

    @Override
    @Transactional
    public void createQuestion(Question question) {
        questionRepository.save(question);
    }

    @Override
    @Transactional
    public void deleteQuestionById(Long questionId) {
        questionRepository.deleteByQuestionId(questionId);

        Collection<Long> answers = answerRepository.findAllByQuestionId(questionId).stream()
                .map(Answer::getAnswerId)
                .collect(Collectors.toSet());
        commentRepository.deleteAllByAnswerIdIn(answers);
        answerRepository.deleteAllByQuestionId(questionId);
    }
}
