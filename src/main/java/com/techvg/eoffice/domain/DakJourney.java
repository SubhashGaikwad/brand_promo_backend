package com.techvg.eoffice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.eoffice.domain.enumeration.DakStatus;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DakJourney.
 */
@Entity
@Table(name = "dak_journey")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DakJourney implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "assigned_on")
    private Instant assignedOn;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "dak_status")
    private DakStatus dakStatus;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "dak_assigned_by")
    private String dakAssignedBy;

    @Column(name = "dak_assigned_to")
    private String dakAssignedTo;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "organization", "securityUsers" }, allowSetters = true)
    private DakMaster dakMaster;

    @ManyToOne
    @JsonIgnoreProperties(value = { "organization", "securityPermissions", "securityRoles", "dakMasters" }, allowSetters = true)
    private SecurityUser securityUser;

    @ManyToOne
    @JsonIgnoreProperties(value = { "securityUser", "dakMaster" }, allowSetters = true)
    private CommentMaster commentMaster;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DakJourney id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getAssignedOn() {
        return this.assignedOn;
    }

    public DakJourney assignedOn(Instant assignedOn) {
        this.setAssignedOn(assignedOn);
        return this;
    }

    public void setAssignedOn(Instant assignedOn) {
        this.assignedOn = assignedOn;
    }

    public Instant getUpdatedOn() {
        return this.updatedOn;
    }

    public DakJourney updatedOn(Instant updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public DakStatus getDakStatus() {
        return this.dakStatus;
    }

    public DakJourney dakStatus(DakStatus dakStatus) {
        this.setDakStatus(dakStatus);
        return this;
    }

    public void setDakStatus(DakStatus dakStatus) {
        this.dakStatus = dakStatus;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public DakJourney status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDakAssignedBy() {
        return this.dakAssignedBy;
    }

    public DakJourney dakAssignedBy(String dakAssignedBy) {
        this.setDakAssignedBy(dakAssignedBy);
        return this;
    }

    public void setDakAssignedBy(String dakAssignedBy) {
        this.dakAssignedBy = dakAssignedBy;
    }

    public String getDakAssignedTo() {
        return this.dakAssignedTo;
    }

    public DakJourney dakAssignedTo(String dakAssignedTo) {
        this.setDakAssignedTo(dakAssignedTo);
        return this;
    }

    public void setDakAssignedTo(String dakAssignedTo) {
        this.dakAssignedTo = dakAssignedTo;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public DakJourney lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public DakJourney lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public DakMaster getDakMaster() {
        return this.dakMaster;
    }

    public void setDakMaster(DakMaster dakMaster) {
        this.dakMaster = dakMaster;
    }

    public DakJourney dakMaster(DakMaster dakMaster) {
        this.setDakMaster(dakMaster);
        return this;
    }

    public SecurityUser getSecurityUser() {
        return this.securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public DakJourney securityUser(SecurityUser securityUser) {
        this.setSecurityUser(securityUser);
        return this;
    }

    public CommentMaster getCommentMaster() {
        return this.commentMaster;
    }

    public void setCommentMaster(CommentMaster commentMaster) {
        this.commentMaster = commentMaster;
    }

    public DakJourney commentMaster(CommentMaster commentMaster) {
        this.setCommentMaster(commentMaster);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DakJourney)) {
            return false;
        }
        return id != null && id.equals(((DakJourney) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DakJourney{" +
            "id=" + getId() +
            ", assignedOn='" + getAssignedOn() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", dakStatus='" + getDakStatus() + "'" +
            ", status='" + getStatus() + "'" +
            ", dakAssignedBy='" + getDakAssignedBy() + "'" +
            ", dakAssignedTo='" + getDakAssignedTo() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
