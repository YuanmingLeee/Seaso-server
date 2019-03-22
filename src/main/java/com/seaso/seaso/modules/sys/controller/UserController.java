package com.seaso.seaso.modules.sys.controller;

import com.seaso.seaso.common.exception.ResourceNotFoundException;
import com.seaso.seaso.modules.sys.entity.User;
import com.seaso.seaso.modules.sys.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Get sys list")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<User> getUserList(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                  @RequestParam(value = "sort_by", defaultValue = "userId") String itemName) {
        return userService.findAllUsers(page, size, Sort.by(itemName).descending());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String postUser(@ModelAttribute User user) {
        userService.createUser(user);
        return "success";
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public User getUser(@PathVariable String username) {
        return userService.findByUsername(username).orElseThrow(ResourceNotFoundException::new);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.PUT)
    public String updateUser(@PathVariable String username,
                             @ModelAttribute User user) {
        userService.updateByUsername(user, username);
        return "success";
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return "success";
    }
}
