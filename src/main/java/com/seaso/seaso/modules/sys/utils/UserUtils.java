package com.seaso.seaso.modules.sys.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserUtils {

    public static String getUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if (principal instanceof Principal)
            username = ((UserDetails) principal).getUsername();
        else
            username = principal.toString();

        return username;
    }
}
