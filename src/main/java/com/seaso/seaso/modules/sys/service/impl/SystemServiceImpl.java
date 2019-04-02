package com.seaso.seaso.modules.sys.service.impl;

import com.seaso.seaso.modules.sys.dao.AuthenticationRepository;
import com.seaso.seaso.modules.sys.dao.SystemUserRepository;
import com.seaso.seaso.modules.sys.entity.Authentication;
import com.seaso.seaso.modules.sys.entity.SystemUser;
import com.seaso.seaso.modules.sys.entity.User;
import com.seaso.seaso.modules.sys.exception.UserNotFoundException;
import com.seaso.seaso.modules.sys.service.SystemService;
import com.seaso.seaso.modules.sys.utils.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SystemServiceImpl implements SystemService {

    private final AuthenticationRepository authenticationRepository;

    private final SystemUserRepository systemUserRepository;

    @Autowired
    public SystemServiceImpl(AuthenticationRepository authenticationRepository, SystemUserRepository systemUserRepository) {
        this.authenticationRepository = authenticationRepository;
        this.systemUserRepository = systemUserRepository;
    }

    /**
     * Load Principal (system user) given username. Password is loaded in if the user use Username/Password pair to
     * authenticate. Otherwise, a null is placed in the password field. The {@link UsernameNotFoundException} will be
     * raised if there is no such username.
     *
     * @param s username
     * @return an instance of the current system user
     */
    @Override
    public UserDetails loadUserByUsername(String s) {
        Authentication authentication = authenticationRepository.findByIdentifier(s)
                .orElseThrow(UserNotFoundException::new);
        User user = authentication.getSystemUser().getUser();
        long userId = user.getUserId();
        String username = user.getUsername();
        String password = authentication.getCredential();
        return new Principal(userId, username, password);
    }

    @Override
    public void createUser(String username, String password) {

    }

    @Override
    public void updateUser(SystemUser systemUser) {

    }

    @Override
    public void deleteUserByUserId(String userId) {

    }
}
