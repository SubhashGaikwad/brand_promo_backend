package com.techvg.eoffice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.eoffice.domain.enumeration.DakStatus;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DakHistory.
 */
@Entity
@Table(name = "dak_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DakHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Column(name = "assigned_by")
    private String assignedBy;

    @Column(name = "assigned_on")
    private Instant assignedOn;

    @Column(name = "created_on")
    private Instant createdOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "dak_status")
    private DakStatus dakStatus;

    @Column(name = "status")
    private Boolean status;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DakHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public DakHistory date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getAssignedBy() {
        return this.assignedBy;
    }

    public DakHistory assignedBy(String assignedBy) {
        this.setAssignedBy(assignedBy);
        return this;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public Instant getAssignedOn() {
        return this.assignedOn;
    }

    public DakHistory assignedOn(Instant assignedOn) {
        this.setAssignedOn(assignedOn);
        return this;
    }

    public void setAssignedOn(Instant assignedOn) {
        this.assignedOn = assignedOn;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public DakHistory createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public DakStatus getDakStatus() {
        return this.dakStatus;
    }

    public DakHistory dakStatus(DakStatus dakStatus) {
        this.setDakStatus(dakStatus);
        return this;
    }

    public void setDakStatus(DakStatus dakStatus) {
        this.dakStatus = dakStatus;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public DakHistory status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public DakHistory lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public DakHistory lastModifiedBy(String lastModifiedBy) {
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

    public DakHistory dakMaster(DakMaster dakMaster) {
        this.setDakMaster(dakMaster);
        return this;
    }

    public SecurityUser getSecurityUser() {
        return this.securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public DakHistory securityUser(SecurityUser securityUser) {
        this.setSecurityUser(securityUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DakHistory)) {
            return false;
        }
        return id != null && id.equals(((DakHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
	@Override
	public String toString() {
		return "DakHistory{" + "id=" + getId() + ", date='" + getDate() + "'" + ", assignedBy='" + getAssignedBy() + "'"
				+ ", assignedOn='" + getAssignedOn() + "'" + ", createdOn='" + getCreatedOn() + "'" + ", dakStatus='"
				+ getDakStatus() + "'" + ", status='" + getStatus() + "'" + ", lastModified='" + getLastModified() + "'"
				+ ", lastModifiedBy='" + getLastModifiedBy() + "'" + "}";
	}
}
