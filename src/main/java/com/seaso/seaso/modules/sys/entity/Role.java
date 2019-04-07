package com.seaso.seaso.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seaso.seaso.common.persistance.DataEntity;
import com.seaso.seaso.modules.sys.utils.RoleType;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sys_role")
public class Role extends DataEntity<Role> implements GrantedAuthority {

    @Transient
    private static final long serialVersionUID = -1835818699458902220L;

    @Transient
    private static final String rolePrefix = "ROLE_";

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

    @Override
    public String getAuthority() {
        return rolePrefix + roleType.name();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof Role && roleType.equals(((Role) obj).roleType);
        }
    }

    @Override
    public int hashCode() {
        return roleType.hashCode();
    }

    @Override
    public String toString() {
        return roleType.name();
    }
}
