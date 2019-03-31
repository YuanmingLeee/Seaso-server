package com.seaso.seaso.modules.sys.dao;

import com.seaso.seaso.modules.sys.entity.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<Authentication, String> {

    Optional<Authentication> findByIdentifier(String identifier);

    @Modifying
    @Query("update Authentication u set u.credential = ?1 where u.identifier = ?2")
    int updatePasswordByIdentifier(String password, String id);

    void deleteByUser_UserId(Long userId);

    void deleteByIdentifier(String identifier);
}
