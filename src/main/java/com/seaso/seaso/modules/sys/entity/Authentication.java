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
@Table(name = "sys_auth",
        indexes = {@Index(name = "user_auth_identifier_unidex", columnList = "identifier", unique = true)})
public class Authentication extends DataEntity<Authentication> {

    @Transient
    private static final long serialVersionUID = 591050349135349779L;

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

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", unique = true, nullable = false, updatable = false)
    private SystemUser systemUser;

    /**
     * Default constructor
     */
    public Authentication() {
        super();
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

    public SystemUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }
}
