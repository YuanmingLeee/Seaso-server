package com.seaso.seaso.modules.sys.web;

import com.seaso.seaso.modules.sys.entity.User;
import com.seaso.seaso.modules.sys.service.SystemService;
import com.seaso.seaso.modules.sys.service.UserService;
import com.seaso.seaso.modules.sys.utils.JsonResponseBody;
import com.seaso.seaso.modules.sys.utils.RoleType;
import com.seaso.seaso.modules.sys.utils.UserUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User endpoint.
 *
 * @author Li Yuanming
 * @version 0.2
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    private final SystemService systemService;

    @Autowired
    public UserController(UserService userService, SystemService systemService) {
        this.userService = userService;
        this.systemService = systemService;
    }

    /**
     * Get a page of user given page number, page size, field name to be sorted by.
     *
     * @param page     page number (starts with 0), default value is 0.
     * @param size     page size, default value is 10.
     * @param itemName field name by which the users will be sorted. Default value is userId.
     * @return a {@link JsonResponseBody} with a {@link Page} of {@link User} if fetched successfully.
     */
    @ApiOperation(value = "Get sys list")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getUserList(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(value = "sort_by", defaultValue = "userId") String itemName) {
        Page<User> users = userService.findAllUsers(page, size, Sort.by(itemName).descending());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Get a user given username.
     *
     * @param username username.
     * @return a {@link JsonResponseBody} with a {@link User} if fetched successfully. A not found will be raised if
     * the user does not exist.
     */
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        return new ResponseEntity<>(new JsonResponseBody<>(HttpStatus.OK, user), HttpStatus.OK);
    }

    /**
     * Update the logged in user's information.
     *
     * @param user the new user with the fields to be updated. If a field is not to be updated, you should leave it as
     *             {@code null}
     * @return a {@link JsonResponseBody} if updated successfully. Other exceptions may be raised.
     */
    @RequestMapping(value = "/", method = RequestMethod.PATCH)
    public ResponseEntity<JsonResponseBody<?>> updateUser(@ModelAttribute User user) {
        Long userId = UserUtils.getCurrentUserId();
        String username = user.getUsername();

        if (user.getUsername() != null) {
            systemService.updateUsernameByUserId(userId, username);
        } else {
            userService.updateByUserId(userId, user);
        }
        return new ResponseEntity<>(new JsonResponseBody<>(), HttpStatus.OK);
    }

    /**
     * Change the logged in user's password.
     *
     * @param password the new plain password.
     * @return a {@link JsonResponseBody} if post successfully. Other exceptions may be raised. Refer to
     * {@link #changePassword(Long, String)} for details.
     */
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(@RequestParam String password) {
        Long userId = UserUtils.getCurrentUserId();
        return changePassword(userId, password);
    }

    /**
     * Create a new user with default user information. A username-password authentication will be created. The default
     * role {@link RoleType#USER} will be assigned. This endpoint is only reachable by user with a role of
     * {@link RoleType#ADMIN}.
     *
     * @param username the username of the user to be created. Username must be unique. Otherwise, an exception will be
     *                 raised.
     * @param password the login password of the user.
     * @return a {@link JsonResponseBody} if created successfully. Exceptions will be raised.
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> createUser(@RequestParam String username,
                                        @RequestParam String password) {
        systemService.createUser(username, password);
        return new ResponseEntity<>(new JsonResponseBody<>(HttpStatus.CREATED), HttpStatus.CREATED);
    }

    /**
     * Update a system user given user ID. The updatable field be this method is username, and user roles. This limit
     * is a consideration of security, where user information cannot be modified by others. This endpoint is only
     * reachable by {@link RoleType#ADMIN}. When called, it can either provide username, or roles. A both set will lead
     * the ineffectiveness of the update of roles.
     *
     * @param userId   the user ID
     * @param username the username
     * @param roles    the list of roles to be assigned.
     * @return a {@link JsonResponseBody} if updated successfully. Other exceptions may be raised.
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.PATCH)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> updateUser(@PathVariable Long userId,
                                        @RequestParam(required = false) String username,
                                        @RequestParam(required = false) List<RoleType> roles) {
        if (username != null)
            systemService.updateUsernameByUserId(userId, username);
        else if (roles != null)
            systemService.updateUserRolesByUserId(userId, roles);
        return new ResponseEntity<>(new JsonResponseBody<>(), HttpStatus.OK);
    }

    /**
     * Delete user given user ID. This endpoint is only reachable by {@link RoleType#ADMIN}.
     *
     * @param userId the user ID
     * @return a {@link JsonResponseBody} if fetched successfully. Other exceptions may be raised.
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        systemService.deleteUserByUserId(userId);
        return new ResponseEntity<>(new JsonResponseBody<>(), HttpStatus.OK);
    }

    /**
     * Update a password given user ID. This endpoint is only reachable by {@link RoleType#ADMIN}.
     *
     * @param userId   the user ID
     * @param password the new plain password
     * @return a {@link JsonResponseBody} if deleted successfully. Other exceptions may be raised.
     */
    @RequestMapping(value = "/password/{userId}", method = RequestMethod.POST)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> changePassword(@PathVariable Long userId,
                                            @RequestParam String password) {
        systemService.updatePasswordByUserId(userId, password);
        return new ResponseEntity<>(new JsonResponseBody<>(), HttpStatus.OK);
    }
}
