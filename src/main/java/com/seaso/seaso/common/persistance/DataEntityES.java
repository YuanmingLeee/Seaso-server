package com.seaso.seaso.common.persistance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seaso.seaso.modules.sys.utils.UserUtils;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;


public abstract class DataEntityES<T> implements Serializable {

    @Field(type = FieldType.Long, index = false, store = true)
    protected Long creator;

    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss", timezone = "GMT+8")
    @JsonProperty(value = "create_date")
    @Field(type = FieldType.Date, store = true, format = DateFormat.date_optional_time)
    protected Date createDate;

    @JsonIgnore
    @Field(type = FieldType.Long, index = false, store = true)
    protected Long updater;

    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss", timezone = "GMT+8")
    @JsonProperty(value = "update_date")
    @Field(type = FieldType.Date, store = true, format = DateFormat.date_optional_time)
    protected Date updateDate;

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

    public void setNotPreUpdate() {
        this.notPreUpdate = true;
    }
}
