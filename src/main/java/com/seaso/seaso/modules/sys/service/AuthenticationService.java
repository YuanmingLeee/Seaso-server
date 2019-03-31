package com.seaso.seaso.modules.sys.service;

import com.seaso.seaso.modules.sys.entity.Authentication;

public interface AuthenticationService {

    void createUserAuth(Authentication authentication);

    int updateUserAuthByIdentifier(String identifier);

    void deleteUserAuthByIdentifier(String identifier);
}
