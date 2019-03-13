package com.seaso.seaso.modules.sys.service;

import com.seaso.seaso.modules.sys.dao.UserRepository;
import com.seaso.seaso.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateByUserId(User user, String userId) {
        User userOriginal = userRepository.findByUserId(userId).get();
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
    public Page<User> findAllUsers(Pageable pageable) {
        return null;
    }

    @Override
    public List<User> findAllUsers(User user) {
        return null;
    }

    @Override
    public void deleteUsers(String userId) {
        userRepository.deleteByUserId(userId);
    }

    @Override
    public List<String> getHistoryByUserId(String userId) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return Collections.emptyList();
    }
}
