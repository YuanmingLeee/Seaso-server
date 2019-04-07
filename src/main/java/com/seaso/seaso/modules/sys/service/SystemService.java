package com.seaso.seaso.modules.sys.service;

import com.seaso.seaso.modules.sys.utils.RoleType;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface SystemService extends UserDetailsService {

    void createUser(String username, String password);

    void updateUsernameByUserId(Long userId, String newUsername);

    void updateUserRolesByUserId(Long userId, List<RoleType> roles);

    void deleteUserByUserId(Long userId);

    void updatePasswordByUserId(Long userId, String password);
}
