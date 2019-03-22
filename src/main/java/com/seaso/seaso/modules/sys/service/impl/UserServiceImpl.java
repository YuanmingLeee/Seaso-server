package com.seaso.seaso.modules.sys.service.impl;

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

import javax.transaction.Transactional;
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
    public void createUser(User user) {
        user.preInsert();
        userRepository.save(user);
    }

    @Override
    public void updateByUsername(User user, String userId) {
        User userOriginal = userRepository.findByUserId(userId).get();
        user.preUpdate();
        // some staff in merging
        userRepository.save(user);
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
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
        userAuthRepository.deleteByIdentifier(username);
    }
}
