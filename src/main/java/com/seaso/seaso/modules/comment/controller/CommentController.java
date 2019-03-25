package com.seaso.seaso.modules.comment.controller;

import com.seaso.seaso.modules.comment.entity.Comment;
import com.seaso.seaso.modules.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
@RequestMapping(value = "/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(value = "/{commentId}", method = RequestMethod.GET)
    public Optional<Comment> findByCommentId(@PathVariable String commentId){

        return commentService.findByCommentId(commentId);

    }

    @RequestMapping(value = "/{commentId}", method = RequestMethod.DELETE)
    public void deleteByCommentId(@PathVariable String commentId){

        commentService.deleteByCommentId(commentId);

    }

    @RequestMapping(value ="/", method = RequestMethod.POST)
    public String postComment(@ModelAttribute Comment comment){

        commentService.createComment(comment);
        return "success";
    }

}
