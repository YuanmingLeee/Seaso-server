package com.seaso.seaso.modules.sys.dao;

import com.seaso.seaso.modules.sys.entity.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<Authentication, String> {

    Optional<Authentication> findByIdentifier(String identifier);

    void deleteByIdentifier(String identifier);
}
