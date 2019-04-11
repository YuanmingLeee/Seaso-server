package com.seaso.seaso.modules.question.service.impl;

import com.seaso.seaso.common.exception.ServiceException;
import com.seaso.seaso.modules.question.dao.AnswerRepository;
import com.seaso.seaso.modules.question.dao.CommentRepository;
import com.seaso.seaso.modules.question.dao.QuestionRepository;
import com.seaso.seaso.modules.question.entity.Answer;
import com.seaso.seaso.modules.question.exception.AnswerApiIllegalArgumentException;
import com.seaso.seaso.modules.question.exception.AnswerNotFoundException;
import com.seaso.seaso.modules.question.service.AnswerService;
import com.seaso.seaso.modules.question.utils.LikeStatus;
import com.seaso.seaso.modules.sys.dao.UserRepository;
import com.seaso.seaso.modules.sys.entity.User;
import com.seaso.seaso.modules.sys.utils.UserPreference;
import com.seaso.seaso.modules.sys.utils.UserUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Answer service implementation.
 *
 * @author Li Yuanming
 * @version 0.2
 */
@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository, UserRepository userRepository,
                             QuestionRepository questionRepository, CommentRepository commentRepository) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void createAnswer(Answer answer) {
        if (questionRepository.existsByQuestionId(answer.getQuestionId()))
            answerRepository.save(answer);
        throw new ServiceException("Question Id not found");
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Answer> getAnswersByQuestionId(Long questionId, int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        return answerRepository.findByQuestionId(questionId, pageable);
    }

    @Override
    @Transactional
    public void likeAnswerById(Long answerId, boolean set) {
        setPreferenceByAnswerId(answerId, true, set);
    }

    @Override
    @Transactional
    public void dislikeAnswerById(Long answerId, boolean set) {
        setPreferenceByAnswerId(answerId, false, set);
    }

    /**
     * Generify set preference by answer ID.
     *
     * @param answerId answer ID.
     * @param like     true for like, false for dislike.
     * @param set      true for set, false for cancel.
     */
    private void setPreferenceByAnswerId(@NotNull Long answerId, boolean like, boolean set) {
        Answer answer = answerRepository.findByAnswerId(answerId).orElseThrow(AnswerNotFoundException::new);
        User user = UserUtils.getCurrentUser();

        LikeStatus likeStatus = answer.getLikeStatus();
        Map<Long, UserPreference> map = UserUtils.decodeUserPreference(user.getMyLikes());
        long likeVal = answer.getLikes();

        if (set && likeStatus == LikeStatus.NONE) {  // set
            map.get(answerId).setPreference(like);
            likeVal += (like ? 1 : -1);
        } else if (!set) {    // cancel
            if (like && likeStatus == LikeStatus.LIKE) {  // cancel like
                map.remove(answerId);
                --likeVal;
            } else if (!like && likeStatus == LikeStatus.DISLIKE) {    // cancel dislike
                map.remove(answerId);
                ++likeVal;
            } else {
                throw new AnswerApiIllegalArgumentException("Wrong `set` argument: excepting true, but got false");
            }
        } else {
            throw new AnswerApiIllegalArgumentException("Wrong `set` argument: excepting false, but got true");
        }

        answer.setLikes(likeVal);

        userRepository.save(user);
        answerRepository.save(answer);
    }

    @Override
    @Transactional(readOnly = true)
    public Answer getAnswerById(Long answerId) {
        return answerRepository.findByAnswerId(answerId).orElseThrow(AnswerNotFoundException::new);
    }

    @Override
    @Transactional
    public void deleteAnswerById(Long answerId) {
        commentRepository.deleteAllByAnswerId(answerId);
        answerRepository.deleteByAnswerId(answerId);
    }
}
