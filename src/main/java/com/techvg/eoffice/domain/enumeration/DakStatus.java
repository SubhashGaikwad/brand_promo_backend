package com.techvg.eoffice.domain.enumeration;

/**
 * The DakStatus enumeration.
 */
public enum DakStatus {
    CREATED("created"),
    UPDATED("updated"),
    ASSIGNED("assigned"),
    AWAITED("awaited"),
    HEARING("hearing"),
    HEARING_AWAITED("hearing_awaited"),
    HEARING_COMPLETED("hearing_completed"),
    PENDING("pending"),
    AWAITED_FOR_ORDER("awaited_for_order"),
    CLEARED("cleared");

    private final String value;

    DakStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
