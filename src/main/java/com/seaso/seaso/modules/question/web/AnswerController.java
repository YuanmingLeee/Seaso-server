package com.seaso.seaso.modules.question.web;

import com.seaso.seaso.modules.question.entity.Answer;
import com.seaso.seaso.modules.question.service.AnswerService;
import com.seaso.seaso.modules.sys.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    public JsonResponse<List<Answer>> getAnswerByQuestionId(@RequestParam(value = "question_id") Long questionId,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size,
                                                            @RequestParam(defaultValue = "likes") String itemName) {
        List<Answer> answers = answerService.getAnswersByQuestionId(questionId, page, size,
                Sort.by(itemName).descending());

        return new JsonResponse<>(answers);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public JsonResponse<String> createAnswer(@ModelAttribute Answer answer) {
        answerService.createAnswer(answer);
        return new JsonResponse<>(null);
    }

    @RequestMapping(value = "/{answerId}", method = RequestMethod.GET)
    public JsonResponse<Answer> getAnswerById(@PathVariable Long answerId) {
        Answer answer = answerService.getAnswerById(answerId);
        return new JsonResponse<>(answer);
    }

    @RequestMapping(value = "/{answerId}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse<String> likeAnswerById(@PathVariable Long answerId,
                                               @RequestParam boolean like,
                                               @RequestParam boolean set) {
        if (like)
            answerService.likeAnswerById(answerId, set);
        else
            answerService.dislikeAnswerById(answerId, set);
        return new JsonResponse<>(null);
    }

    /* Bug found here: fk dependency on reply_id, comment */
    @RequestMapping(value = "/{answerId}", method = RequestMethod.DELETE)
    public JsonResponse<String> deleteAnswerById(@PathVariable Long answerId) {
        answerService.deleteAnswerById(answerId);
        return new JsonResponse<>(null);
    }
}
