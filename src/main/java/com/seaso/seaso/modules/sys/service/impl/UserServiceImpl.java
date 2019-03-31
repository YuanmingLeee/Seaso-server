package com.seaso.seaso.modules.sys.service.impl;

import com.seaso.seaso.common.exception.ResourceConflictException;
import com.seaso.seaso.common.exception.ResourceNotFoundException;
import com.seaso.seaso.modules.sys.dao.UserAuthRepository;
import com.seaso.seaso.modules.sys.dao.UserRepository;
import com.seaso.seaso.modules.sys.entity.User;
import com.seaso.seaso.modules.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void createUser(User user) {
        user.preInsert();
        userRepository.findByUsername(user.getUsername()).ifPresent(s -> {
            throw new ResourceConflictException("Username has been taken");
        });
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateByUsername(User user, String username) {
        User original = userRepository.findByUsername(username).orElseThrow(ResourceNotFoundException::new);
        original.merge(user);
        original.preUpdate();
        userRepository.save(original);
    }

    @Override
    public User findUserByUserId(Long userId) {
        return userRepository.findByUserId(userId).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers(int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable).getContent();
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(ResourceNotFoundException::new);
        userRepository.deleteByUsername(username);
        userAuthRepository.deleteByUser_UserId(user.getUserId());
    }
}
