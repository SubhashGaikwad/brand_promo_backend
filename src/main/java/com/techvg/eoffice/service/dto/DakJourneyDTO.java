package com.techvg.eoffice.service.dto;

import com.techvg.eoffice.domain.enumeration.DakStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.eoffice.domain.DakJourney} entity.
 */
public class DakJourneyDTO implements Serializable {

    private Long id;

    private Instant assignedOn;

    private Instant updatedOn;

    private DakStatus dakStatus;

    private Boolean status;

    private String dakAssignedBy;

    private String dakAssignedTo;

    private Instant lastModified;

    private String lastModifiedBy;

    private DakMasterDTO dakMaster;

    private SecurityUserDTO securityUser;

    private CommentMasterDTO commentMaster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getAssignedOn() {
        return assignedOn;
    }

    public void setAssignedOn(Instant assignedOn) {
        this.assignedOn = assignedOn;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
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

    public String getDakAssignedBy() {
        return dakAssignedBy;
    }

    public void setDakAssignedBy(String dakAssignedBy) {
        this.dakAssignedBy = dakAssignedBy;
    }

    public String getDakAssignedTo() {
        return dakAssignedTo;
    }

    public void setDakAssignedTo(String dakAssignedTo) {
        this.dakAssignedTo = dakAssignedTo;
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

    public CommentMasterDTO getCommentMaster() {
        return commentMaster;
    }

    public void setCommentMaster(CommentMasterDTO commentMaster) {
        this.commentMaster = commentMaster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DakJourneyDTO)) {
            return false;
        }

        DakJourneyDTO dakJourneyDTO = (DakJourneyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dakJourneyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DakJourneyDTO{" +
            "id=" + getId() +
            ", assignedOn='" + getAssignedOn() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", dakStatus='" + getDakStatus() + "'" +
            ", status='" + getStatus() + "'" +
            ", dakAssignedBy='" + getDakAssignedBy() + "'" +
            ", dakAssignedTo='" + getDakAssignedTo() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", dakMaster=" + getDakMaster() +
            ", securityUser=" + getSecurityUser() +
            ", commentMaster=" + getCommentMaster() +
            "}";
    }
}
