package com.techvg.eoffice.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.eoffice.domain.DakIdGenerator} entity.
 */
public class DakIdGeneratorDTO implements Serializable {

    private Long id;

    private Long nextValInward;

    private Long nextValOutward;

    private OrganizationDTO organization;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNextValInward() {
        return nextValInward;
    }

    public void setNextValInward(Long nextValInward) {
        this.nextValInward = nextValInward;
    }

    public Long getNextValOutward() {
        return nextValOutward;
    }

    public void setNextValOutward(Long nextValOutward) {
        this.nextValOutward = nextValOutward;
    }

    public OrganizationDTO getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DakIdGeneratorDTO)) {
            return false;
        }

        DakIdGeneratorDTO dakIdGeneratorDTO = (DakIdGeneratorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dakIdGeneratorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DakIdGeneratorDTO{" +
            "id=" + getId() +
            ", nextValInward=" + getNextValInward() +
            ", nextValOutward=" + getNextValOutward() +
            ", organization=" + getOrganization() +
            "}";
    }
}
