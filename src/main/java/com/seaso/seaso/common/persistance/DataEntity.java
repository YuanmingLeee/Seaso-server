package com.seaso.seaso.common.persistance;

import com.seaso.seaso.modules.user.entity.User;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class DataEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "data_entity_user_user_id_fk"),
            name = "creator", referencedColumnName = "userId")
    protected User creator;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "data_entity_user_user_id_fk2"),
            name = "updater", referencedColumnName = "userId")
    protected User updater;

    @Column
    protected Date createDate;

    @Column
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
