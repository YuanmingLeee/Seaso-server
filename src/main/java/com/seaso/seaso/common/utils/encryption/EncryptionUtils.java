package com.seaso.seaso.common.utils.encryption;

import com.seaso.seaso.modules.sys.utils.UserPreference;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public final class EncryptionUtils {
    /**
     * Decode encrypted data.
     *
     * @param string encrypted string
     * @param clazz  type of the entity
     * @param <T>    class of the entity
     * @return a decoded data
     */
    private static <T extends EncryptData> T decodeEncryptData(String string, Class<T> clazz) {
        try {
            T instance = clazz.getConstructor().newInstance();
            instance.decode(string);
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Decode a system encoded string in to a {@link Map} whose key is Long type ID and the value is an
     * {@link EncryptData} entity.
     *
     * @param string the system encoded string.
     * @param clazz  the encrypt data entity class type.
     * @param <T>    the class of the encrypt data entity.
     * @return a {@link Map} with the key of a Long type ID, and the value of the extended {@link EncryptData}.
     */
    public static <T extends EncryptData> Map<Long, T> decodeString(@NotNull String string, Class<T> clazz) {
        return Arrays.stream(string.split(";"))
                .filter(e -> !e.equals(""))
                .map(e -> Arrays.asList(e.split(":")))
                .collect(Collectors.toMap(entry -> Long.parseLong(entry.get(0)),
                        entry -> decodeEncryptData(entry.get(1), clazz)));
    }

    /**
     * Encode the decoded encrypt data entity back to a string
     *
     * @param map the map of the encrypt data with Long type ID.
     * @return the encoded string
     */
    public static String encryptString(@NotNull Map<Long, UserPreference> map) {
        return map.entrySet().stream()
                .filter(x -> x.getValue() != null)
                .map(e -> e.getKey() + ":" + e.getValue().encrypt())
                .reduce((x, y) -> x + ";" + y).orElse("");
    }
}
