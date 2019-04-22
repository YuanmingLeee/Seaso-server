package com.seaso.seaso.modules.sys.dao;

import com.seaso.seaso.modules.sys.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {

    boolean existsByUserUsername(String username);

    Optional<SystemUser> findByUser_Username(String username);

    Optional<SystemUser> findByUser_UserId(Long userId);

}
