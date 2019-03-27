package com.seaso.seaso.modules.question.dao;

import com.seaso.seaso.modules.question.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Page<Answer> getByQuestionId(String questionId, Pageable pageable);

    Optional<Answer> getByAnswerId(String answerId);

    void deleteByAnswerId(String answerId);
}
