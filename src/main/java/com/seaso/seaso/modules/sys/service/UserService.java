package com.seaso.seaso.modules.sys.service;

import com.seaso.seaso.modules.sys.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void createUser(User user);

    void updateByUserId(User user, String userId);

    Optional<User> findByUserId(String userId);

    Optional<User> findByUsername(String username);

    Page<User> findAllUsers(Pageable pageable);

    List<User> findAllUsers(User user);

    void deleteUsers(String userId);

    List<String> getHistoryByUserId(String userId);

    List<User> getAllUsers();
}
