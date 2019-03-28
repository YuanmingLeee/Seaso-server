package com.seaso.seaso.modules.question.service.impl;

import com.seaso.seaso.modules.question.dao.AnswerRepository;
import com.seaso.seaso.modules.question.dao.CommentRepository;
import com.seaso.seaso.modules.question.entity.Comment;
import com.seaso.seaso.modules.question.exception.AnswerNotFoundException;
import com.seaso.seaso.modules.question.exception.CommentApiIllegalArgumentException;
import com.seaso.seaso.modules.question.exception.CommentNotFoundException;
import com.seaso.seaso.modules.question.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final AnswerRepository answerRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, AnswerRepository answerRepository) {
        this.commentRepository = commentRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public Comment findByCommentId(String commentId) {
        return commentRepository.findByCommentId(commentId).orElseThrow(CommentNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findByAnswerId(String answerId, int page, int size, Sort sort) {
        answerRepository.findByAnswerId(answerId).orElseThrow(AnswerNotFoundException::new);
        Pageable pageable = PageRequest.of(page, size, sort);
        return commentRepository.findByAnswerId(answerId, pageable).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findByAnswerIdAndReplyId(String answerId, String replyId, int page, int size, Sort sort) {
        answerRepository.findByAnswerId(answerId).orElseThrow(AnswerNotFoundException::new);
        commentRepository.findByCommentId(replyId).orElseThrow(CommentNotFoundException::new);
        Pageable pageable = PageRequest.of(page, size, sort);
        return commentRepository.findByAnswerIdAndReplyId(answerId, replyId, pageable).getContent();
    }

    @Override
    @Transactional
    public void deleteByCommentId(String commentId) {
        commentRepository.findByCommentId(commentId).orElseThrow(CommentNotFoundException::new);
        commentRepository.deleteAllByReplyId(commentId);
        commentRepository.deleteByCommentId(commentId);
    }

    @Override
    @Transactional
    public void createComment(Comment comment) {
        answerRepository.findByAnswerId(comment.getAnswerId()).orElseThrow(AnswerNotFoundException::new);
        if (comment.getReplyId() != null) {
            Comment comment1 = commentRepository.findByCommentId(comment.getReplyId())
                    .orElseThrow(CommentNotFoundException::new);
            if (!comment1.getAnswerId().equals(comment.getAnswerId()))
                throw new CommentApiIllegalArgumentException(
                        "Answer id is not matched with the replied comment's answer id");
        }
        comment.preInsert();
        commentRepository.save(comment);
    }


}
