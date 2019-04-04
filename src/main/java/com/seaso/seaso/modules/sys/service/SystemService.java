package com.seaso.seaso.modules.sys.service;

import com.seaso.seaso.modules.sys.entity.SystemUser;
import com.seaso.seaso.modules.sys.utils.RoleType;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SystemService extends UserDetailsService {

    void createUser(String username, String password);

    void updateUser(SystemUser systemUser);

    void deleteUserByUserId(Long userId);

    void updatePasswordByUserId(Long userId, String password);

    void assignRoleByUserId(Long userId, RoleType roleType);
}
