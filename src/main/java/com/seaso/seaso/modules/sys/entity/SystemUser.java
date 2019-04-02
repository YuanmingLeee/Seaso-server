package com.seaso.seaso.modules.sys.entity;

import com.seaso.seaso.common.persistance.DataEntity;
import org.springframework.data.annotation.Persistent;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sys_user")
public class SystemUser extends DataEntity<SystemUser> {

    @Persistent
    private static final long serialVersionUID = -1572196250684286148L;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userId", unique = true, nullable = false)
    private User user;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "systemUser")
    private List<Authentication> authentications;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH}, mappedBy = "systemUser")
    private List<Role> roles;

    public SystemUser() {
        super();
        roles = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Authentication> getAuthentications() {
        return authentications;
    }

    public void setAuthentications(List<Authentication> authentications) {
        this.authentications = authentications;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
