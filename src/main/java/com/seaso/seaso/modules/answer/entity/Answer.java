package com.seaso.seaso.modules.answer.entity;

import com.seaso.seaso.common.persistance.DataEntity;

import javax.persistence.*;

/**
 * Answer Entity class is mapped to ANSWER table. It stores answer posted.
 *
 * @author Yuanming Li
 * @version 0.1
 */
@Entity
@Table(name = "answer",
        indexes = {@Index(name = "answer_answer_id_uindex", columnList = "answerId", unique = true)})
public class Answer extends DataEntity<Answer> {

    private static final long serialVersionUID = 7500358540229630981L;
    /**
     * Answer id
     */
    @Column(nullable = false, unique = true, length = 32)
    private String answerId;

    /**
     * Question id which this answer id belongs to
     */
    @Column(nullable = false, length = 32)
    private String questionId;

    /**
     * Number of likes
     */
    @Column
    private Long likes = 0L;

    /**
     * Number of dislikes
     */
    @Column
    private Long dislikes = 0L;

    /**
     * Answer description (the first few characters)
     */
    @Column(length = 100)
    private String description = "";

    /**
     * Answer cover image
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column
    private byte[] cover;

    /**
     * Answer detailed content
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 16777215)
    private byte[] content;

    /**
     * Default constructor
     */
    public Answer() {
        super();
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getDislikes() {
        return dislikes;
    }

    public void setDislikes(Long dislikes) {
        this.dislikes = dislikes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
