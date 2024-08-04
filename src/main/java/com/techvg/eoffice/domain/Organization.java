package com.techvg.eoffice.domain;

import com.techvg.eoffice.domain.enumeration.OrganizationType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Organization.
 */
@Entity
@Table(name = "organization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "organization_name", nullable = false, unique = true)
    private String organizationName;

    @Column(name = "org_name_marathi", unique = true)
    private String orgNameInMarathi;

    @Column(name = "address")
    private String address;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "description")
    private String description;

    @Column(name = "is_activate")
    private Boolean isActivate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "orgnization_type", nullable = false)
    private OrganizationType orgnizationType;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Organization id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public Organization organizationName(String organizationName) {
        this.setOrganizationName(organizationName);
        return this;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getAddress() {
        return this.address;
    }

    public Organization address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedOn() {
        return this.createdOn;
    }

    public Organization createdOn(String createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getDescription() {
        return this.description;
    }

    public Organization description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActivate() {
        return this.isActivate;
    }

    public Organization isActivate(Boolean isActivate) {
        this.setIsActivate(isActivate);
        return this;
    }

    public void setIsActivate(Boolean isActivate) {
        this.isActivate = isActivate;
    }

    public OrganizationType getOrgnizationType() {
        return this.orgnizationType;
    }

    public Organization orgnizationType(OrganizationType orgnizationType) {
        this.setOrgnizationType(orgnizationType);
        return this;
    }

    public void setOrgnizationType(OrganizationType orgnizationType) {
        this.orgnizationType = orgnizationType;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Organization lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Organization lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getOrgNameInMarathi() {
        return this.orgNameInMarathi;
    }

    public Organization orgNameInMarathi(String orgNameInMarathi) {
        this.setOrgNameInMarathi(orgNameInMarathi);
        return this;
    }

    public void setOrgNameInMarathi(String orgNameInMarathi) {
        this.orgNameInMarathi = orgNameInMarathi;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organization)) {
            return false;
        }
        return id != null && id.equals(((Organization) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organization{" +
            "id=" + getId() +
            ", organizationName='" + getOrganizationName() + "'" +
            ", orgNameInMarathi='" + getOrgNameInMarathi() + "'" +
            ", address='" + getAddress() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActivate='" + getIsActivate() + "'" +
            ", orgnizationType='" + getOrgnizationType() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
