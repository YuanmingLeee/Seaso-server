package com.seaso.seaso.modules.sys.utils;

import com.seaso.seaso.common.utils.encryption.EncryptionUtils;
import com.seaso.seaso.modules.sys.dao.RoleRepository;
import com.seaso.seaso.modules.sys.entity.Role;
import com.seaso.seaso.modules.sys.entity.SystemUser;
import com.seaso.seaso.modules.sys.entity.User;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserUtils {

    /**
     * User password encoder. This encoder is for the use of static method
     */
    private static PasswordEncoder encoder;

    /**
     * User password encoder for auto injection
     */
    private final PasswordEncoder _encoder;

    private static List<Role> roles;

    private final RoleRepository roleRepository;

    public static final long GUEST_USER_ID = -1L;

    /**
     * Constructor of UserUtils with {@link PasswordEncoder}
     *
     * @param encoder specific password encoder
     */
    @Autowired
    public UserUtils(PasswordEncoder encoder, RoleRepository roleRepository) {
        _encoder = encoder;
        this.roleRepository = roleRepository;
    }

    public static String encryptByBCrypt(@NotNull String plainPassword) {
        return encoder.encode(plainPassword);
    }

    /**
     * Get current authenticated user ID.
     *
     * @return User ID or -1 if no user has been authenticated.
     */
    public static long getCurrentUserId() {
        return getCurrentSystemUser().map(SystemUser::getUserId).orElse(GUEST_USER_ID);
    }

    /**
     * Get current authenticated user. A guest will be returned if no user has logged in.
     *
     * @return User or a guest instance.
     */
    public static User getCurrentUser() {
        return getCurrentSystemUser().map(SystemUser::getUser).orElse(User.getGuestInstance());
    }

    /**
     * Get current authenticated system user. Null will wrapped in Optional and returned if no user has
     * been authenticated. This method should be used with a control, as not all method need to expose the authentication
     * and authorization part in the system.
     *
     * @return the wrapped {@link SystemUser} or {@code null} by {@link Optional}
     */
    @NotNull
    private static Optional<SystemUser> getCurrentSystemUser() {
        Object principal = null;

        SystemUser systemUser;
        try {
            principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (NullPointerException ignored) {
        }

        if (principal instanceof SystemUser) {
            systemUser = (SystemUser) principal;
        } else {
            systemUser = SystemUser.getGuestInstance();
        }
        return Optional.of(systemUser);
    }

    @Contract("_ -> !null")
    public static String encryptUserPreference(@NotNull Map<Long, UserPreference> map) {
        return EncryptionUtils.encryptString(map);
    }

    public static Map<Long, UserPreference> decodeUserPreference(String string) {
        return EncryptionUtils.decodeString(string, UserPreference.class);
    }

    public static Role getRole(@NotNull RoleType roleType) {
        return roles.get(roleType.ordinal());
    }

    /**
     * Method automatically called after the registration of this beans. It copies to static {@link #encoder}
     * field for the use of it in static methods.
     */
    @PostConstruct
    public void init() {
        encoder = _encoder;
        _loadRoles();
    }

    private void _loadRoles() {
        RoleType[] roleTypes = RoleType.values();
        roles = roleRepository.findByRoleTypeIn(Arrays.asList(roleTypes));
    }
}
