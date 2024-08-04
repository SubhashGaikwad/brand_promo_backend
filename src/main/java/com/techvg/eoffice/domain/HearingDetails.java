package com.techvg.eoffice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.techvg.eoffice.domain.enumeration.DakStatus;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HearingDetails.
 */
@Entity
@Table(name = "hearing_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HearingDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "accuser_name")
    private String accuserName;

    @Column(name = "order_date")
    private Instant orderDate;

    @Column(name = "respondent_name")
    private String respondentName;

    @Column(name = "comment")
    private String comment;

    @Column(name = "date")
    private Instant date;

    @Column(name = "time")
    private String time;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DakStatus status;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @ManyToOne
    // @JsonManagedReference
    @JsonIgnoreProperties(value = { "organization", "securityUsers" }, allowSetters = true)
    private DakMaster dakMaster;

    @ManyToOne
    // @JsonManagedReference
    @JsonIgnoreProperties(value = { "organization", "securityRole", "securityPermission", "dakMaster" }, allowSetters = true)
    private SecurityUser securityUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HearingDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccuserName() {
        return this.accuserName;
    }

    public HearingDetails accuserName(String accuserName) {
        this.setAccuserName(accuserName);
        return this;
    }

    public void setAccuserName(String accuserName) {
        this.accuserName = accuserName;
    }

    public Instant getOrderDate() {
        return this.orderDate;
    }

    public HearingDetails orderDate(Instant orderDate) {
        this.setOrderDate(orderDate);
        return this;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public String getRespondentName() {
        return this.respondentName;
    }

    public HearingDetails respondentName(String respondentName) {
        this.setRespondentName(respondentName);
        return this;
    }

    public void setRespondentName(String respondentName) {
        this.respondentName = respondentName;
    }

    public String getComment() {
        return this.comment;
    }

    public HearingDetails comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getDate() {
        return this.date;
    }

    public HearingDetails date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public HearingDetails time(String time) {
        this.setTime(time);
        return this;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DakStatus getStatus() {
        return this.status;
    }

    public HearingDetails status(DakStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(DakStatus status) {
        this.status = status;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public HearingDetails lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public HearingDetails lastModifiedBy(String lastModifiedBy) {
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

    public HearingDetails dakMaster(DakMaster dakMaster) {
        this.setDakMaster(dakMaster);
        return this;
    }

    public SecurityUser getSecurityUser() {
        return this.securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public HearingDetails securityUser(SecurityUser securityUser) {
        this.setSecurityUser(securityUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HearingDetails)) {
            return false;
        }
        return id != null && id.equals(((HearingDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HearingDetails{" +
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
            "}";
    }
}
