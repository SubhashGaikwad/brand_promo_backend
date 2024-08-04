package com.techvg.eoffice.service.criteria;

import com.techvg.eoffice.domain.enumeration.OrganizationType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.techvg.eoffice.domain.Organization} entity. This class is used
 * in {@link com.techvg.eoffice.web.rest.OrganizationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /organizations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class OrganizationCriteria implements Serializable, Criteria {

    /**
     * Class for filtering OrganizationType
     */
    public static class OrganizationTypeFilter extends Filter<OrganizationType> {

        public OrganizationTypeFilter() {}

        public OrganizationTypeFilter(OrganizationTypeFilter filter) {
            super(filter);
        }

        @Override
        public OrganizationTypeFilter copy() {
            return new OrganizationTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter organizationName;

    private StringFilter address;

    private StringFilter createdOn;

    private StringFilter description;

    private BooleanFilter isActivate;

    private OrganizationTypeFilter orgnizationType;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public OrganizationCriteria() {}

    public OrganizationCriteria(OrganizationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.organizationName = other.organizationName == null ? null : other.organizationName.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.isActivate = other.isActivate == null ? null : other.isActivate.copy();
        this.orgnizationType = other.orgnizationType == null ? null : other.orgnizationType.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OrganizationCriteria copy() {
        return new OrganizationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getOrganizationName() {
        return organizationName;
    }

    public StringFilter organizationName() {
        if (organizationName == null) {
            organizationName = new StringFilter();
        }
        return organizationName;
    }

    public void setOrganizationName(StringFilter organizationName) {
        this.organizationName = organizationName;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getCreatedOn() {
        return createdOn;
    }

    public StringFilter createdOn() {
        if (createdOn == null) {
            createdOn = new StringFilter();
        }
        return createdOn;
    }

    public void setCreatedOn(StringFilter createdOn) {
        this.createdOn = createdOn;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BooleanFilter getIsActivate() {
        return isActivate;
    }

    public BooleanFilter isActivate() {
        if (isActivate == null) {
            isActivate = new BooleanFilter();
        }
        return isActivate;
    }

    public void setIsActivate(BooleanFilter isActivate) {
        this.isActivate = isActivate;
    }

    public OrganizationTypeFilter getOrgnizationType() {
        return orgnizationType;
    }

    public OrganizationTypeFilter orgnizationType() {
        if (orgnizationType == null) {
            orgnizationType = new OrganizationTypeFilter();
        }
        return orgnizationType;
    }

    public void setOrgnizationType(OrganizationTypeFilter orgnizationType) {
        this.orgnizationType = orgnizationType;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public InstantFilter lastModified() {
        if (lastModified == null) {
            lastModified = new InstantFilter();
        }
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrganizationCriteria that = (OrganizationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(organizationName, that.organizationName) &&
            Objects.equals(address, that.address) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(description, that.description) &&
            Objects.equals(isActivate, that.isActivate) &&
            Objects.equals(orgnizationType, that.orgnizationType) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            organizationName,
            address,
            createdOn,
            description,
            isActivate,
            orgnizationType,
            lastModified,
            lastModifiedBy,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (organizationName != null ? "organizationName=" + organizationName + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (isActivate != null ? "isActivate=" + isActivate + ", " : "") +
            (orgnizationType != null ? "orgnizationType=" + orgnizationType + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
