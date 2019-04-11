package com.seaso.seaso.modules.question.service;

import com.seaso.seaso.modules.question.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface AnswerService {

    void createAnswer(Answer answer);

    Page<Answer> getAnswersByQuestionId(Long questionId, int page, int size, Sort sort);

    /**
     * Like or cancel like an answer given answer ID. Exception will be raised if the user set/unset like to an answer
     * he has set/unset before. This method is synchronized for update.
     *
     * @param answerId answer ID.
     * @param set      {@code true} for set like, {@code false} for cancel like.
     */
    void likeAnswerById(Long answerId, boolean set);

    /**
     * Dislike or cancel dislike an answer given answer ID. Exception will be raised if the user set/unset dislike to an
     * answer he has set/unset before. This method is synchronized for update.
     *
     * @param answerId answer ID.
     * @param set {@code true} for set dislike, {@code false} for cancel dislike.
     */
    void dislikeAnswerById(Long answerId, boolean set);

    Answer getAnswerById(Long answerId);

    void deleteAnswerById(Long answerId);
}
