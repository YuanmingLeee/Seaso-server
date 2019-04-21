package com.seaso.seaso.modules.question.dao;

import com.seaso.seaso.modules.question.entity.Question;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends ElasticsearchRepository<Question, String> {

    Optional<Question> findByQuestionId(Long questionId);

    List<Question> findByQuestionIdIn(Collection<Long> questionIds);

    void deleteByQuestionId(Long questionId);
}
