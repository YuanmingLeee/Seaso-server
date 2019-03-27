package com.seaso.seaso.modules.question.service.impl;

import com.seaso.seaso.modules.question.dao.AnswerRepository;
import com.seaso.seaso.modules.question.entity.Answer;
import com.seaso.seaso.modules.question.exception.AnswerApiIllegalArgumentException;
import com.seaso.seaso.modules.question.exception.AnswerNotFoundException;
import com.seaso.seaso.modules.question.service.AnswerService;
import com.seaso.seaso.modules.question.utils.QuestionUtils;
import com.seaso.seaso.modules.sys.dao.UserRepository;
import com.seaso.seaso.modules.sys.entity.User;
import com.seaso.seaso.modules.sys.exception.UserNotFoundException;
import com.seaso.seaso.modules.sys.utils.UserUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository, UserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createAnswer(Answer answer) {
        answer.preInsert();
        answerRepository.save(answer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Answer> getAnswersByQuestionId(String questionId, int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        List<Answer> answers = answerRepository.getByQuestionId(questionId, pageable).getContent();
        User user = userRepository.findByUsername(UserUtils.getUserId()).orElse(new User());

        // obtain user answer preference maps
        Map<String, Date> likeMap = UserUtils.decodeUserAnswerPreference(user.getMyLikes());
        Map<String, Date> dislikeMap = UserUtils.decodeUserAnswerPreference(user.getMyDislikes());

        // set like status for each answers
        answers.forEach(answer ->
                answer.setLikeStatus(QuestionUtils.mapPreferenceMapsToLikeStatus(
                        answer.getAnswerId(), likeMap, dislikeMap))
        );

        return answers;
    }

    @Override
    @Transactional
    public void likeAnswerById(String answerId, boolean set) {
        Answer answer = answerRepository.getByAnswerId(answerId).orElseThrow(AnswerNotFoundException::new);
        User user = userRepository.findByUsername(UserUtils.getUserId()).orElseThrow(UserNotFoundException::new);

        Map<String, Date> map = UserUtils.decodeUserAnswerPreference(user.getMyLikes());

        // check set argument
        if (set == map.containsKey(answerId))
            throw new AnswerApiIllegalArgumentException("Wrong set argument: excepting " + !set + ", but got " + set);

        // update answer entity
        answer.setLikes(answer.getLikes() + (set ? 1 : -1));

        // common tail for like/dislike
        preferenceAnswerId(answer, user, map, set);

        user.setMyLikes(UserUtils.encodeUserAnswerPreference(map));
        answerRepository.save(answer);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void dislikeAnswerById(String answerId, boolean set) {
        Answer answer = answerRepository.getByAnswerId(answerId).orElseThrow(AnswerNotFoundException::new);
        User user = userRepository.findByUsername(UserUtils.getUserId()).orElseThrow(UserNotFoundException::new);

        Map<String, Date> map = UserUtils.decodeUserAnswerPreference(user.getMyDislikes());

        // check set argument
        if (set == map.containsKey(answerId))
            throw new AnswerApiIllegalArgumentException("Wrong set argument: excepting " + !set + ", but got " + set);

        // update answer entity
        answer.setDislikes(answer.getDislikes() + (set ? 1 : -1));

        // common tail for like/dislike
        preferenceAnswerId(answer, user, map, set);

        user.setMyDislikes(UserUtils.encodeUserAnswerPreference(map));
        answerRepository.save(answer);
        userRepository.save(user);
    }


    private void preferenceAnswerId(@NotNull Answer answer, @NotNull User user, @NotNull Map<String, Date> map,
                                    boolean set) {
        String answerId = answer.getAnswerId();
        answer.preUpdate();

        // update user entity
        if (set)
            map.put(answerId, new Date());
        else
            map.remove(answerId);
        user.preUpdate();
    }

    @Override
    public Answer getAnswerById(String answerId) {
        Answer answer = answerRepository.getByAnswerId(answerId).orElseThrow(AnswerNotFoundException::new);
        User user = userRepository.findByUsername(UserUtils.getUserId()).orElse(new User());

        // obtain user answer preference maps
        Map<String, Date> likeMap = UserUtils.decodeUserAnswerPreference(user.getMyLikes());
        Map<String, Date> dislikeMap = UserUtils.decodeUserAnswerPreference(user.getMyDislikes());

        // set like status for answer
        answer.setLikeStatus(
                QuestionUtils.mapPreferenceMapsToLikeStatus(answer.getAnswerId(), likeMap, dislikeMap)
        );

        return answer;
    }

    @Override
    @Transactional
    public void deleteAnswerById(String answerId) {
        answerRepository.getByAnswerId(answerId).orElseThrow(AnswerNotFoundException::new);
        answerRepository.deleteByAnswerId(answerId);
    }
}
