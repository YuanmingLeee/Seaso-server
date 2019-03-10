package com.seaso.seaso.modules.sys.dao;

import com.seaso.seaso.modules.sys.entity.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuthentication, String> {

    Optional<UserAuthentication> findByIdentifier(String identifier);

    @Modifying
    @Query("update UserAuthentication u set u.credential = ?1 where u.credential = ?2")
    int updatePasswordByUserId(String password, String id);

    Optional<UserAuthRepository> deleteByIdentifier(String identifier);
}
