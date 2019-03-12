package com.seaso.seaso.common.persistance;

import com.seaso.seaso.modules.sys.utils.UserUtils;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class DataEntity<T> {

    @Column(nullable = false)
    protected String creator;
    @Column(nullable = false)
    protected String updater;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    protected Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    protected Date updateDate;

    protected DataEntity() {
    }

    public void preInsert() {
        String userId = UserUtils.getUserId();
        this.updater = this.creator = userId;
        this.updateDate = this.createDate = new Date();
    }

    public void preUpdate() {
        this.updater = UserUtils.getUserId();
        this.updateDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
