package com.seaso.seaso.modules.sys.entity;

import com.seaso.seaso.common.persistance.DataEntity;
import com.seaso.seaso.modules.sys.utils.RoleType;
import org.springframework.data.annotation.Persistent;

import javax.persistence.*;

@Entity
@Table(name = "sys_role")
public class Role extends DataEntity<Role> {

    @Persistent
    private static final long serialVersionUID = -1835818699458902220L;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private SystemUser systemUser;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    public Role() {
        super();
    }

    public SystemUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
}
