package com.seaso.seaso.modules.question.web;

import com.seaso.seaso.modules.question.entity.Comment;
import com.seaso.seaso.modules.question.service.CommentService;
import com.seaso.seaso.modules.sys.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    public JsonResponse<Comment> findByCommentId(@PathVariable String commentId) {

        Comment comment = commentService.findByCommentId(commentId);
        return new JsonResponse<>(comment);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public JsonResponse<String> postComment(@ModelAttribute Comment comment) {

        commentService.createComment(comment);
        return new JsonResponse<>(null);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public JsonResponse<List<Comment>> getComment(@RequestParam(name = "answer_id") String answerId,
                                                  @RequestParam(name = "reply_id", required = false) String replyId,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "createDate") String itemName) {
        Sort sort = Sort.by(itemName).descending();
        if (replyId == null)
            return new JsonResponse<>(commentService.findByAnswerId(answerId, page, size, sort));
        return new JsonResponse<>(commentService.findByAnswerIdAndReplyId(
                answerId, replyId, page, size, sort));
    }

    @RequestMapping(value = "/{commentId}", method = RequestMethod.DELETE)
    public JsonResponse<String> deleteByCommentId(@PathVariable String commentId) {

        commentService.deleteByCommentId(commentId);
        return new JsonResponse<>(null);
    }
}
