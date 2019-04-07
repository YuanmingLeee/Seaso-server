package com.seaso.seaso.modules.question.web;

import com.seaso.seaso.modules.question.entity.Comment;
import com.seaso.seaso.modules.question.service.CommentService;
import com.seaso.seaso.modules.sys.utils.JsonResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(value = "/{commentId}", method = RequestMethod.GET)
    public JsonResponseBody<Comment> findByCommentId(@PathVariable Long commentId) {

        Comment comment = commentService.findByCommentId(commentId);
        return new JsonResponseBody<>(HttpStatus.OK, comment);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public JsonResponseBody<String> postComment(@ModelAttribute Comment comment) {

        commentService.createComment(comment);
        return new JsonResponseBody<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public JsonResponseBody<List<Comment>> getComment(@RequestParam(name = "answer_id") Long answerId,
                                                      @RequestParam(name = "reply_id", required = false) Long replyId,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      @RequestParam(defaultValue = "createDate") String itemName) {
        Sort sort = Sort.by(itemName).descending();
        if (replyId == null)
            return new JsonResponseBody<>(HttpStatus.OK, commentService.findByAnswerId(answerId, page, size, sort));
        return new JsonResponseBody<>(HttpStatus.OK, commentService.findByAnswerIdAndReplyId(
                answerId, replyId, page, size, sort));
    }

    @RequestMapping(value = "/{commentId}", method = RequestMethod.DELETE)
    public JsonResponseBody<String> deleteByCommentId(@PathVariable Long commentId) {

        commentService.deleteByCommentId(commentId);
        return new JsonResponseBody<>();
    }
}
