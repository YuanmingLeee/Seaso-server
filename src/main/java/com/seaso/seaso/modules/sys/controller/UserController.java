package com.seaso.seaso.modules.sys.controller;

import com.seaso.seaso.modules.sys.entity.User;
import com.seaso.seaso.modules.sys.service.UserService;
import com.seaso.seaso.modules.sys.utils.JsonResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ResponseBody
    public JsonResponse<List<User>> getUserList(@RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size,
                                                @RequestParam(value = "sort_by", defaultValue = "userId") String itemName) {
        List<User> users = userService.findAllUsers(page, size, Sort.by(itemName).descending());
        return new JsonResponse<>(users);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse<String> postUser(@ModelAttribute User user) {
        userService.createUser(user);
        return new JsonResponse<>("");
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse<User> getUser(@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        return new JsonResponse<>(user);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.PATCH)
    @ResponseBody
    public JsonResponse<String> updateUser(@PathVariable String username,
                                           @ModelAttribute User user) {
        userService.updateByUsername(user, username);
        return new JsonResponse<>("");
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse<String> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return new JsonResponse<>("");
    }


}
