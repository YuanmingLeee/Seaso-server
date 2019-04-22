package com.seaso.seaso.modules.sys.dao;

import com.seaso.seaso.modules.sys.entity.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Authentication DAO implemented by JPA
 */
public interface AuthenticationRepository extends JpaRepository<Authentication, String> {

    /**
     * Find {@link Authentication} DTO given identifier.
     *
     * @param identifier identifier, same as username if username/password is used for authentication.
     * @return an nullable {@link Authentication} DTO wrapped by {@link Optional}
     */
    Optional<Authentication> findByIdentifier(String identifier);

    void deleteByIdentifier(String identifier);
}
