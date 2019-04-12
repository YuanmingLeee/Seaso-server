package com.seaso.seaso.modules.question.service.impl;

import com.seaso.seaso.common.exception.ApiIllegalArgumentException;
import com.seaso.seaso.common.exception.ResourceNotFoundException;
import com.seaso.seaso.modules.question.dao.AnswerRepository;
import com.seaso.seaso.modules.question.dao.CommentRepository;
import com.seaso.seaso.modules.question.entity.Comment;
import com.seaso.seaso.modules.question.exception.CommentApiIllegalArgumentException;
import com.seaso.seaso.modules.question.exception.CommentNotFoundException;
import com.seaso.seaso.modules.question.service.CommentService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Comment findByCommentId(Long commentId) {
        return commentRepository.findByCommentId(commentId).orElseThrow(CommentNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Comment> findByAnswerId(Long answerId, int page, int size, Sort sort) {
        if (!answerRepository.existsAnswerByAnswerId(answerId))
            throw new ResourceNotFoundException("answer ID not found");

        Pageable pageable = PageRequest.of(page, size, sort);
        Pageable commentPageable = PageRequest.of(0, 10, Sort.by("createDate").descending());

        Page<Comment> comments = commentRepository.findByAnswerIdAndReplyIdIsNull(answerId, pageable);

        comments.getContent().forEach(comment ->
                comment.setReplies(
                        commentRepository.findByRootCommentIdAndReplyIdNotNull(comment.getCommentId(), commentPageable)
                                .getContent()));
        return comments;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Comment> findByRootCommentId(Long rootCommentId, int page, int size, Sort sort) {
        if (!commentRepository.existsByCommentId(rootCommentId))
            throw new ResourceNotFoundException("root comment ID not found");

        Pageable pageable = PageRequest.of(page, size, sort);
        return commentRepository.findByRootCommentIdAndReplyIdNotNull(rootCommentId, pageable);
    }

    /**
     * Delete by commend ID. The comment is not really deleted, only the content is removed.
     *
     * @param commentId answer ID.
     */
    @Override
    @Transactional
    public void deleteByCommentId(Long commentId) {
        Comment comment = commentRepository.findByCommentId(commentId).orElseThrow(CommentNotFoundException::new);
        abstractedDeleteByCommentId(comment);
    }

    /**
     * Delete by comment ID. The comment is filtered given creator.
     *
     * @param commentId comment ID.
     * @param creator   creator.
     */
    @Override
    @Transactional
    public void deleteByCommentIdAndCreator(Long commentId, Long creator) {
        Comment comment = commentRepository.findByCommentIdAndCreator(commentId, creator)
                .orElseThrow(ResourceNotFoundException::new);
        abstractedDeleteByCommentId(comment);
    }

    /**
     * Abstracted delete comment method, only used in {@link #deleteByCommentId(Long)} and
     * {@link #deleteByCommentIdAndCreator(Long, Long)}
     *
     * @param comment not null {@link Comment} entity.
     */
    private void abstractedDeleteByCommentId(@NotNull Comment comment) {
        if (comment.isDelete())
            throw new ApiIllegalArgumentException("Delete comment ID has been removed.");
        comment.setContent("");
        comment.setDelete(true);
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void createComment(Comment comment) {
        if (!answerRepository.existsAnswerByAnswerId(comment.getAnswerId()))
            throw new ResourceNotFoundException("Answer ID not found.");

        if (comment.getReplyId() != null) {
            Comment comment1 = commentRepository.findByCommentId(comment.getReplyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Replied comment ID not found."));
            if (comment1.isDelete())
                throw new ApiIllegalArgumentException("Replied comment has been deleted.");

            if (!comment1.getAnswerId().equals(comment.getAnswerId()))
                throw new CommentApiIllegalArgumentException(
                        "Answer id is not matched with the replied comment's answer id");

            comment.setRootCommentId(comment1.getRootCommentId());
        }
        commentRepository.save(comment);
    }
}
