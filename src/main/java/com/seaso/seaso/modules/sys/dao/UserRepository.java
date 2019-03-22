package com.seaso.seaso.modules.sys.dao;

import com.seaso.seaso.modules.sys.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

    Optional<User> findByUsername(String username);

    void deleteByUsername(String userId);
}
