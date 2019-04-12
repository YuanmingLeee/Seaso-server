package com.seaso.seaso.common.persistance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seaso.seaso.common.exception.ServiceException;
import com.seaso.seaso.modules.sys.utils.UserUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

/**
 * Base class of all Data entities.
 *
 * @param <T> type of the child entity
 * @author Li Yuanming
 * @version 0.2
 */
@MappedSuperclass
public abstract class DataEntity<T> implements Serializable {

    @Transient
    private static final long serialVersionUID = 1735325270419412291L;

    /**
     * Entity owner.
     */
    @Column(nullable = false, length = 32)
    protected Long creator;
    /**
     * Entity updater.
     */
    @JsonIgnore
    @Column(nullable = false, length = 32)
    protected Long updater;
    /**
     * Entity create date.
     */
    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    @JsonProperty(value = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    protected Date createDate;
    /**
     * Entity update date.
     */
    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    @JsonProperty(value = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    protected Date updateDate;
    /**
     * Flag to mark if the entity not to be invoked in pre update.
     */
    @Transient
    private boolean notPreUpdate;
    /**
     * Primary key for persistence.
     */
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected DataEntity() {
    }

    /**
     * This method will be invoked before an new entity is persisted. It sets the auxiliary information about the
     * entity.
     */
    @PrePersist
    public void preInsert() {
        Long userId = UserUtils.getCurrentUserId();
        setDataId();
        this.updater = this.creator = userId;
        this.updateDate = this.createDate = new Date();
    }

    /**
     * This method will be invoked before an entity is updated.
     */
    @PreUpdate
    public void preUpdate() {
        if (notPreUpdate)
            return;
        this.updater = UserUtils.getCurrentUserId();
        this.updateDate = new Date();
    }

    /**
     * Force merge two entities. It overrides all not-null and non-static fields by the passed in object.
     * {@link ServiceException} will be raised if the passed in object cannot be force merged.
     *
     * @param obj the patch object. All of its not null fields will override the message receiver.
     * @throws ServiceException thrown if two object cannot be force merged.
     */
    public void merge(T obj) throws ServiceException {
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(obj) != null && !Modifier.isStatic(field.getModifiers()))
                    field.set(this, field.get(obj));
            }
        } catch (IllegalAccessException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    /**
     * This method is used to set the natural ID of the entity. It will be invoked by {@link #preInsert()} if an entity
     * is about to persist. You may want to override this method to set the specific data ID by some ID generators.
     *
     * @see #preInsert()
     */
    protected void setDataId() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNotPreUpdate() {
        this.notPreUpdate = true;
    }
}
