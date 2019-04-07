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

    @ApiOperation(value = "Get sys list")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getUserList(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(value = "sort_by", defaultValue = "userId") String itemName) {
        Page<User> users = userService.findAllUsers(page, size, Sort.by(itemName).descending());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        return new ResponseEntity<>(new JsonResponseBody<>(), HttpStatus.OK);
    }

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

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(@RequestParam String password) {
        Long userId = UserUtils.getCurrentUserId();
        return changePassword(userId, password);
    }


    @RequestMapping(value = "/", method = RequestMethod.POST)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> createUser(@RequestParam String username,
                                        @RequestParam String password) {
        systemService.createUser(username, password);
        return new ResponseEntity<>(new JsonResponseBody<>(HttpStatus.CREATED), HttpStatus.CREATED);
    }

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

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        systemService.deleteUserByUserId(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/password/{userId}", method = RequestMethod.POST)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> changePassword(@PathVariable Long userId,
                                            @RequestParam String password) {
        systemService.updatePasswordByUserId(userId, password);
        return new ResponseEntity<>(new JsonResponseBody<>(), HttpStatus.OK);
    }
}
