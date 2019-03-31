package com.seaso.seaso.modules.question.entity;

import com.seaso.seaso.common.persistance.DataEntity;
import com.seaso.seaso.common.utils.idgen.IdGen;

import javax.persistence.*;

/**
 * Question entity class is mapped to QUESTION table. It stores question posted.
 *
 * @author Yuanming Li
 * @version 0.1
 */
@Entity
@Table(name = "question",
        indexes = {@Index(name = "question_question_id_uindex", columnList = "questionId", unique = true)})
public class Question extends DataEntity<Question> {

    private static final long serialVersionUID = 8150616186831582842L;
    /**
     * Question id
     */
    @Column(nullable = false, unique = true, length = 64)
    private Long questionId;

    /**
     * Keywords for searching
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 16777215)
    private String keywords = "";

    /**
     * Question title
     */
    @Column(length = 40)
    private String title = "";

    /**
     * Question description(subtitle)
     */
    @Column(length = 100)
    private String description;

    /**
     * Question cover image
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column
    private byte[] cover;

    /**
     * Question detailed content
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 16777215)
    private byte[] content;

    /**
     * Number of views
     */
    @Column
    private Long views = 0L;

    /**
     * Default constructor
     */
    public Question() {
        super();
    }

    @Override
    protected void setDataId() {
        questionId = IdGen.generateId();
    }

    public Long getQuestion_id() {
        return questionId;
    }

    public void setQuestion_id(Long questionId) {
        this.questionId = questionId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }
}
