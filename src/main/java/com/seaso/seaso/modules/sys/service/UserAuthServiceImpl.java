package com.seaso.seaso.modules.sys.service;

import com.seaso.seaso.modules.sys.dao.UserAuthRepository;
import com.seaso.seaso.modules.sys.entity.User;
import com.seaso.seaso.modules.sys.entity.UserAuthentication;
import com.seaso.seaso.modules.sys.utils.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    private final UserAuthRepository userAuthRepository;

    @Autowired
    public UserAuthServiceImpl(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public int updatePasswordByUserId(String userId, String newPassword) {
        return userAuthRepository.updatePasswordByUserId(newPassword, userId);
    }

    @Override
    public void createUserAuth(UserAuthentication userAuthentication) {
        userAuthRepository.save(userAuthentication);
    }

    @Override
    public int updateUserAuthByIdentifier(String identifier) {
        return 0;
    }

    @Override
    public void deleteUserAuthByIdentifier(String identifier) {
        userAuthRepository.deleteByIdentifier(identifier);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userAuthRepository.findByIdentifier(s).map(UserAuthentication::getUser)
                .orElseThrow(() -> new UsernameNotFoundException(s));

        return new Principal(user);
    }
}
