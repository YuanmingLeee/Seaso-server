package com.seaso.seaso.modules.question.dao;

import com.seaso.seaso.modules.question.entity.Question;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface QuestionRepository extends ElasticsearchRepository<Question, String> {

    Optional<Question> findByQuestionId(String questionId);

    boolean existsByQuestionId(String questionId);

    void deleteByQuestionId(String questionId);
}
