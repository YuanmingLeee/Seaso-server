package com.seaso.seaso.modules.question.web;

import com.seaso.seaso.modules.question.entity.Question;
import com.seaso.seaso.modules.question.service.QuestionService;
import com.seaso.seaso.modules.sys.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/questions")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public JsonResponse<List<Question>> findAllQuestions(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                                         @RequestParam(value = "sort_by", defaultValue = "questionId") String itemName) {
        List<Question> questions = questionService.findAllQuestions(page, size, Sort.by(itemName).descending());
        return new JsonResponse<>(questions);
    }

    @RequestMapping(value = "/{questionId}", method = RequestMethod.GET)
    public JsonResponse<Question> findQuestionById(@PathVariable Long questionId) {
        Question question = questionService.findQuestionById(questionId);
        return new JsonResponse<>(question);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public JsonResponse<String> postQuestion(@ModelAttribute Question question) {
        questionService.createQuestion(question);
        return new JsonResponse<>(null);
    }
}
