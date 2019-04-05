package com.seaso.seaso.modules.sys.service;

import com.seaso.seaso.modules.sys.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface UserService {

    void updateByUsername(User user, String username);

    User findUserByUserId(Long userId);

    User findUserByUsername(String username);

    Page<User> findAllUsers(int page, int size, Sort sort);
}
