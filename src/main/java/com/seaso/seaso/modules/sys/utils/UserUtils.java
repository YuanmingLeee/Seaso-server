package com.seaso.seaso.modules.sys.utils;

import com.seaso.seaso.common.utils.idgen.IdService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserUtils {

    /**
     * User password encoder. This encoder is for the use of static method
     */
    private static PasswordEncoder encoder;
    /**
     * Static specific ID generating service for the use of static method
     */
    private static IdService idService;
    /**
     * User password encoder for auto injection
     */
    private final PasswordEncoder _encoder;
    /**
     * Specific ID generating service
     */
    private final IdService _idService;

    /**
     * Constructor of UserUtils with {@link PasswordEncoder}
     *
     * @param encoder   specific password encoder
     * @param idService specific ID generating service
     */
    @Autowired
    public UserUtils(PasswordEncoder encoder, @Qualifier("snowflake") IdService idService) {
        _encoder = encoder;
        _idService = idService;
    }

    public static String encryptByBCrypt(@NotNull String plainPassword) {
        return encoder.encode(plainPassword);
    }

    public static String getUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if (principal instanceof Principal)
            username = ((UserDetails) principal).getUsername();
        else
            username = principal.toString();

        return username;
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
     * key is {@link com.seaso.seaso.modules.question.entity.Answer#answerId} and the value is preference set Date.
     *
     * @param string the encoded preference string
     * @return a {@link Map}
     */
    public static Map<String, Date> decodeUserAnswerPreference(@NotNull String string) {
        return Arrays.stream(string.split(";"))
                .filter(e -> !e.equals(""))
                .map(e -> Arrays.asList(e.split(",")))
                .collect(Collectors.toMap(entry -> entry.get(0), entry -> parseDateString(entry.get(1))));
    }

    /**
     * Encode preference to a {@link String}
     *
     * @param map the preference map
     * @return the encoded string
     * @see #decodeUserAnswerPreference(String)
     */
    public static String encodeUserAnswerPreference(@NotNull Map<String, Date> map) {
        return map.entrySet().stream()
                .filter(x -> x.getValue() != null)
                .map(e -> e.getKey() + "," + e.getValue().getTime())
                .reduce((x, y) -> x + ";" + y).orElse("");
    }

    /**
     * Method automatically called after the registration of this beans. It copies to static {@link #encoder}
     * field for the use of it in static methods.
     */
    @PostConstruct
    public void init() {
        encoder = _encoder;
    }
}
