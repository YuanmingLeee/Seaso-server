package com.seaso.seaso.modules.sys.service;

import com.seaso.seaso.modules.sys.entity.UserAuthentication;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAuthService extends UserDetailsService {

    void createUserAuth(UserAuthentication userAuthentication);

    int updateUserAuthByIdentifier(String identifier);

    void deleteUserAuthByIdentifier(String identifier);
}
