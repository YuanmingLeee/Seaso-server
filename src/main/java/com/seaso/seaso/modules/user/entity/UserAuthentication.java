package com.seaso.seaso.modules.user.entity;

import com.seaso.seaso.common.persistance.DataEntity;
import com.seaso.seaso.modules.user.utils.AuthenticationType;

import javax.persistence.*;

/**
 * User authentication Entity class is mapped to USER_AUTH table which is responsible for user authentication.
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
     * Id for primary key of persistence layer
     */
    @Id
    @GeneratedValue
    @Column
    private Long id;

    /**
     * User id
     */
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
}
