package com.techvg.eoffice.service.dto;

import com.techvg.eoffice.domain.enumeration.DakStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.eoffice.domain.DakHistory} entity.
 */
public class DakHistoryDTO implements Serializable {

    private Long id;

    private Instant date;

    private String assignedBy;

    private Instant assignedOn;

    private Instant createdOn;

    private DakStatus dakStatus;

    private Boolean status;

    private Instant lastModified;

    private String lastModifiedBy;

    private DakMasterDTO dakMaster;

    private SecurityUserDTO securityUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public Instant getAssignedOn() {
        return assignedOn;
    }

    public void setAssignedOn(Instant assignedOn) {
        this.assignedOn = assignedOn;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public DakStatus getDakStatus() {
        return dakStatus;
    }

    public void setDakStatus(DakStatus dakStatus) {
        this.dakStatus = dakStatus;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public DakMasterDTO getDakMaster() {
        return dakMaster;
    }

    public void setDakMaster(DakMasterDTO dakMaster) {
        this.dakMaster = dakMaster;
    }

    public SecurityUserDTO getSecurityUser() {
        return securityUser;
    }

    public void setSecurityUser(SecurityUserDTO securityUser) {
        this.securityUser = securityUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DakHistoryDTO)) {
            return false;
        }

        DakHistoryDTO dakHistoryDTO = (DakHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dakHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DakHistoryDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", assignedBy='" + getAssignedBy() + "'" +
            ", assignedOn='" + getAssignedOn() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", dakStatus='" + getDakStatus() + "'" +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", dakMaster=" + getDakMaster() +
            ", securityUser=" + getSecurityUser() +
            "}";
    }
}
