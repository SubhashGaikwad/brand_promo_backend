package com.techvg.eoffice.service.criteria;

import com.techvg.eoffice.domain.enumeration.DakStatus;
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
 * Criteria class for the {@link com.techvg.eoffice.domain.DakHistory} entity. This class is used
 * in {@link com.techvg.eoffice.web.rest.DakHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dak-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class DakHistoryCriteria implements Serializable, Criteria {

    /**
     * Class for filtering DakStatus
     */
    public static class DakStatusFilter extends Filter<DakStatus> {

        public DakStatusFilter() {}

        public DakStatusFilter(DakStatusFilter filter) {
            super(filter);
        }

        @Override
        public DakStatusFilter copy() {
            return new DakStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter date;

    private StringFilter assignedBy;

    private InstantFilter assignedOn;

    private InstantFilter createdOn;

    private DakStatusFilter dakStatus;

    private BooleanFilter status;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter dakMasterId;

    private LongFilter securityUserId;

    private Boolean distinct;

    public DakHistoryCriteria() {}

    public DakHistoryCriteria(DakHistoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.assignedBy = other.assignedBy == null ? null : other.assignedBy.copy();
        this.assignedOn = other.assignedOn == null ? null : other.assignedOn.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.dakStatus = other.dakStatus == null ? null : other.dakStatus.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.dakMasterId = other.dakMasterId == null ? null : other.dakMasterId.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DakHistoryCriteria copy() {
        return new DakHistoryCriteria(this);
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

    public InstantFilter getDate() {
        return date;
    }

    public InstantFilter date() {
        if (date == null) {
            date = new InstantFilter();
        }
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public StringFilter getAssignedBy() {
        return assignedBy;
    }

    public StringFilter assignedBy() {
        if (assignedBy == null) {
            assignedBy = new StringFilter();
        }
        return assignedBy;
    }

    public void setAssignedBy(StringFilter assignedBy) {
        this.assignedBy = assignedBy;
    }

    public InstantFilter getAssignedOn() {
        return assignedOn;
    }

    public InstantFilter assignedOn() {
        if (assignedOn == null) {
            assignedOn = new InstantFilter();
        }
        return assignedOn;
    }

    public void setAssignedOn(InstantFilter assignedOn) {
        this.assignedOn = assignedOn;
    }

    public InstantFilter getCreatedOn() {
        return createdOn;
    }

    public InstantFilter createdOn() {
        if (createdOn == null) {
            createdOn = new InstantFilter();
        }
        return createdOn;
    }

    public void setCreatedOn(InstantFilter createdOn) {
        this.createdOn = createdOn;
    }

    public DakStatusFilter getDakStatus() {
        return dakStatus;
    }

    public DakStatusFilter dakStatus() {
        if (dakStatus == null) {
            dakStatus = new DakStatusFilter();
        }
        return dakStatus;
    }

    public void setDakStatus(DakStatusFilter dakStatus) {
        this.dakStatus = dakStatus;
    }

    public BooleanFilter getStatus() {
        return status;
    }

    public BooleanFilter status() {
        if (status == null) {
            status = new BooleanFilter();
        }
        return status;
    }

    public void setStatus(BooleanFilter status) {
        this.status = status;
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

    public LongFilter getDakMasterId() {
        return dakMasterId;
    }

    public LongFilter dakMasterId() {
        if (dakMasterId == null) {
            dakMasterId = new LongFilter();
        }
        return dakMasterId;
    }

    public void setDakMasterId(LongFilter dakMasterId) {
        this.dakMasterId = dakMasterId;
    }

    public LongFilter getSecurityUserId() {
        return securityUserId;
    }

    public LongFilter securityUserId() {
        if (securityUserId == null) {
            securityUserId = new LongFilter();
        }
        return securityUserId;
    }

    public void setSecurityUserId(LongFilter securityUserId) {
        this.securityUserId = securityUserId;
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
        final DakHistoryCriteria that = (DakHistoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(assignedBy, that.assignedBy) &&
            Objects.equals(assignedOn, that.assignedOn) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(dakStatus, that.dakStatus) &&
            Objects.equals(status, that.status) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(dakMasterId, that.dakMasterId) &&
            Objects.equals(securityUserId, that.securityUserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            date,
            assignedBy,
            assignedOn,
            createdOn,
            dakStatus,
            status,
            lastModified,
            lastModifiedBy,
            dakMasterId,
            securityUserId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DakHistoryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (assignedBy != null ? "assignedBy=" + assignedBy + ", " : "") +
            (assignedOn != null ? "assignedOn=" + assignedOn + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (dakStatus != null ? "dakStatus=" + dakStatus + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (dakMasterId != null ? "dakMasterId=" + dakMasterId + ", " : "") +
            (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
