package com.seaso.seaso.modules.sys.service.impl;

import com.seaso.seaso.modules.sys.dao.UserAuthRepository;
import com.seaso.seaso.modules.sys.entity.Authentication;
import com.seaso.seaso.modules.sys.utils.AuthenticationType;
import com.seaso.seaso.modules.sys.utils.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SystemService implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;

    @Autowired
    public SystemService(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
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
        Authentication userAuthen = userAuthRepository.findByIdentifier(s)
                .orElseThrow(() -> new UsernameNotFoundException(s));
        String password = userAuthen.getAuthenticationType() == AuthenticationType.USERNAME ?
                userAuthen.getCredential() : null;
        return new Principal(userAuthen.getUser().getUsername(), password);
    }
}
