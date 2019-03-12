package com.seaso.seaso.modules.comment.entity;

import com.seaso.seaso.common.persistance.DataEntity;
import com.seaso.seaso.modules.answer.entity.Answer;

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

    /**
     * Comment id
     */
    @Column(nullable = false, unique = true, length = 16)
    private String commentId;

    /**
     * Answer id which this comment replies
     */
    @ManyToOne
    @JoinColumn(name = "answerId", referencedColumnName = "answerId", nullable = false,
            foreignKey = @ForeignKey(name = "comment_answer_answer_id_fk"))
    private Answer answer;

    /**
     * Replied comment id which this comment replies
     */
    @ManyToOne
    @JoinColumn(name = "replyId", referencedColumnName = "commentId",
            foreignKey = @ForeignKey(name = "comment_comment_id_fk"))
    private Comment replyId;

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

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Comment getReplyId() {
        return replyId;
    }

    public void setReplyId(Comment replyId) {
        this.replyId = replyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
