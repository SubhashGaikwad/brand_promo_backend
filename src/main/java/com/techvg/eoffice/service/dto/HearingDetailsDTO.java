package com.techvg.eoffice.service.dto;

import com.techvg.eoffice.domain.DakMaster;
import com.techvg.eoffice.domain.enumeration.DakStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.eoffice.domain.HearingDetails} entity.
 */
public class HearingDetailsDTO implements Serializable {

    private Long id;

    private String accuserName;

    private Instant orderDate;

    private String respondentName;

    private String comment;

    private Instant date;

    private String time;

    private DakStatus status;

    private Instant lastModified;

    private String lastModifiedBy;

    private Long dakMasterId;

    private SecurityUserDTO securityUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccuserName() {
        return accuserName;
    }

    public void setAccuserName(String accuserName) {
        this.accuserName = accuserName;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public String getRespondentName() {
        return respondentName;
    }

    public void setRespondentName(String respondentName) {
        this.respondentName = respondentName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DakStatus getStatus() {
        return status;
    }

    public void setStatus(DakStatus status) {
        this.status = status;
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

    public Long getDakMasterId() {
        return dakMasterId;
    }

    public void setDakMasterId(Long dakMasterId) {
        this.dakMasterId = dakMasterId;
    }

    public SecurityUserDTO getSecurityUser() {
        return securityUser;
    }

    public void setSecurityUser(SecurityUserDTO securityUser) {
        this.securityUser = securityUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HearingDetailsDTO)) {
            return false;
        }

        HearingDetailsDTO hearingDetailsDTO = (HearingDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hearingDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HearingDetailsDTO{" +
            "id=" + getId() +
            ", accuserName='" + getAccuserName() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            ", respondentName='" + getRespondentName() + "'" +
            ", comment='" + getComment() + "'" +
            ", date='" + getDate() + "'" +
            ", time='" + getTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", dakMasterId=" + getDakMasterId() +
            ", securityUser=" + getSecurityUser() +
            
            "}";
    }
}
