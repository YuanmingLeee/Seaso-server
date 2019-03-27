package com.seaso.seaso.modules.sys.service;

import com.seaso.seaso.modules.sys.entity.User;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserService {
    void createUser(User user);

    void updateByUsername(User user, String userId);

    User findUserByUserId(String userId);

    User findUserByUsername(String username);

    List<User> findAllUsers(int page, int size, Sort sort);

    void deleteUser(String username);
}
