package com.seaso.seaso.modules.question.entity;

import com.seaso.seaso.common.persistance.DataEntity;
import com.seaso.seaso.modules.sys.utils.UserUtils;

import javax.persistence.*;

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
    @Column(nullable = false, unique = true, length = 64)
    private Long commentId;

    /**
     * Answer id which this comment replies
     */
    @Column(nullable = false, length = 32)
    private String answerId;

    /**
     * Replied comment id which this comment replies
     */
    @Column(length = 32)
    private String replyId;

    /**
     * Comment content
     */
    @Basic(fetch = FetchType.LAZY)
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
        commentId = UserUtils.idGenerate(1).get(0);
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
