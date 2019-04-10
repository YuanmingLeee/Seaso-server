package com.seaso.seaso.modules.question.dao;

import com.seaso.seaso.modules.question.entity.Question;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, String>,ElasticsearchRepository<Question,String> {

    Optional<Question> findByQuestionId(Long questionId);

    void deleteByQuestionId(Long questionId);

}
