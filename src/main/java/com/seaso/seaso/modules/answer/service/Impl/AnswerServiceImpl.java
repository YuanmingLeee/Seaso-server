package com.seaso.seaso.modules.answer.service.Impl;


import com.seaso.seaso.modules.answer.dao.AnswerRepository;
import com.seaso.seaso.modules.answer.entity.Answer;
import com.seaso.seaso.modules.answer.service.AnswerService;
import com.seaso.seaso.modules.comment.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    //Implementation to be added here
    @Override
    public Page<Comment> getCommentsByAnswerId(String answerId) {
        return null;
    }

    @Override
    public void createAnswer(Answer answer) {
        answerRepository.save(answer);
    }

    //Implementation to be added here
    @Override
    public Page<Answer> getAnswersByQuestionId(String questionId) {
        return null;
    }

    @Override
    @Transactional
    public void likeAnswerById(String answerId) {
        Answer ans = answerRepository.getAnswerById(answerId).get();
        ans.setLikes(ans.getLikes()+1);
        answerRepository.save(ans);
    }

    @Override
    @Transactional
    public void dislikeAnswerById(String answerId) {
        Answer ans = answerRepository.getAnswerById(answerId).get();
        ans.setDislikes(ans.getDislikes()+1);
        answerRepository.save(ans);
    }

    @Override
    public Optional<Answer> getAnswerById(String answerId) {
        return answerRepository.getAnswerById(answerId);
    }
}
