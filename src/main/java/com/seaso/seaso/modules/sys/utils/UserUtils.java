package com.seaso.seaso.modules.sys.utils;

import com.seaso.seaso.modules.sys.dao.RoleRepository;
import com.seaso.seaso.modules.sys.entity.Role;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public static long getCurrentUserId() {
        Object principal;

        try {
            principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (NullPointerException e) {
            principal = new Principal(-1, "Guest", null);
        }

        long userId;

        if (principal instanceof Principal) {
            userId = ((Principal) principal).getUserId();
        } else {
            // set user to be the guest
            userId = -1;
        }

        return userId;
    }

    /**
     * Parse date {@link String} to {@link Date}
     *
     * @param string the date string
     * @return the phased date object
     */
    private static Date parseDateString(String string) {
        try {
            return new Date(Long.parseLong(string));
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Decode preference string in {@link org.springframework.security.core.userdetails.User} to a {@link Map} whose
     * key is {@link com.seaso.seaso.modules.question.entity.Answer#setAnswerId(Long)} and the value is preference
     * set Date.
     *
     * @param string the encoded preference string
     * @return a {@link Map}
     */
    public static Map<Long, Date> decodeUserAnswerPreference(@NotNull String string) {
        return Arrays.stream(string.split(";"))
                .filter(e -> !e.equals(""))
                .map(e -> Arrays.asList(e.split(",")))
                .collect(Collectors.toMap(entry -> Long.parseLong(entry.get(0)),
                        entry -> parseDateString(entry.get(1))));
    }

    /**
     * Encode preference to a {@link String}
     *
     * @param map the preference map
     * @return the encoded string
     * @see #decodeUserAnswerPreference(String)
     */
    public static String encodeUserAnswerPreference(@NotNull Map<Long, Date> map) {
        return map.entrySet().stream()
                .filter(x -> x.getValue() != null)
                .map(e -> e.getKey() + "," + e.getValue().getTime())
                .reduce((x, y) -> x + ";" + y).orElse("");
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
