package com.jygoh.heartspace.domain.post.model;

public enum Type {
    CHAT,
    CONCERNS,
    EMOTIONS;

    public static Type fromString(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Value can not be null or empty");
        }

        for (Type type : Type.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Invalid value: " + value);
    }
}
