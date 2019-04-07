package com.seaso.seaso.modules.question.web;

import com.seaso.seaso.modules.question.entity.Answer;
import com.seaso.seaso.modules.question.service.AnswerService;
import com.seaso.seaso.modules.sys.utils.JsonResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/answers")
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public JsonResponseBody<List<Answer>> getAnswerByQuestionId(@RequestParam(value = "question_id") Long questionId,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size,
                                                                @RequestParam(defaultValue = "likes") String itemName) {
        List<Answer> answers = answerService.getAnswersByQuestionId(questionId, page, size,
                Sort.by(itemName).descending());

        return new JsonResponseBody<>(HttpStatus.OK, answers);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public JsonResponseBody<String> createAnswer(@ModelAttribute Answer answer) {
        answerService.createAnswer(answer);
        return new JsonResponseBody<>(null);
    }

    @RequestMapping(value = "/{answerId}", method = RequestMethod.GET)
    public JsonResponseBody<Answer> getAnswerById(@PathVariable Long answerId) {
        Answer answer = answerService.getAnswerById(answerId);
        return new JsonResponseBody<>(HttpStatus.OK, answer);
    }

    @RequestMapping(value = "/{answerId}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseBody<String> likeAnswerById(@PathVariable Long answerId,
                                                   @RequestParam boolean like,
                                                   @RequestParam boolean set) {
        if (like)
            answerService.likeAnswerById(answerId, set);
        else
            answerService.dislikeAnswerById(answerId, set);
        return new JsonResponseBody<>();
    }

    /* Bug found here: fk dependency on reply_id, comment */
    @RequestMapping(value = "/{answerId}", method = RequestMethod.DELETE)
    public JsonResponseBody<String> deleteAnswerById(@PathVariable Long answerId) {
        answerService.deleteAnswerById(answerId);
        return new JsonResponseBody<>();
    }
}
