package com.seaso.seaso.modules.question.dao;

import com.seaso.seaso.modules.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, String> {

    Optional<Question> findByQuestionId(String questionId);

    void deleteByQuestionId(String questionId);
}
