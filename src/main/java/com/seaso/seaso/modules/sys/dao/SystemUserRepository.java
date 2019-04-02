package com.seaso.seaso.modules.sys.dao;

import com.seaso.seaso.modules.sys.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {

    Optional<SystemUser> findByUser_Username(String username);

    void deleteByUser_UserId(Long userId);
}