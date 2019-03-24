package com.seaso.seaso.modules.question.controller;

import com.seaso.seaso.modules.question.entity.Question;
import com.seaso.seaso.modules.question.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(value = "/{questionId}", method = RequestMethod.GET)
    public Optional<Question> findQuestionById(@PathVariable String questionId){

        return questionService.findQuestionById(questionId);

    }

    @RequestMapping(value ="/", method = RequestMethod.POST)
    public String postQuestion (@ModelAttribute Question question){
        questionService.createQuestion(question);
        return"success";
    }
}
