package com.seaso.seaso.modules.question.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seaso.seaso.common.persistance.DataEntity;
import com.seaso.seaso.common.utils.idgen.IdGen;
import com.seaso.seaso.modules.question.utils.LikeStatus;
import com.seaso.seaso.modules.question.utils.QuestionUtils;
import com.seaso.seaso.modules.sys.utils.UserUtils;

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
    @Column(nullable = false, unique = true, length = 64)
    private Long answerId;

    /**
     * Question id which this answer id belongs to
     */
    @JsonProperty("question_id")
    @Column(nullable = false, updatable = false, length = 64)
    private Long questionId;

    /**
     * User like status to the answer
     */
    @JsonProperty("like_status")
    @Transient
    private LikeStatus likeStatus;

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
    @Column(length = 16777215)
    private String content;

    /**
     * Default constructor
     */
    public Answer() {
        super();
    }

    @Override
    protected void setDataId() {
        this.answerId = IdGen.generateId();
    }

    public Long getAnswerId() {
        return answerId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public LikeStatus getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(LikeStatus likeStatus) {
        this.likeStatus = likeStatus;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @PostLoad
    public void postLoad() {
        likeStatus = QuestionUtils.mapPreferenceMapsToLikeStatus(answerId,
                UserUtils.decodeUserPreference(UserUtils.getCurrentUser().getMyLikes()));
    }
}
