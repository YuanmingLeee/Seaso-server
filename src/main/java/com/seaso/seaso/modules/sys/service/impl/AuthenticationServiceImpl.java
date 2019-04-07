package com.seaso.seaso.modules.sys.service.impl;

import com.seaso.seaso.modules.sys.dao.AuthenticationRepository;
import com.seaso.seaso.modules.sys.entity.Authentication;
import com.seaso.seaso.modules.sys.service.AuthenticationService;
import com.seaso.seaso.modules.sys.utils.AuthenticationType;
import com.seaso.seaso.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationRepository authenticationRepository;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public void createUserAuth(Authentication authentication) {
        if (authentication.getAuthenticationType() == AuthenticationType.USERNAME) {
            String password = UserUtils.encryptByBCrypt(authentication.getCredential());
            authentication.setCredential(password);
        }
        authenticationRepository.save(authentication);
    }

    @Override
    public int updateUserAuthByIdentifier(String identifier) {
        return 0;
    }

    @Override
    public void deleteUserAuthByIdentifier(String identifier) {
        authenticationRepository.deleteByIdentifier(identifier);
    }
}
