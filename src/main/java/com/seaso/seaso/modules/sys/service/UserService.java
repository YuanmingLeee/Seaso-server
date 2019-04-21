package com.seaso.seaso.modules.sys.service;

import com.seaso.seaso.modules.question.entity.Question;
import com.seaso.seaso.modules.sys.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * User service class.
 *
 * @author Li Yuanming
 * @version 0.1
 */
public interface UserService {

    /**
     * Update user entity given the specific username.
     *
     * @param username username.
     * @param user     the user to be updated. THe change of field {@link User#username} has no effect.
     *                 Use {@link SystemService#updateUsernameByUserId(Long, String)} instead.
     * @see SystemService#updateUsernameByUserId(Long, String)
     */
    void updateByUsername(String username, User user);

    /**
     * Update user entity given user ID.
     *
     * @param userId user ID
     * @param user   the user to be updated. Similar to {@link UserService#updateByUsername(String, User)}, the change of
     *               field {@link User#username} has no effect.
     * @see UserService#updateByUsername(String, User)
     * @see SystemService#updateUsernameByUserId(Long, String)
     */
    void updateByUserId(Long userId, User user);

    /**
     * Find user given user ID.
     *
     * @param userId user ID.
     * @return the found user.
     */
    User findUserByUserId(Long userId);

    /**
     * Find user given username.
     *
     * @param username username.
     * @return the found user.
     */
    User findUserByUsername(String username);

    List<Question> findUserHistoryByUsername(String username);

    /**
     * Find a page of users given the queried page number, page size and sorting method.
     *
     * @param page the page name starts at 0.
     * @param size the page size.
     * @param sort the sorting method.
     * @return a page of users.
     * @see Page
     * @see Sort
     */
    Page<User> findAllUsers(int page, int size, Sort sort);
}
