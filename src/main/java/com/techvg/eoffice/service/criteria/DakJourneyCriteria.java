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
 * Criteria class for the {@link com.techvg.eoffice.domain.DakJourney} entity. This class is used
 * in {@link com.techvg.eoffice.web.rest.DakJourneyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dak-journeys?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class DakJourneyCriteria implements Serializable, Criteria {

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

    private InstantFilter assignedOn;

    private InstantFilter updatedOn;

    private DakStatusFilter dakStatus;

    private BooleanFilter status;

    private StringFilter dakAssignedBy;

    private StringFilter dakAssignedTo;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter dakMasterId;

    private LongFilter securityUserId;

    private LongFilter commentMasterId;

    private Boolean distinct;

    public DakJourneyCriteria() {}

    public DakJourneyCriteria(DakJourneyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.assignedOn = other.assignedOn == null ? null : other.assignedOn.copy();
        this.updatedOn = other.updatedOn == null ? null : other.updatedOn.copy();
        this.dakStatus = other.dakStatus == null ? null : other.dakStatus.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.dakAssignedBy = other.dakAssignedBy == null ? null : other.dakAssignedBy.copy();
        this.dakAssignedTo = other.dakAssignedTo == null ? null : other.dakAssignedTo.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.dakMasterId = other.dakMasterId == null ? null : other.dakMasterId.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.commentMasterId = other.commentMasterId == null ? null : other.commentMasterId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DakJourneyCriteria copy() {
        return new DakJourneyCriteria(this);
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

    public InstantFilter getUpdatedOn() {
        return updatedOn;
    }

    public InstantFilter updatedOn() {
        if (updatedOn == null) {
            updatedOn = new InstantFilter();
        }
        return updatedOn;
    }

    public void setUpdatedOn(InstantFilter updatedOn) {
        this.updatedOn = updatedOn;
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

    public StringFilter getDakAssignedBy() {
        return dakAssignedBy;
    }

    public StringFilter dakAssignedBy() {
        if (dakAssignedBy == null) {
            dakAssignedBy = new StringFilter();
        }
        return dakAssignedBy;
    }

    public void setDakAssignedBy(StringFilter dakAssignedBy) {
        this.dakAssignedBy = dakAssignedBy;
    }

    public StringFilter getDakAssignedTo() {
        return dakAssignedTo;
    }

    public StringFilter dakAssignedTo() {
        if (dakAssignedTo == null) {
            dakAssignedTo = new StringFilter();
        }
        return dakAssignedTo;
    }

    public void setDakAssignedTo(StringFilter dakAssignedTo) {
        this.dakAssignedTo = dakAssignedTo;
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

    public LongFilter getCommentMasterId() {
        return commentMasterId;
    }

    public LongFilter commentMasterId() {
        if (commentMasterId == null) {
            commentMasterId = new LongFilter();
        }
        return commentMasterId;
    }

    public void setCommentMasterId(LongFilter commentMasterId) {
        this.commentMasterId = commentMasterId;
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
        final DakJourneyCriteria that = (DakJourneyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(assignedOn, that.assignedOn) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(dakStatus, that.dakStatus) &&
            Objects.equals(status, that.status) &&
            Objects.equals(dakAssignedBy, that.dakAssignedBy) &&
            Objects.equals(dakAssignedTo, that.dakAssignedTo) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(dakMasterId, that.dakMasterId) &&
            Objects.equals(securityUserId, that.securityUserId) &&
            Objects.equals(commentMasterId, that.commentMasterId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            assignedOn,
            updatedOn,
            dakStatus,
            status,
            dakAssignedBy,
            dakAssignedTo,
            lastModified,
            lastModifiedBy,
            dakMasterId,
            securityUserId,
            commentMasterId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DakJourneyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (assignedOn != null ? "assignedOn=" + assignedOn + ", " : "") +
            (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
            (dakStatus != null ? "dakStatus=" + dakStatus + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (dakAssignedBy != null ? "dakAssignedBy=" + dakAssignedBy + ", " : "") +
            (dakAssignedTo != null ? "dakAssignedTo=" + dakAssignedTo + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (dakMasterId != null ? "dakMasterId=" + dakMasterId + ", " : "") +
            (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "") +
            (commentMasterId != null ? "commentMasterId=" + commentMasterId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
