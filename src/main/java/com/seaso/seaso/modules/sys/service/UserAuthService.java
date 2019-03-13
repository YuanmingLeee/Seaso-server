package com.seaso.seaso.modules.sys.service;

import com.seaso.seaso.modules.sys.entity.UserAuthentication;
import com.seaso.seaso.modules.sys.utils.AuthenticationType;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAuthService extends UserDetailsService {

    int updatePasswordByUserId(String userId, String newPassword);

    void createUserAuth(UserAuthentication userAuthentication);

    int updateUserAuthByIdentifier(String identifier);

    void deleteUserAuthByIdentifier(String identifier);
}
