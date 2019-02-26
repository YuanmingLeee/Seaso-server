package com.seaso.seaso.common.persistance;

import com.seaso.seaso.modules.sys.entity.User;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class DataEntity<T> {

    @ManyToOne
    @JoinColumn(name = "creator", referencedColumnName = "userId", nullable = false)
    protected User creator;

    @ManyToOne
    @JoinColumn(name = "updater", referencedColumnName = "userId", nullable = false)
    protected User updater;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    protected Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    protected Date updateDate;

    protected DataEntity() {}

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getUpdater() {
        return updater;
    }

    public void setUpdater(User updater) {
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
