package com.techvg.eoffice.domain.enumeration;

/**
 * The OrganizationType enumeration.
 */
public enum OrganizationType {
    AR("TalukaRegister"),
    DDR("DistrictDeputyRegister");

    private final String value;

    OrganizationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
