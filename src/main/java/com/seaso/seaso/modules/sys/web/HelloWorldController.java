package com.seaso.seaso.modules.sys.web;

import com.seaso.seaso.modules.sys.entity.User;
import com.seaso.seaso.modules.sys.utils.JsonResponseBody;
import com.seaso.seaso.modules.sys.utils.UserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {

    @RequestMapping("/")
    public String index(ModelMap map) {
        map.addAttribute("host", "https://www.seaso.io");
        return "home";
    }

    @RequestMapping("/webapp/**")
    public String webapp(ModelMap map) {
        return "index";
    }

    @RequestMapping(value = "/login/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> login() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/whoami", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> whoAmI() {
        User user = UserUtils.getCurrentUser();
        if (user.getUserId() != -1L)
            return new ResponseEntity<>(new JsonResponseBody<>(HttpStatus.OK, user), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> logout(@RequestParam(required = false) boolean success) {
        if (success) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
