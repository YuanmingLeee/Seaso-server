package com.seaso.seaso.modules.sys.service.impl;

import com.seaso.seaso.common.exception.ResourceNotFoundException;
import com.seaso.seaso.common.exception.ServiceException;
import com.seaso.seaso.modules.sys.dao.UserAuthRepository;
import com.seaso.seaso.modules.sys.dao.UserRepository;
import com.seaso.seaso.modules.sys.entity.User;
import com.seaso.seaso.modules.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserAuthRepository userAuthRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserAuthRepository userAuthRepository) {
        this.userRepository = userRepository;
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    @Transactional
    public void createUser(User user) throws ServiceException {
        user.preInsert();
        userRepository.findByUsername(user.getUsername()).ifPresent(s -> {
            throw new ResourceNotFoundException();
        });
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateByUsername(User user, String userId) throws ServiceException {
        User original = userRepository.findByUsername(userId).orElseThrow(ResourceNotFoundException::new);
        original.merge(user);
        original.preUpdate();
        userRepository.save(original);
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<User> findAllUsers(int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void deleteUser(String username) throws ServiceException {
        User user = userRepository.findByUsername(username).orElseThrow(ResourceNotFoundException::new);
        userRepository.deleteByUsername(username);
        userAuthRepository.deleteByUser_UserId(user.getUserId());
    }
}
