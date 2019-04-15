package com.seaso.seaso.common.persistance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seaso.seaso.modules.sys.utils.UserUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

public abstract class DataEntityES<T> implements Serializable {

    @JsonIgnore
    @Field(type = FieldType.Long, store = true, index = false)
    protected Long updater;
    @JsonIgnore
    @Id
    private String id;
    @Field(type = FieldType.Long, store = true, index = false)
    private Long creator;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT+8")
    @Field(type = FieldType.Date, store = true, format = DateFormat.date_hour_minute_second)
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT+8")
    @Field(type = FieldType.Date, store = true, format = DateFormat.date_hour_minute_second)
    private Date updateDate;

    @Transient
    private boolean notPreUpdate;

    public void preInsert() {
        Long userId = UserUtils.getCurrentUserId();
        setDataId();
        this.updater = this.creator = userId;
        this.updateDate = this.createDate = new Date();
    }

    public void preUpdate() {
        if (notPreUpdate)
            return;
        this.updater = UserUtils.getCurrentUserId();
        this.updateDate = new Date();
    }

    protected void setDataId() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreator() {
        return creator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Long getUpdater() {
        return updater;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setNotPreUpdate() {
        this.notPreUpdate = true;
    }
}
