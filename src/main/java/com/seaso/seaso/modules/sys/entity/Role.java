package com.seaso.seaso.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seaso.seaso.common.persistance.DataEntity;
import com.seaso.seaso.modules.sys.utils.RoleType;
import org.springframework.data.annotation.Persistent;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sys_role")
public class Role extends DataEntity<Role> {

    @Persistent
    private static final long serialVersionUID = -1835818699458902220L;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "roles")
    private List<SystemUser> systemUsers;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    public Role() {
        super();
        systemUsers = new ArrayList<>();
        roleType = RoleType.GUEST;
    }

    public List<SystemUser> getSystemUsers() {
        return systemUsers;
    }

    public void setSystemUsers(List<SystemUser> systemUsers) {
        this.systemUsers = systemUsers;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
}
