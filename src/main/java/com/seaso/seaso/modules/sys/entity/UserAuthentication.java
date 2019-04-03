package com.seaso.seaso.modules.sys.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class UserAuthentication implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId",
            foreignKey = @ForeignKey(name = "user_authen_user_user_id_fk"))
    private User user;

    @OneToOne
    @JoinColumn(name = "authentication_identifier", referencedColumnName = "identifier",
            foreignKey = @ForeignKey(name = "user_authen_authen_identifier_fk"))
    private Authentication authentication;

    public UserAuthentication() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}
