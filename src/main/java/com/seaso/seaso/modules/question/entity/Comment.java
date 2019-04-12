package com.seaso.seaso.modules.question.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seaso.seaso.common.persistance.DataEntity;
import com.seaso.seaso.common.utils.idgen.IdGen;

import javax.persistence.*;
import java.util.List;

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

    @Transient
    private static final long serialVersionUID = -6686228172563795386L;

    /**
     * Comment id
     */
    @JsonProperty("comment_id")
    @Column(nullable = false, length = 64)
    private Long commentId;

    /**
     * Answer id which this comment replies
     */
    @JsonProperty("answer_id")
    @Column(nullable = false, length = 64)
    private Long answerId;

    /**
     * Root comment Id. If the comment is root, this field should be put the self commend ID.
     */
    @JsonIgnore
    @Column(nullable = false, updatable = false, length = 64)
    private Long rootCommentId;

    /**
     * Replied comment id which this comment replies
     */
    @JsonProperty("reply_id")
    @Column(updatable = false, length = 64)
    private Long replyId;

    /**
     * Replies, set by service.
     */
    @Transient
    private List<Comment> replies;

    /**
     * Comment content
     */
    @Column(length = 240, nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isDelete = false;

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

    @Override
    public void preInsert() {
        super.preInsert();
        if (replyId == null)
            rootCommentId = commentId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Long getRootCommentId() {
        return rootCommentId;
    }

    public void setRootCommentId(Long rootCommentId) {
        this.rootCommentId = rootCommentId;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
