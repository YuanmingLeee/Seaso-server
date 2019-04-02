package com.seaso.seaso.modules.sys.utils;

import com.seaso.seaso.common.persistance.DataEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Principal extends DataEntity<Principal> implements UserDetails {

    private static final long serialVersionUID = -6644006256880489365L;

    private long userId;

    private String username;

    private String password;

    public Principal(long userId, @NotNull String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("User"));
        return authorities;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public String getPassword() {
        return username;
    }

    @Override
    public String getUsername() {
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
}
