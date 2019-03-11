package com.seaso.seaso.modules.sys.controller;

import com.seaso.seaso.modules.sys.entity.User;
import com.seaso.seaso.modules.sys.service.UserAuthService;
import com.seaso.seaso.modules.sys.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    private final UserAuthService userAuthService;

    @Autowired
    public UserController(UserService userService, UserAuthService userAuthService) {
        this.userService = userService;
        this.userAuthService = userAuthService;
    }

    @ApiOperation(value = "Get sys list")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<User> getUserList() {

        return userService.findAllUsers(new PageRequest(1, 10));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String postUser(@ModelAttribute User user) {
        userService.createUser(user);
        return "success";
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public User getUser(@PathVariable String username) {
        return userService.findByUserId(username).get();
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.PUT)
    public String updateUser(@ModelAttribute User user) {
        userService.updateByUserId(user, user.getUserId());
        return "success";
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable String username) {
        userService.deleteUsers(username);
        return "success";
    }
}
