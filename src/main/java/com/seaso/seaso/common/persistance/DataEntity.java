package com.seaso.seaso.common.persistance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seaso.seaso.common.exception.ServiceException;
import com.seaso.seaso.modules.sys.utils.UserUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

@MappedSuperclass
public abstract class DataEntity<T> implements Serializable {

    @Transient
    private static final long serialVersionUID = 1735325270419412291L;

    @Transient
    private boolean isNewRecord = true;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private Long creator;

    @Column(nullable = false, length = 32)
    private Long updater;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updateDate;

    protected DataEntity() {
    }

    @PrePersist
    public void preInsert() {
        if (isNewRecord) {
            Long userId = UserUtils.getCurrentUserId();
            setDataId();
            this.updater = this.creator = userId;
            this.updateDate = this.createDate = new Date();
            isNewRecord = false;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updater = UserUtils.getCurrentUserId();
        this.updateDate = new Date();
    }

    @PostLoad
    public void postLoad() {
        isNewRecord = false;
    }

    public void merge(T obj) throws ServiceException {
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(obj) != null)
                    field.set(this, field.get(obj));
            }
        } catch (IllegalAccessException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    protected void setDataId() {
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    @JsonIgnore
    public Long getUpdater() {
        return updater;
    }

    public void setUpdater(Long updater) {
        this.updater = updater;
    }

    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    @JsonProperty(value = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    @JsonProperty(value = "update_date")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
