package com.techvg.eoffice.domain.enumeration;

/**
 * The LetterType enumeration.
 */
public enum LetterType {
    INWARD("Inward"),
    OUTWARD("Outward"),
    SELF("Self"),
    OTHER("Other");

    private final String value;

    LetterType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
