package com.seaso.seaso.modules.sys.service.impl;

import com.google.common.collect.Lists;
import com.seaso.seaso.common.exception.ResourceNotFoundException;
import com.seaso.seaso.common.exception.ServiceException;
import com.seaso.seaso.modules.question.dao.QuestionRepository;
import com.seaso.seaso.modules.question.entity.Question;
import com.seaso.seaso.modules.sys.dao.UserRepository;
import com.seaso.seaso.modules.sys.entity.User;
import com.seaso.seaso.modules.sys.service.UserService;
import com.seaso.seaso.modules.sys.utils.UserUtils;
import com.seaso.seaso.modules.sys.utils.ViewDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final QuestionRepository questionRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByUserId(Long userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @Override
    public List<Question> findUserHistoryByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        Map<Long, ViewDate> map = UserUtils.decodeUserHistory(user.getHistory());
        Set<Long> questionIds = map.keySet();
        if (questionIds.size() == 0)
            return Lists.newArrayList();
        return questionRepository.findByQuestionIdIn(questionIds);
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
        User original = userRepository.findByUsername(username).orElseThrow(() -> new ServiceException("User not found."));
        user.setId(original.getId());    // to prevent a creation of new user
        original.merge(user);
        userRepository.save(original);
    }

    @Override
    @Transactional
    public void updateByUserId(Long userId, User user) {
        User original = userRepository.findByUserId(userId).orElseThrow(() -> new ServiceException("User not found."));
        user.setId(original.getId());    // to prevent a creation of new user
        original.merge(user);
        userRepository.save(original);
    }
}
