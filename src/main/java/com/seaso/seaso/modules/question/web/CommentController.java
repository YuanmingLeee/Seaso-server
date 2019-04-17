package com.seaso.seaso.modules.question.web;

import com.seaso.seaso.common.exception.ApiIllegalArgumentException;
import com.seaso.seaso.modules.question.entity.Comment;
import com.seaso.seaso.modules.question.service.CommentService;
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
@RequestMapping(value = "/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Find commend by comment ID.
     *
     * @param commentId comment ID.
     * @return an OK {@link JsonResponseBody} wrapped by a {@link ResponseEntity} if the comment is found.
     */
    @RequestMapping(value = "/{commentId}", method = RequestMethod.GET)
    public ResponseEntity<?> findByCommentId(@PathVariable Long commentId) {

        Comment comment = commentService.findByCommentId(commentId);
        return new ResponseEntity<>(new JsonResponseBody<>(20, comment), HttpStatus.OK);
    }

    /**
     * Post a comment.
     *
     * @param comment comment entity.
     * @return an CREATED {@link JsonResponseBody} wrapped by a {@link ResponseEntity} if the comment is post
     * successfully.
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> postComment(@ModelAttribute Comment comment) {

        commentService.createComment(comment);
        return new ResponseEntity<>(new JsonResponseBody<>(201), HttpStatus.CREATED);
    }

    /**
     * Get comments or replies given answer ID or reply ID. Noted that answer ID and reply ID can only be provided one.
     * An {@link ApiIllegalArgumentException} will be raised if two or none of them are set.
     *
     * @param answerId answer ID.
     * @param replyId  reply ID.
     * @param page     page number, starts with 0.
     * @param size     page size.
     * @param itemName item for sorting.
     * @return the paged comments or replies wrapped by {@link JsonResponseBody} and then wrapped
     * by {@link ResponseEntity}.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getComment(@RequestParam(name = "answer_id", required = false) Long answerId,
                                        @RequestParam(name = "reply_id", required = false) Long replyId,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "createDate") String itemName) {
        if ((answerId == null) == (replyId == null))
            throw new ApiIllegalArgumentException("Parameter `answer_id` and `reply_id` should be set only one.");

        Sort sort = Sort.by(itemName).descending();
        if (replyId == null) {    // get comment
            Page<Comment> comments = commentService.findByAnswerId(answerId, page, size, sort);
            return new ResponseEntity<>(new JsonResponseBody<>(200, comments), HttpStatus.OK);
        }

        // get replies
        Page<Comment> replies = commentService.findByRootCommentId(replyId, page, size, sort);
        return new ResponseEntity<>(new JsonResponseBody<>(200, replies), HttpStatus.OK);
    }

    /**
     * Delete by comment ID. This method is limited by creator. User can only delete the question that created by
     * himself.
     *
     * @param commentId comment ID.
     * @return an OK {@link JsonResponseBody} wrapped by {@link ResponseEntity} if the delete is success.
     */
    @RequestMapping(value = "/{commentId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSelfByCommentId(@PathVariable Long commentId) {
        commentService.deleteByCommentIdAndCreator(commentId, UserUtils.getCurrentUserId());
        return new ResponseEntity<>(new JsonResponseBody(), HttpStatus.OK);
    }

    /**
     * Delete by comment ID. This method is limited to admin.
     *
     * @param commentId comment ID.
     * @return an OK {@link JsonResponseBody} wrapped by {@link ResponseEntity} if the delete is success.
     */
    @RequestMapping(value = "/admin/{commentId}", method = RequestMethod.DELETE)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> deleteByCommentId(@PathVariable Long commentId) {
        commentService.deleteByCommentId(commentId);
        return new ResponseEntity<>(new JsonResponseBody(), HttpStatus.OK);
    }
}
