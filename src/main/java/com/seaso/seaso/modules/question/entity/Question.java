package com.seaso.seaso.modules.question.entity;

import com.seaso.seaso.common.persistance.DataEntityES;
import com.seaso.seaso.common.utils.idgen.IdGen;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Question Elasticsearch entity class is mapped to QUESTION document. It stores question posted.
 *
 * @author Yuanming Li
 * @version 0.2
 */
@Document(indexName = "test", type = "question")
public class Question extends DataEntityES<Question> {

    @Transient
    private static final long serialVersionUID = 8150616186831582842L;

    /**
     * Question id
     */
    @Id
    @Field(store = true)
    private String questionId;

    /**
     * Keywords for searching
     */
    @Field(type = FieldType.Keyword, index = false, store = true)
    private String keywords = "";

    /**
     * Question title
     */
    @Field(type = FieldType.Text, store = true)
    private String title = "";

    /**
     * Question description(subtitle)
     */
    @Field(type = FieldType.Keyword, index = false, store = true)
    private String description;

    /**
     * Question cover image
     */
    private byte[] cover;

    /**
     * Question detailed content
     */
    @Field(type = FieldType.Text, store = true)
    private String content;

    /**
     * Default constructor
     */
    public Question() {
        super();
    }

    @Override
    protected void setDataId() {
        questionId = IdGen.generateId().toString();
    }

    public Long getQuestionId() {
        return Long.parseLong(questionId);
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
