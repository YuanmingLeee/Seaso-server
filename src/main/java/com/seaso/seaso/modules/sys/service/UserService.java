package com.seaso.seaso.modules.sys.service;

import com.seaso.seaso.modules.sys.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    int createUser(String name, Integer age);

    int updateUser(User user);

    Page<User> findAllUsers(Pageable pageable);

    List<User> findAllUsers(User user);

    int deleteUsers(User user);

    int updatePasswordByUserId(String userId, String newPassword);

    List<String> getHistoryByUserId(String userId);

    List<User> getAllUsers();

    int deleteAllUsers();
}
