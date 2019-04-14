package com.seaso.seaso.modules.question.dao;

import com.seaso.seaso.modules.question.entity.Question;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface QuestionRepository extends ElasticsearchRepository<Question, String> {

    Optional<Question> findByQuestionId(Long questionId);

    boolean existsByQuestionId(Long questionId);

    void deleteByQuestionId(Long questionId);
}
