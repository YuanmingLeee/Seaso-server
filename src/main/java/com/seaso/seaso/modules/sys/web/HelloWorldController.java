package com.seaso.seaso.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldController {

    @RequestMapping("/")
    public String index(ModelMap map) {
        map.addAttribute("host", "https://www.seaso.io");
        return "home";
    }

    @RequestMapping("/login")
    public String login(ModelMap map) {
        return "login";
    }
}
