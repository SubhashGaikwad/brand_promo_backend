package com.techvg.eoffice.service.criteria;

import com.techvg.eoffice.domain.enumeration.DakStatus;
import com.techvg.eoffice.domain.enumeration.LetterType;
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
 * Criteria class for the {@link com.techvg.eoffice.domain.DakMaster} entity. This class is used
 * in {@link com.techvg.eoffice.web.rest.DakMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dak-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class DakMasterCriteria implements Serializable, Criteria {

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

    /**
     * Class for filtering LetterType
     */
    public static class LetterTypeFilter extends Filter<LetterType> {

        public LetterTypeFilter() {}

        public LetterTypeFilter(LetterTypeFilter filter) {
            super(filter);
        }

        @Override
        public LetterTypeFilter copy() {
            return new LetterTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter inwardNumber;

    private StringFilter senderName;

    private StringFilter contactNumber;

    private StringFilter senderAddress;

    private StringFilter senderEmail;

    private StringFilter subject;

    private InstantFilter letterDate;

    private DakStatusFilter currentStatus;

    private BooleanFilter letterStatus;

    private InstantFilter letterReceivedDate;

    private StringFilter awaitReason;

    private InstantFilter dispatchDate;

    private StringFilter createdBy;

    private LetterTypeFilter letterType;

    private BooleanFilter isResponseReceived;

    private InstantFilter assignedDate;

    private InstantFilter updatedOn;

    private StringFilter updatedBy;

    private LongFilter dakAssignedFrom;

    private LongFilter dakAssignee;

    private LongFilter dispatchedBy;

    private StringFilter senderOutward;

    private StringFilter outwardNumber;

    private StringFilter taluka;

    private LongFilter organizationId;

    private LongFilter securityUserId;

    private Boolean distinct;

    public DakMasterCriteria() {}

    public DakMasterCriteria(DakMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.inwardNumber = other.inwardNumber == null ? null : other.inwardNumber.copy();
        this.senderName = other.senderName == null ? null : other.senderName.copy();
        this.contactNumber = other.contactNumber == null ? null : other.contactNumber.copy();
        this.senderAddress = other.senderAddress == null ? null : other.senderAddress.copy();
        this.senderEmail = other.senderEmail == null ? null : other.senderEmail.copy();
        this.subject = other.subject == null ? null : other.subject.copy();
        this.letterDate = other.letterDate == null ? null : other.letterDate.copy();
        this.currentStatus = other.currentStatus == null ? null : other.currentStatus.copy();
        this.letterStatus = other.letterStatus == null ? null : other.letterStatus.copy();
        this.letterReceivedDate = other.letterReceivedDate == null ? null : other.letterReceivedDate.copy();
        this.awaitReason = other.awaitReason == null ? null : other.awaitReason.copy();
        this.dispatchDate = other.dispatchDate == null ? null : other.dispatchDate.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.letterType = other.letterType == null ? null : other.letterType.copy();
        this.isResponseReceived = other.isResponseReceived == null ? null : other.isResponseReceived.copy();
        this.assignedDate = other.assignedDate == null ? null : other.assignedDate.copy();
        this.updatedOn = other.updatedOn == null ? null : other.updatedOn.copy();
        this.updatedBy = other.updatedBy == null ? null : other.updatedBy.copy();
        this.dakAssignedFrom = other.dakAssignedFrom == null ? null : other.dakAssignedFrom.copy();
        this.dakAssignee = other.dakAssignee == null ? null : other.dakAssignee.copy();
        this.dispatchedBy = other.dispatchedBy == null ? null : other.dispatchedBy.copy();
        this.senderOutward = other.senderOutward == null ? null : other.senderOutward.copy();
        this.outwardNumber = other.outwardNumber == null ? null : other.outwardNumber.copy();
        this.taluka = other.taluka == null ? null : other.taluka.copy();
        this.organizationId = other.organizationId == null ? null : other.organizationId.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DakMasterCriteria copy() {
        return new DakMasterCriteria(this);
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

    public StringFilter getInwardNumber() {
        return inwardNumber;
    }

    public StringFilter inwardNumber() {
        if (inwardNumber == null) {
            inwardNumber = new StringFilter();
        }
        return inwardNumber;
    }

    public void setInwardNumber(StringFilter inwardNumber) {
        this.inwardNumber = inwardNumber;
    }

    public StringFilter getSenderName() {
        return senderName;
    }

    public StringFilter senderName() {
        if (senderName == null) {
            senderName = new StringFilter();
        }
        return senderName;
    }

    public void setSenderName(StringFilter senderName) {
        this.senderName = senderName;
    }

    public StringFilter getContactNumber() {
        return contactNumber;
    }

    public StringFilter contactNumber() {
        if (contactNumber == null) {
            contactNumber = new StringFilter();
        }
        return contactNumber;
    }

    public void setContactNumber(StringFilter contactNumber) {
        this.contactNumber = contactNumber;
    }

    public StringFilter getSenderAddress() {
        return senderAddress;
    }

    public StringFilter senderAddress() {
        if (senderAddress == null) {
            senderAddress = new StringFilter();
        }
        return senderAddress;
    }

    public void setSenderAddress(StringFilter senderAddress) {
        this.senderAddress = senderAddress;
    }

    public StringFilter getSenderEmail() {
        return senderEmail;
    }

    public StringFilter senderEmail() {
        if (senderEmail == null) {
            senderEmail = new StringFilter();
        }
        return senderEmail;
    }

    public void setSenderEmail(StringFilter senderEmail) {
        this.senderEmail = senderEmail;
    }

    public StringFilter getSubject() {
        return subject;
    }

    public StringFilter subject() {
        if (subject == null) {
            subject = new StringFilter();
        }
        return subject;
    }

    public void setSubject(StringFilter subject) {
        this.subject = subject;
    }

    public InstantFilter getLetterDate() {
        return letterDate;
    }

    public InstantFilter letterDate() {
        if (letterDate == null) {
            letterDate = new InstantFilter();
        }
        return letterDate;
    }

    public void setLetterDate(InstantFilter letterDate) {
        this.letterDate = letterDate;
    }

    public DakStatusFilter getCurrentStatus() {
        return currentStatus;
    }

    public DakStatusFilter currentStatus() {
        if (currentStatus == null) {
            currentStatus = new DakStatusFilter();
        }
        return currentStatus;
    }

    public void setCurrentStatus(DakStatusFilter currentStatus) {
        this.currentStatus = currentStatus;
    }

    public BooleanFilter getLetterStatus() {
        return letterStatus;
    }

    public BooleanFilter letterStatus() {
        if (letterStatus == null) {
            letterStatus = new BooleanFilter();
        }
        return letterStatus;
    }

    public void setLetterStatus(BooleanFilter letterStatus) {
        this.letterStatus = letterStatus;
    }

    public InstantFilter getLetterReceivedDate() {
        return letterReceivedDate;
    }

    public InstantFilter letterReceivedDate() {
        if (letterReceivedDate == null) {
            letterReceivedDate = new InstantFilter();
        }
        return letterReceivedDate;
    }

    public void setLetterReceivedDate(InstantFilter letterReceivedDate) {
        this.letterReceivedDate = letterReceivedDate;
    }

    public StringFilter getAwaitReason() {
        return awaitReason;
    }

    public StringFilter awaitReason() {
        if (awaitReason == null) {
            awaitReason = new StringFilter();
        }
        return awaitReason;
    }

    public void setAwaitReason(StringFilter awaitReason) {
        this.awaitReason = awaitReason;
    }

    public InstantFilter getDispatchDate() {
        return dispatchDate;
    }

    public InstantFilter dispatchDate() {
        if (dispatchDate == null) {
            dispatchDate = new InstantFilter();
        }
        return dispatchDate;
    }

    public void setDispatchDate(InstantFilter dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public LetterTypeFilter getLetterType() {
        return letterType;
    }

    public LetterTypeFilter letterType() {
        if (letterType == null) {
            letterType = new LetterTypeFilter();
        }
        return letterType;
    }

    public void setLetterType(LetterTypeFilter letterType) {
        this.letterType = letterType;
    }

    public BooleanFilter getIsResponseReceived() {
        return isResponseReceived;
    }

    public BooleanFilter isResponseReceived() {
        if (isResponseReceived == null) {
            isResponseReceived = new BooleanFilter();
        }
        return isResponseReceived;
    }

    public void setIsResponseReceived(BooleanFilter isResponseReceived) {
        this.isResponseReceived = isResponseReceived;
    }

    public InstantFilter getAssignedDate() {
        return assignedDate;
    }

    public InstantFilter assignedDate() {
        if (assignedDate == null) {
            assignedDate = new InstantFilter();
        }
        return assignedDate;
    }

    public void setAssignedDate(InstantFilter assignedDate) {
        this.assignedDate = assignedDate;
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

    public StringFilter getUpdatedBy() {
        return updatedBy;
    }

    public StringFilter updatedBy() {
        if (updatedBy == null) {
            updatedBy = new StringFilter();
        }
        return updatedBy;
    }

    public void setUpdatedBy(StringFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LongFilter getDakAssignedFrom() {
        return dakAssignedFrom;
    }

    public LongFilter dakAssignedFrom() {
        if (dakAssignedFrom == null) {
            dakAssignedFrom = new LongFilter();
        }
        return dakAssignedFrom;
    }

    public void setDakAssignedFrom(LongFilter dakAssignedFrom) {
        this.dakAssignedFrom = dakAssignedFrom;
    }

    public LongFilter getDakAssignee() {
        return dakAssignee;
    }

    public LongFilter dakAssignee() {
        if (dakAssignee == null) {
            dakAssignee = new LongFilter();
        }
        return dakAssignee;
    }

    public void setDakAssignee(LongFilter dakAssignee) {
        this.dakAssignee = dakAssignee;
    }

    public LongFilter getDispatchedBy() {
        return dispatchedBy;
    }

    public LongFilter dispatchedBy() {
        if (dispatchedBy == null) {
            dispatchedBy = new LongFilter();
        }
        return dispatchedBy;
    }

    public void setDispatchedBy(LongFilter dispatchedBy) {
        this.dispatchedBy = dispatchedBy;
    }

    public StringFilter getSenderOutward() {
        return senderOutward;
    }

    public StringFilter senderOutward() {
        if (senderOutward == null) {
            senderOutward = new StringFilter();
        }
        return senderOutward;
    }

    public void setSenderOutward(StringFilter senderOutward) {
        this.senderOutward = senderOutward;
    }

    public StringFilter getOutwardNumber() {
        return outwardNumber;
    }

    public StringFilter outwardNumber() {
        if (outwardNumber == null) {
            outwardNumber = new StringFilter();
        }
        return outwardNumber;
    }

    public void setOutwardNumber(StringFilter outwardNumber) {
        this.outwardNumber = outwardNumber;
    }

    public StringFilter getTaluka() {
        return taluka;
    }

    public StringFilter taluka() {
        if (taluka == null) {
            taluka = new StringFilter();
        }
        return taluka;
    }

    public void setTaluka(StringFilter taluka) {
        this.taluka = taluka;
    }

    public LongFilter getOrganizationId() {
        return organizationId;
    }

    public LongFilter organizationId() {
        if (organizationId == null) {
            organizationId = new LongFilter();
        }
        return organizationId;
    }

    public void setOrganizationId(LongFilter organizationId) {
        this.organizationId = organizationId;
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
        final DakMasterCriteria that = (DakMasterCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(inwardNumber, that.inwardNumber) &&
            Objects.equals(senderName, that.senderName) &&
            Objects.equals(contactNumber, that.contactNumber) &&
            Objects.equals(senderAddress, that.senderAddress) &&
            Objects.equals(senderEmail, that.senderEmail) &&
            Objects.equals(subject, that.subject) &&
            Objects.equals(letterDate, that.letterDate) &&
            Objects.equals(currentStatus, that.currentStatus) &&
            Objects.equals(letterStatus, that.letterStatus) &&
            Objects.equals(letterReceivedDate, that.letterReceivedDate) &&
            Objects.equals(awaitReason, that.awaitReason) &&
            Objects.equals(dispatchDate, that.dispatchDate) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(letterType, that.letterType) &&
            Objects.equals(isResponseReceived, that.isResponseReceived) &&
            Objects.equals(assignedDate, that.assignedDate) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(dakAssignedFrom, that.dakAssignedFrom) &&
            Objects.equals(dakAssignee, that.dakAssignee) &&
            Objects.equals(dispatchedBy, that.dispatchedBy) &&
            Objects.equals(senderOutward, that.senderOutward) &&
            Objects.equals(outwardNumber, that.outwardNumber) &&
            Objects.equals(taluka, that.taluka) &&
            Objects.equals(organizationId, that.organizationId) &&
            Objects.equals(securityUserId, that.securityUserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            inwardNumber,
            senderName,
            contactNumber,
            senderAddress,
            senderEmail,
            subject,
            letterDate,
            currentStatus,
            letterStatus,
            letterReceivedDate,
            awaitReason,
            dispatchDate,
            createdBy,
            letterType,
            isResponseReceived,
            assignedDate,
            updatedOn,
            updatedBy,
            dakAssignedFrom,
            dakAssignee,
            dispatchedBy,
            senderOutward,
            outwardNumber,
            taluka,
            organizationId,
            securityUserId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DakMasterCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (inwardNumber != null ? "inwardNumber=" + inwardNumber + ", " : "") +
            (senderName != null ? "senderName=" + senderName + ", " : "") +
            (contactNumber != null ? "contactNumber=" + contactNumber + ", " : "") +
            (senderAddress != null ? "senderAddress=" + senderAddress + ", " : "") +
            (senderEmail != null ? "senderEmail=" + senderEmail + ", " : "") +
            (subject != null ? "subject=" + subject + ", " : "") +
            (letterDate != null ? "letterDate=" + letterDate + ", " : "") +
            (currentStatus != null ? "currentStatus=" + currentStatus + ", " : "") +
            (letterStatus != null ? "letterStatus=" + letterStatus + ", " : "") +
            (letterReceivedDate != null ? "letterReceivedDate=" + letterReceivedDate + ", " : "") +
            (awaitReason != null ? "awaitReason=" + awaitReason + ", " : "") +
            (dispatchDate != null ? "dispatchDate=" + dispatchDate + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (letterType != null ? "letterType=" + letterType + ", " : "") +
            (isResponseReceived != null ? "isResponseReceived=" + isResponseReceived + ", " : "") +
            (assignedDate != null ? "assignedDate=" + assignedDate + ", " : "") +
            (updatedOn != null ? "lastModified=" + updatedOn + ", " : "") +
            (updatedBy != null ? "lastModifiedBy=" + updatedBy + ", " : "") +
            (dakAssignedFrom != null ? "dakAssignedFrom=" + dakAssignedFrom + ", " : "") +
            (dakAssignee != null ? "dakAssignee=" + dakAssignee + ", " : "") +
            (dispatchedBy != null ? "dispatchedBy=" + dispatchedBy + ", " : "") +
            (senderOutward != null ? "senderOutward=" + senderOutward + ", " : "") +
            (outwardNumber != null ? "outwardNumber=" + outwardNumber + ", " : "") +
            (taluka != null ? "taluka=" + taluka + ", " : "") +
            (organizationId != null ? "organizationId=" + organizationId + ", " : "") +
            (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
