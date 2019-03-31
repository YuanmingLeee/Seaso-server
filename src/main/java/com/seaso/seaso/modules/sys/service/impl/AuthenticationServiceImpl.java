package com.seaso.seaso.modules.sys.service.impl;

import com.seaso.seaso.modules.sys.dao.UserAuthRepository;
import com.seaso.seaso.modules.sys.entity.Authentication;
import com.seaso.seaso.modules.sys.service.AuthenticationService;
import com.seaso.seaso.modules.sys.utils.AuthenticationType;
import com.seaso.seaso.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserAuthRepository userAuthRepository;

    @Autowired
    public AuthenticationServiceImpl(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public void createUserAuth(Authentication authentication) {
        if (authentication.getAuthenticationType() == AuthenticationType.USERNAME) {
            String password = UserUtils.encryptByBCrypt(authentication.getCredential());
            authentication.setCredential(password);
        }
        userAuthRepository.save(authentication);
    }

    @Override
    public int updateUserAuthByIdentifier(String identifier) {
        return 0;
    }

    @Override
    public void deleteUserAuthByIdentifier(String identifier) {
        userAuthRepository.deleteByIdentifier(identifier);
    }
}
