package com.seaso.seaso.modules.sys.service.impl;

import com.seaso.seaso.common.exception.ResourceNotFoundException;
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

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByUserId(Long userId) {
        return userRepository.findByUserId(userId).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAllUsers(int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void updateByUsername(String username, User user) {
        User original = userRepository.findByUsername(username).orElseThrow(ResourceNotFoundException::new);
        original.merge(user);
        userRepository.save(original);
    }

    @Override
    @Transactional
    public void updateByUserId(Long userId, User user) {
        user.setUserId(userId);
        userRepository.save(user);
    }
}
