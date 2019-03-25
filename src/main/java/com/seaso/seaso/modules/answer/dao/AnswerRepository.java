package com.seaso.seaso.modules.answer.dao;

import com.seaso.seaso.modules.answer.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Page<Answer> getByQuestionId(String questionId, Pageable pageable);

    Optional<Answer> getByAnswerId(String answerId);

}
