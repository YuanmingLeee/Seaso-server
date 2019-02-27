package com.seaso.seaso.modules.sys.dao;

import com.seaso.seaso.modules.sys.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(String name);
}
