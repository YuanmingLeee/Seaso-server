package com.seaso.seaso.modules.sys.service;

import com.seaso.seaso.common.exception.ServiceException;
import com.seaso.seaso.modules.sys.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public interface UserService {
    void createUser(User user) throws ServiceException;

    void updateByUsername(User user, String userId) throws ServiceException;

    Optional<User> findByUserId(String userId);

    Optional<User> findByUsername(String username);

    Page<User> findAllUsers(int page, int size, Sort sort);

    void deleteUser(String username) throws ServiceException;
}
