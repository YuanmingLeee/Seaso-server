package com.seaso.seaso.modules.sys.entity;

import com.seaso.seaso.common.persistance.DataEntity;
import com.seaso.seaso.modules.sys.utils.AuthenticationType;

import javax.persistence.*;

/**
 * User authentication Entity class is mapped to USER_AUTH table which is responsible for sys authentication.
 * In each row, it stores the type of authentication method, identifier(e.g. username) and credential(e.g.
 * password).
 *
 * @author Yuanming Li
 * @version 0.1
 */
@Entity
@Table(name = "user_auth",
        indexes = {@Index(name = "user_auth_identifier_unidex", columnList = "identifier", unique = true)})
public class UserAuthentication extends DataEntity<UserAuthentication> {

    /**
     * User id
     */
    @Basic(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false,
            foreignKey = @ForeignKey(name = "user_auth_user_user_id_fk"))
    private User user;

    /**
     * Authentication method
     */
    @Enumerated(EnumType.STRING)
    private AuthenticationType authenticationType;

    /**
     * Identifier for authentication
     */
    @Column(nullable = false, unique = true)
    private String identifier;

    /**
     * Credential for authentication
     */
    @Column(nullable = false)
    private String credential;

    /**
     * Default constructor
     */
    public UserAuthentication() {
        super();
    }

    public User getUser() {
        user.setPassword(credential);
        return user;
    }

    public void setUser(User user) {
        user.setPassword(credential);
        this.user = user;
    }

    public AuthenticationType getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(AuthenticationType authenticationType) {
        this.authenticationType = authenticationType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }
}
