package com.seaso.seaso.modules.question.controller;

import com.seaso.seaso.modules.question.entity.Answer;
import com.seaso.seaso.modules.question.entity.Comment;
import com.seaso.seaso.modules.question.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/answers")
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @RequestMapping(value = "/{answerId}/comment", method = RequestMethod.GET)
    public Page<Comment> getCommentsByAnswerId(@PathVariable String answerId){

        return null;
        //implementation to be added here
    }

    @RequestMapping(value = "/{questionId}/answer", method = RequestMethod.GET)
    public Page<Comment> getAnswerByQuestionId(@PathVariable String questionId){

        return null;
        //implementation to be added here
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String createAnswer (@ModelAttribute Answer answer){

        answerService.createAnswer(answer);
        return "success";
    }

    @RequestMapping(value = "/{answerId}/like", method = RequestMethod.PATCH)
    public String likeQuestion (@PathVariable String answerId){

        answerService.likeAnswerById(answerId);
        return "success";
    }

    @RequestMapping(value = "/{answerId}/dislike", method = RequestMethod.PATCH)
    public String dislikeQuestion (@PathVariable String answerId){

        answerService.dislikeAnswerById(answerId);
        return "success";
    }

}

