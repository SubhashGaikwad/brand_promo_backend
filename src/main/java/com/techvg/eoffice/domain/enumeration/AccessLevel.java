package com.techvg.eoffice.domain.enumeration;

/**
 * The AccessLevel enumeration.
 */
public enum AccessLevel {
    ROOT("Root"),
    ORGANIZATION("Organization");

    private final String value;

    AccessLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
