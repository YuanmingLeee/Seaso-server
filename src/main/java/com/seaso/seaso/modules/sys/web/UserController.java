package com.seaso.seaso.modules.sys.web;

import com.seaso.seaso.modules.sys.entity.User;
import com.seaso.seaso.modules.sys.service.SystemService;
import com.seaso.seaso.modules.sys.service.UserService;
import com.seaso.seaso.modules.sys.utils.JsonResponse;
import com.seaso.seaso.modules.sys.utils.UserUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "Get user list")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public JsonResponse<Page<User>> getUserList(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(value = "sort_by", defaultValue = "userId") String itemName) {
        Page<User> users = userService.findAllUsers(page, size, Sort.by(itemName).descending());
        return new JsonResponse<>(users);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public JsonResponse<User> getUser(@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        return new JsonResponse<>(user);
    }

    @RequestMapping(value = "/", method = RequestMethod.PATCH)
    public JsonResponse<User> updateUser(@ModelAttribute User user) {
        userService.updateByUserId(UserUtils.getCurrentUserId(), user);
        return new JsonResponse<>(user);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @Secured("ADMIN")
    public JsonResponse<String> createUser(@RequestParam String username,
                                           @RequestParam String password) {
        systemService.createUser(username, password);
        return new JsonResponse<>(HttpStatus.CREATED, "success", null);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.PATCH)
    @Secured("ADMIN")
    public JsonResponse<String> updateUser(@PathVariable String username,
                                           @ModelAttribute User user) {
        userService.updateByUsername(username, user);
        return new JsonResponse<>(null);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    @Secured("ADMIN")
    public JsonResponse<String> deleteUser(@PathVariable Long userId) {
        systemService.deleteUserByUserId(userId);
        return new JsonResponse<>("");
    }


}
