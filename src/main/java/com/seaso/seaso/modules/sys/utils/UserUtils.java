package com.seaso.seaso.modules.sys.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserUtils {

    private static PasswordEncoder encoder;
    private final PasswordEncoder _encoder;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyyHHmmssZ");

    @Autowired
    public UserUtils(PasswordEncoder _encoder) {
        this._encoder = _encoder;
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

    @PostConstruct
    public void init() {
        encoder = _encoder;
    }

    private static Date parseDateString(String string, SimpleDateFormat dateFormat) {
        try {
            return dateFormat.parse(string);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Date> decodeUserAnswerPreference(@NotNull String string) {
        return Arrays.stream(string.split(";"))
                .filter(e -> !e.equals(""))
                .map(e -> Arrays.asList(e.split(",")))
                .collect(Collectors.toMap(entry -> entry.get(0), entry -> parseDateString(entry.get(1), dateFormat)));
    }

    public static String encodeUserAnswerPreference(@NotNull Map<String, Date> map) {
        return map.entrySet().stream()
                .filter(x -> x.getValue() != null)
                .map(e -> e.getKey() + "," + dateFormat.format(e.getValue()))
                .reduce((x, y) -> x + ";" + y).orElse("");
    }
}
