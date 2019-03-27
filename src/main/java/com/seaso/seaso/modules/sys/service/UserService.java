package com.seaso.seaso.modules.sys.service;

import com.seaso.seaso.common.exception.ServiceException;
import com.seaso.seaso.modules.sys.entity.User;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserService {
    void createUser(User user) throws ServiceException;

    void updateByUsername(User user, String userId) throws ServiceException;

    User findUserByUserId(String userId) throws ServiceException;

    User findUserByUsername(String username) throws ServiceException;

    List<User> findAllUsers(int page, int size, Sort sort) throws ServiceException;

    void deleteUser(String username) throws ServiceException;
}
