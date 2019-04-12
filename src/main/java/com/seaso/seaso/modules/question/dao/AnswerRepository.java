package com.seaso.seaso.modules.question.dao;

import com.seaso.seaso.modules.question.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;


public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Page<Answer> findByQuestionId(Long questionId, Pageable pageable);

    Collection<Answer> findAllByQuestionId(Long questionId);

    Optional<Answer> findByAnswerId(Long answerId);

    boolean existsByAnswerId(Long answerId);

    boolean existsByAnswerIdAndCreator(Long answerId, Long creator);

    void deleteByAnswerId(Long answerId);

    void deleteAllByQuestionId(Long questionId);
}
