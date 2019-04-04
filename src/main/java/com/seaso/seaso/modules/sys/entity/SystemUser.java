package com.seaso.seaso.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.seaso.seaso.common.persistance.DataEntity;
import com.seaso.seaso.modules.sys.utils.AuthenticationType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * System user (principal) Entity class is mapped to SYS_USER table which holding basic information for authentication
 * and authorization.
 *
 * @author Yuanming Li
 * @version 0.1
 */
@Entity
@Table(name = "sys_user")
public class SystemUser extends DataEntity<SystemUser> implements UserDetails {

    @Transient
    private static final long serialVersionUID = -1572196250684286148L;

    @Transient
    private Long userId;

    @Transient
    private String username;

    @Transient
    private String password;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userId", unique = true, nullable = false)
    private User user;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "systemUser", orphanRemoval = true)
    private List<Authentication> authentications;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinTable(
            name = "sys_user_role",
            joinColumns = {
                    @JoinColumn(name = "userId", unique = true, nullable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "roleId", unique = true, nullable = false)
            }
    )
    private List<Role> roles;

    public SystemUser() {
        super();
        authentications = Lists.newArrayList();
        roles = Lists.newArrayList();
    }

    private SystemUser(User user, List<Authentication> authentications, List<Role> roles) {
        this();
        this.user = user;
        this.authentications.addAll(authentications);
        this.roles.addAll(roles);
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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

    /**
     * Post load method called by JPA. This method caches username, user ID and password.
     */
    @PostLoad
    public void _postLoad() {

        if (password != null && username != null && userId != null) {    // already cached
            return;
        }
        Authentication authentication;
        try {
            authentication = authentications.stream().filter($ -> $.getAuthenticationType().equals(AuthenticationType.USERNAME))
                    .collect(Collectors.toList()).get(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Not a valid user", e.getCause());
        }
        password = authentication.getCredential();
        username = authentication.getIdentifier();
        userId = user.getUserId();
    }

    public static class SystemUserBuilder {
        public User user;
        public List<Authentication> authentications;
        public List<Role> roles;

        public SystemUserBuilder with(Consumer<SystemUserBuilder> build) {
            build.accept(this);
            return this;
        }

        public SystemUser build() {
            return new SystemUser(user, authentications, roles);
        }
    }
}
