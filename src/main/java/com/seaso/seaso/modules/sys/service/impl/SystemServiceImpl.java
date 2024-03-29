package com.seaso.seaso.modules.sys.service.impl;

import com.google.common.collect.Lists;
import com.seaso.seaso.common.exception.ServiceException;
import com.seaso.seaso.modules.sys.dao.AuthenticationRepository;
import com.seaso.seaso.modules.sys.dao.SystemUserRepository;
import com.seaso.seaso.modules.sys.entity.Authentication;
import com.seaso.seaso.modules.sys.entity.Role;
import com.seaso.seaso.modules.sys.entity.SystemUser;
import com.seaso.seaso.modules.sys.entity.User;
import com.seaso.seaso.modules.sys.service.SystemService;
import com.seaso.seaso.modules.sys.utils.AuthenticationType;
import com.seaso.seaso.modules.sys.utils.RoleType;
import com.seaso.seaso.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SystemServiceImpl implements SystemService {

    private final AuthenticationRepository authenticationRepository;

    private final SystemUserRepository systemUserRepository;

    @Autowired
    public SystemServiceImpl(AuthenticationRepository authenticationRepository,
                             SystemUserRepository systemUserRepository) {
        this.authenticationRepository = authenticationRepository;
        this.systemUserRepository = systemUserRepository;
    }

    /**
     * Load Principal (system user) given username. Password is loaded in if the user use Username/Password pair to
     * authenticate. Otherwise, a null is placed in the password field. The {@link com.seaso.seaso.common.exception.ServiceException} will be
     * raised if there is no such username.
     *
     * @param s username
     * @return an instance of the current system user
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) {
        Authentication authentication = authenticationRepository.findByIdentifier(s)
                .orElseThrow(() -> new ServiceException("Authentication identifier not found"));
        SystemUser systemUser = authentication.getSystemUser();
        @SuppressWarnings("unused") long ignored = systemUser.getUserId();    // to trigger lazy fetch
        return systemUser;
    }

    /**
     * Create user given username and plain password. A default user will be created with its authentication and role.
     * Exception may be thrown by the database.
     *
     * @param username username
     * @param password plain password
     */
    @Override
    @Transactional
    public void createUser(String username, String password) {
        // check if username has been taken
        if (systemUserRepository.existsByUserUsername(username))
            throw new ServiceException("Username has been taken");

        // build user
        User user = new User.UserBuilder().with($ -> $.username = username).build();

        // build system user
        SystemUser systemUser = new SystemUser.SystemUserBuilder().with($ -> {
            $.user = user;
            $.roles = Lists.newArrayList(UserUtils.getRole(RoleType.USER));
        }).build();

        // build authentication
        Authentication authentication = new Authentication.AuthenticationBuilder().with($ -> {
            $.identifier = username;
            $.credential = password;
            $.authenticationType = AuthenticationType.USERNAME;
            $.systemUser = systemUser;
        }).build();

        // persist
        authenticationRepository.save(authentication);
    }

    @Override
    @Transactional
    public void updateUsernameByUserId(Long userId, String newUsername) {
        // check if the user ID exists
        SystemUser systemUser = systemUserRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ServiceException("User ID not found"));

        User user = systemUser.getUser();
        user.setUsername(newUsername);

        List<Authentication> authentications = systemUser.getAuthentications();
        Authentication auth = authentications.stream()
                .filter($ -> $.getAuthenticationType().equals(AuthenticationType.USERNAME))
                .collect(Collectors.toList())
                .get(0);
        auth.setIdentifier(newUsername);

        systemUserRepository.save(systemUser);
    }

    @Override
    @Transactional
    public void updateUserRolesByUserId(Long userId, List<RoleType> roles) {
        SystemUser systemUser = systemUserRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ServiceException("User ID not found"));
        Set<Role> roleEntities = roles.stream()
                .map(UserUtils::getRole)
                .collect(Collectors.toSet());
        systemUser.setRoles(Lists.newArrayList(roleEntities));
        systemUserRepository.save(systemUser);
    }

    @Override
    @Transactional
    public void deleteUserByUserId(Long userId) {
        SystemUser systemUser = systemUserRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ServiceException("User ID not found"));
        systemUserRepository.delete(systemUser);
    }

    @Override
    @Transactional
    public void updatePasswordByUserId(Long userId, String password) {
        SystemUser systemUser = systemUserRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ServiceException("User ID not found"));
        List<Authentication> authentications = systemUser.getAuthentications().stream()
                .filter($ -> $.getAuthenticationType().equals(AuthenticationType.USERNAME))
                .collect(Collectors.toList());
        Authentication authen = authentications.get(0);
        authen.setCredential(password);
        authenticationRepository.save(authen);
    }
}
