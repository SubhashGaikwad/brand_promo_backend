package com.techvg.eoffice.service.criteria;

import com.techvg.eoffice.domain.enumeration.DakStatus;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.techvg.eoffice.domain.HearingDetails} entity. This class is used
 * in {@link com.techvg.eoffice.web.rest.HearingDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /hearing-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class HearingDetailsCriteria implements Serializable, Criteria {

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

    private StringFilter accuserName;

    private InstantFilter orderDate;

    private StringFilter respondentName;

    private StringFilter comment;

    private InstantFilter date;

    private StringFilter time;

    private DakStatusFilter status;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter dakMasterId;

    private LongFilter securityUserId;

    private Boolean distinct;

    public HearingDetailsCriteria() {}

    public HearingDetailsCriteria(HearingDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.accuserName = other.accuserName == null ? null : other.accuserName.copy();
        this.orderDate = other.orderDate == null ? null : other.orderDate.copy();
        this.respondentName = other.respondentName == null ? null : other.respondentName.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.time = other.time == null ? null : other.time.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.dakMasterId = other.dakMasterId == null ? null : other.dakMasterId.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public HearingDetailsCriteria copy() {
        return new HearingDetailsCriteria(this);
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

    public StringFilter getAccuserName() {
        return accuserName;
    }

    public StringFilter accuserName() {
        if (accuserName == null) {
            accuserName = new StringFilter();
        }
        return accuserName;
    }

    public void setAccuserName(StringFilter accuserName) {
        this.accuserName = accuserName;
    }

    public InstantFilter getOrderDate() {
        return orderDate;
    }

    public InstantFilter orderDate() {
        if (orderDate == null) {
            orderDate = new InstantFilter();
        }
        return orderDate;
    }

    public void setOrderDate(InstantFilter orderDate) {
        this.orderDate = orderDate;
    }

    public StringFilter getRespondentName() {
        return respondentName;
    }

    public StringFilter respondentName() {
        if (respondentName == null) {
            respondentName = new StringFilter();
        }
        return respondentName;
    }

    public void setRespondentName(StringFilter respondentName) {
        this.respondentName = respondentName;
    }

    public StringFilter getComment() {
        return comment;
    }

    public StringFilter comment() {
        if (comment == null) {
            comment = new StringFilter();
        }
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
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

    public StringFilter getTime() {
        return time;
    }

    public StringFilter time() {
        if (time == null) {
            time = new StringFilter();
        }
        return time;
    }

    public void setTime(StringFilter time) {
        this.time = time;
    }

    public DakStatusFilter getStatus() {
        return status;
    }

    public DakStatusFilter status() {
        if (status == null) {
            status = new DakStatusFilter();
        }
        return status;
    }

    public void setStatus(DakStatusFilter status) {
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
        final HearingDetailsCriteria that = (HearingDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(accuserName, that.accuserName) &&
            Objects.equals(orderDate, that.orderDate) &&
            Objects.equals(respondentName, that.respondentName) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(date, that.date) &&
            Objects.equals(time, that.time) &&
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
            accuserName,
            orderDate,
            respondentName,
            comment,
            date,
            time,
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
        return "HearingDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (accuserName != null ? "accuserName=" + accuserName + ", " : "") +
            (orderDate != null ? "orderDate=" + orderDate + ", " : "") +
            (respondentName != null ? "respondentName=" + respondentName + ", " : "") +
            (comment != null ? "comment=" + comment + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (time != null ? "time=" + time + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (dakMasterId != null ? "dakMasterId=" + dakMasterId + ", " : "") +
            (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
