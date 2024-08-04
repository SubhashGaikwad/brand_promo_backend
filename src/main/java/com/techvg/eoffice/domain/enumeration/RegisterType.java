package com.techvg.eoffice.domain.enumeration;

/**
 * The RegisterType enumeration.
 */
public enum RegisterType {
    WORK_DESCRIPTION("WorkDescription"),
    AWAITED_REGISTER("AwaitedRegister");

    private final String value;

    RegisterType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
