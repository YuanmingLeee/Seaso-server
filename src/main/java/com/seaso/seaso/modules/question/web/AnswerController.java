package com.seaso.seaso.modules.question.web;

import com.seaso.seaso.modules.question.entity.Answer;
import com.seaso.seaso.modules.question.service.AnswerService;
import com.seaso.seaso.modules.sys.utils.JsonResponseBody;
import com.seaso.seaso.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/answers")
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getAnswerByQuestionId(@RequestParam(value = "question_id") Long questionId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(defaultValue = "likes") String itemName) {
        Page<Answer> answers = answerService.findAnswersByQuestionId(questionId, page, size,
                Sort.by(itemName).descending());

        return new ResponseEntity<>(new JsonResponseBody<>(HttpStatus.OK, answers), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createAnswer(@ModelAttribute Answer answer) {
        answerService.createAnswer(answer);
        return new ResponseEntity<>(new JsonResponseBody<>(HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{answerId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAnswerById(@PathVariable Long answerId) {
        Answer answer = answerService.findAnswerById(answerId);
        return new ResponseEntity<>(new JsonResponseBody<>(HttpStatus.OK, answer), HttpStatus.OK);
    }

    @RequestMapping(value = "/{answerId}", method = RequestMethod.POST)
    public ResponseEntity<?> likeAnswerById(@PathVariable Long answerId,
                                            @RequestParam boolean like,
                                            @RequestParam boolean set) {
        if (like)
            answerService.likeAnswerById(answerId, set);
        else
            answerService.dislikeAnswerById(answerId, set);
        return new ResponseEntity<>(new JsonResponseBody<>(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{answerId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateAnswer(@ModelAttribute Answer answer,
                                          @PathVariable Long answerId) {
        answerService.updateAnswerByIdAndCreator(answerId, UserUtils.getCurrentUserId(), answer);
        return new ResponseEntity<>(new JsonResponseBody<>(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{answerId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSelfAnswerById(@PathVariable Long answerId) {
        answerService.deleteAnswerByIdAndCreator(answerId, UserUtils.getCurrentUserId());
        return new ResponseEntity<>(new JsonResponseBody<>(), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/{answerId}", method = RequestMethod.DELETE)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> deleteAnswerById(@PathVariable Long answerId) {
        answerService.deleteAnswerById(answerId);
        return new ResponseEntity<>(new JsonResponseBody<>(), HttpStatus.OK);
    }
}
