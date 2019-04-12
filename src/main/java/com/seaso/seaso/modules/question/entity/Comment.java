package com.seaso.seaso.modules.question.entity;

import com.seaso.seaso.common.persistance.DataEntity;
import com.seaso.seaso.common.utils.idgen.IdGen;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * Comment Entity class is mapped to COMMENT table.
 *
 * @author Yuanming Li
 * @version 0.1
 */
@Entity
@Table(name = "comment",
        indexes = {@Index(name = "comment_comment_id_uindex", columnList = "commentId", unique = true)})
public class Comment extends DataEntity<Comment> {

    private static final long serialVersionUID = -6686228172563795386L;

    /**
     * Comment id
     */
    @Column(nullable = false, length = 64)
    private Long commentId;

    /**
     * Answer id which this comment replies
     */
    @Column(nullable = false, length = 64)
    private Long answerId;

    /**
     * Replied comment id which this comment replies
     */
    @Column(length = 64)
    private Long replyId;

    /**
     * Comment content
     */
    @Column(length = 240)
    private String content = "";

    /**
     * Default constructor
     */
    public Comment() {
        super();
    }

    @Override
    protected void setDataId() {
        commentId = IdGen.generateId();
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
