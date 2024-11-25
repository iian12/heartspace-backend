package com.jygoh.heartspace.global.utils;

import java.util.function.Predicate;

public class EntityValidationUtils {

    public static Long decodeAndValidate(String encodedId, EncodeDecode encodeDecode,
        Predicate<Long> existsFn, String errorMessage) {
        if (encodedId == null) {
            return null;
        }

        Long decodedId = encodeDecode.decode(encodedId);
        if (!existsFn.test(decodedId)) {
            throw new IllegalArgumentException(errorMessage);
        }
        return decodedId;
    }
}
