package com.techvg.eoffice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.techvg.eoffice.domain.enumeration.DakStatus;
import com.techvg.eoffice.domain.enumeration.LetterType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * A DakMaster.
 */
@Entity
@Table(name = "dak_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DakMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "assigned_date")
    private Instant assignedDate;

    @Column(name = "await_reason")
    private String awaitReason;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_status")
    private DakStatus currentStatus;

    @Column(name = "dispatched_date")
    private Instant dispatchDate;

    @Column(name = "letter_date")
    private Instant letterDate;

    @Column(name = "letter_received_date")
    private Instant letterReceivedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "letter_type")
    private LetterType letterType;

    @Column(name = "response_received")
    private Boolean isResponseReceived;

    @Column(name = "sender_address")
    private String senderAddress;

    @Column(name = "sender_email")
    private String senderEmail;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "status")
    private Boolean letterStatus;

    @Column(name = "subject")
    private String subject;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @JsonIgnore
    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "dak_assign_from")
    private SecurityUser dakAssignedFrom;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    @JoinColumn(name = "dak_assignee")
    private SecurityUser dakAssignee;

    @JsonIgnore
    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "dispatched_by")
    private SecurityUser dispatchedBy;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonManagedReference
    @JoinColumn(name = "user_id")
    private SecurityUser securityUsers;

    @Column(name = "sender_outward")
    private String senderOutward;

    @Column(name = "taluka")
    private String taluka;

    @Column(name = "outward_number")
    private String outwardNumber;

    @Column(name = "inward_number")
    private String inwardNumber;

    @ManyToOne
    private Organization organization;

    @OneToMany(mappedBy = "dakMaster")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dakMaster" }, allowSetters = true)
    private Set<HearingDetails> hearingDetails = new HashSet<>();

    // @ManyToMany
    // @JoinTable(
    // name = "rel_dak_master__security_user",
    // joinColumns = @JoinColumn(name = "dak_master_id"),
    // inverseJoinColumns = @JoinColumn(name = "security_user_id")
    // )
    // @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    // @JsonIgnoreProperties(value = { "organization", "securityPermissions",
    // "securityRoles", "dakMasters" }, allowSetters = true)
    // private Set<SecurityUser> securityUsers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DakMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInwardNumber() {
        return this.inwardNumber;
    }

    public DakMaster inwardNumber(String inwardNumber) {
        this.setInwardNumber(inwardNumber);
        return this;
    }

    public void setInwardNumber(String inwardNumber) {
        this.inwardNumber = inwardNumber;
    }

    public String getSenderName() {
        return this.senderName;
    }

    public DakMaster senderName(String senderName) {
        this.setSenderName(senderName);
        return this;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getContactNumber() {
        return this.contactNumber;
    }

    public DakMaster contactNumber(String contactNumber) {
        this.setContactNumber(contactNumber);
        return this;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getSenderAddress() {
        return this.senderAddress;
    }

    public DakMaster senderAddress(String senderAddress) {
        this.setSenderAddress(senderAddress);
        return this;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderEmail() {
        return this.senderEmail;
    }

    public DakMaster senderEmail(String senderEmail) {
        this.setSenderEmail(senderEmail);
        return this;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSubject() {
        return this.subject;
    }

    public DakMaster subject(String subject) {
        this.setSubject(subject);
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Instant getLetterDate() {
        return this.letterDate;
    }

    public DakMaster letterDate(Instant letterDate) {
        this.setLetterDate(letterDate);
        return this;
    }

    public void setLetterDate(Instant letterDate) {
        this.letterDate = letterDate;
    }

    public DakStatus getCurrentStatus() {
        return this.currentStatus;
    }

    public DakMaster currentStatus(DakStatus currentStatus) {
        this.setCurrentStatus(currentStatus);
        return this;
    }

    public void setCurrentStatus(DakStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Boolean getLetterStatus() {
        return this.letterStatus;
    }

    public DakMaster letterStatus(Boolean letterStatus) {
        this.setLetterStatus(letterStatus);
        return this;
    }

    public void setLetterStatus(Boolean letterStatus) {
        this.letterStatus = letterStatus;
    }

    public Instant getLetterReceivedDate() {
        return this.letterReceivedDate;
    }

    public DakMaster letterReceivedDate(Instant letterReceivedDate) {
        this.setLetterReceivedDate(letterReceivedDate);
        return this;
    }

    public void setLetterReceivedDate(Instant letterReceivedDate) {
        this.letterReceivedDate = letterReceivedDate;
    }

    public String getAwaitReason() {
        return this.awaitReason;
    }

    public DakMaster awaitReason(String awaitReason) {
        this.setAwaitReason(awaitReason);
        return this;
    }

    public void setAwaitReason(String awaitReason) {
        this.awaitReason = awaitReason;
    }

    public Instant getDispatchDate() {
        return this.dispatchDate;
    }

    public DakMaster dispatchDate(Instant dispatchDate) {
        this.setDispatchDate(dispatchDate);
        return this;
    }

    public void setDispatchDate(Instant dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public DakMaster createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LetterType getLetterType() {
        return this.letterType;
    }

    public DakMaster letterType(LetterType letterType) {
        this.setLetterType(letterType);
        return this;
    }

    public void setLetterType(LetterType letterType) {
        this.letterType = letterType;
    }

    public Boolean getIsResponseReceived() {
        return this.isResponseReceived;
    }

    public DakMaster isResponseReceived(Boolean isResponseReceived) {
        this.setIsResponseReceived(isResponseReceived);
        return this;
    }

    public void setIsResponseReceived(Boolean isResponseReceived) {
        this.isResponseReceived = isResponseReceived;
    }

    public Instant getAssignedDate() {
        return this.assignedDate;
    }

    public DakMaster assignedDate(Instant assignedDate) {
        this.setAssignedDate(assignedDate);
        return this;
    }

    public void setAssignedDate(Instant assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Instant getUpdatedOn() {
        return this.updatedOn;
    }

    public DakMaster updatedOn(Instant updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public DakMaster updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public SecurityUser getDakAssignedFrom() {
        return this.dakAssignedFrom;
    }

    public DakMaster dakAssignedFrom(SecurityUser dakAssignedFrom) {
        this.setDakAssignedFrom(dakAssignedFrom);
        return this;
    }

    public void setDakAssignedFrom(SecurityUser dakAssignedFrom) {
        this.dakAssignedFrom = dakAssignedFrom;
    }

    public SecurityUser getDakAssignee() {
        return this.dakAssignee;
    }

    public DakMaster dakAssignee(SecurityUser dakAssignee) {
        this.setDakAssignee(dakAssignee);
        return this;
    }

    public void setDakAssignee(SecurityUser dakAssignee) {
        this.dakAssignee = dakAssignee;
    }

    public SecurityUser getDispatchedBy() {
        return this.dispatchedBy;
    }

    public DakMaster dispatchedBy(SecurityUser dispatchedBy) {
        this.setDispatchedBy(dispatchedBy);
        return this;
    }

    public void setDispatchedBy(SecurityUser dispatchedBy) {
        this.dispatchedBy = dispatchedBy;
    }

    public SecurityUser getSecurityUsers() {
        return this.securityUsers;
    }

    public DakMaster securityUsers(SecurityUser securityUsers) {
        this.setSecurityUsers(securityUsers);
        return this;
    }

    public void setSecurityUsers(SecurityUser securityUsers) {
        this.securityUsers = securityUsers;
    }

    public String getSenderOutward() {
        return this.senderOutward;
    }

    public DakMaster senderOutward(String senderOutward) {
        this.setSenderOutward(senderOutward);
        return this;
    }

    public void setSenderOutward(String senderOutward) {
        this.senderOutward = senderOutward;
    }

    public String getOutwardNumber() {
        return this.outwardNumber;
    }

    public DakMaster outwardNumber(String outwardNumber) {
        this.setOutwardNumber(outwardNumber);
        return this;
    }

    public void setOutwardNumber(String outwardNumber) {
        this.outwardNumber = outwardNumber;
    }

    public String getTaluka() {
        return this.taluka;
    }

    public DakMaster taluka(String taluka) {
        this.setTaluka(taluka);
        return this;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public DakMaster organization(Organization organization) {
        this.setOrganization(organization);
        return this;
    }

    public Set<HearingDetails> getHearingDetails() {
        return this.hearingDetails;
    }

    public void setHearingDetails(Set<HearingDetails> hearingDetails) {
        if (this.hearingDetails != null) {
            this.hearingDetails.forEach(i -> i.setDakMaster(null));
        }
        if (hearingDetails != null) {
            hearingDetails.forEach(i -> i.setDakMaster(this));
        }
        this.hearingDetails = hearingDetails;
    }

    public DakMaster hearingDetails(Set<HearingDetails> hearingDetails) {
        this.setHearingDetails(hearingDetails);
        return this;
    }

    public DakMaster addHearingDetails(HearingDetails hearingDetails) {
        this.hearingDetails.add(hearingDetails);
        hearingDetails.setDakMaster(this);
        return this;
    }

    public DakMaster removeHearingDetails(HearingDetails hearingDetails) {
        this.hearingDetails.remove(hearingDetails);
        hearingDetails.setDakMaster(null);
        return this;
    }

    // public Set<SecurityUser> getSecurityUsers() {
    // return this.securityUsers;
    // }
    //
    // public void setSecurityUsers(Set<SecurityUser> securityUsers) {
    // this.securityUsers = securityUsers;
    // }
    //
    // public DakMaster securityUsers(Set<SecurityUser> securityUsers) {
    // this.setSecurityUsers(securityUsers);
    // return this;
    // }
    //
    // public DakMaster addSecurityUser(SecurityUser securityUser) {
    // this.securityUsers.add(securityUser);
    // securityUser.getDakMasters().add(this);
    // return this;
    // }
    //
    // public DakMaster removeSecurityUser(SecurityUser securityUser) {
    // this.securityUsers.remove(securityUser);
    // securityUser.getDakMasters().remove(this);
    // return this;
    // }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DakMaster)) {
            return false;
        }
        return id != null && id.equals(((DakMaster) o).id);
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
		return "DakMaster{" + "id=" + getId() + ", inwardNumber='" + getInwardNumber() + "'" + ", senderName='"
				+ getSenderName() + "'" + ", contactNumber='" + getContactNumber() + "'" + ", senderAddress='"
				+ getSenderAddress() + "'" + ", senderEmail='" + getSenderEmail() + "'" + ", subject='" + getSubject()
				+ "'" + ", letterDate='" + getLetterDate() + "'" + ", currentStatus='" + getCurrentStatus() + "'"
				+ ", letterStatus='" + getLetterStatus() + "'" + ", letterReceivedDate='" + getLetterReceivedDate()
				+ "'" + ", awaitReason='" + getAwaitReason() + "'" + ", dispatchDate='" + getDispatchDate() + "'"
				+ ", createdBy='" + getCreatedBy() + "'" + ", createdOn='" + getCreatedOn() + "'" + ", letterType='"
				+ getLetterType() + "'" + ", isResponseReceived='" + getIsResponseReceived() + "'" + ", assignedDate='"
				+ getAssignedDate() + "'" + ", updatedOn='" + getUpdatedOn() + "'" + ", updatedBy='" + getUpdatedBy()
				+ "'" + ", dakAssignedFrom='" + getDakAssignedFrom() + "'" + ", dakAssignee='" + getDakAssignee() + "'"
				+ ", dispatchedBy='" + getDispatchedBy() + "'" + ", senderOutward='" + getSenderOutward() + "'"
				+ ", outwardNumber='" + getOutwardNumber() + "'" + ", taluka='" + getTaluka() + "'" + "}";
	}

    public Instant getCreatedOn() {
        return createdOn;
    }

    public DakMaster createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }
}
