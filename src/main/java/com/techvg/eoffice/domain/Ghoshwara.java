package com.techvg.eoffice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.eoffice.domain.enumeration.RegisterType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ghoshwara.
 */
@Entity
@Table(name = "ghoshwara")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ghoshwara implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "register_type")
    private RegisterType registerType;

    @Column(name = "initial_pendings")
    private Integer initialPendings;

    @Column(name = "daily_pendings")
    private Integer dailyPendings;

    @Column(name = "current_week_inwards")
    private Integer currentWeekInwards;

    @Column(name = "total")
    private Integer total;

    @Column(name = "self_generated")
    private Integer selfGenerated;

    @Column(name = "current_week_cleared")
    private Integer currentWeekCleared;

    @Column(name = "current_week_pendings")
    private Integer currentWeekPendings;

    @Column(name = "first_week")
    private Integer firstWeek;

    @Column(name = "second_week")
    private Integer secondWeek;

    @Column(name = "third_week")
    private Integer thirdWeek;

    @Column(name = "first_month")
    private Integer firstMonth;

    @Column(name = "second_month")
    private Integer secondMonth;

    @Column(name = "third_month")
    private Integer thirdMonth;

    @Column(name = "within_six_months")
    private Integer withinSixMonths;

    @Column(name = "above_six_months")
    private Integer aboveSixMonths;

    @Column(name = "above_one_year")
    private Integer aboveOneYear;

    @Column(name = "date")
    private Instant date;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "organization", "securityPermissions", "securityRoles", "dakMasters" }, allowSetters = true)
    private SecurityUser securityUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ghoshwara id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RegisterType getRegisterType() {
        return this.registerType;
    }

    public Ghoshwara registerType(RegisterType registerType) {
        this.setRegisterType(registerType);
        return this;
    }

    public void setRegisterType(RegisterType registerType) {
        this.registerType = registerType;
    }

    public Integer getInitialPendings() {
        return this.initialPendings;
    }

    public Ghoshwara initialPendings(Integer initialPendings) {
        this.setInitialPendings(initialPendings);
        return this;
    }

    public void setInitialPendings(Integer initialPendings) {
        this.initialPendings = initialPendings;
    }

    public Integer getCurrentWeekInwards() {
        return this.currentWeekInwards;
    }

    public Ghoshwara currentWeekInwards(Integer currentWeekInwards) {
        this.setCurrentWeekInwards(currentWeekInwards);
        return this;
    }

    public void setCurrentWeekInwards(Integer currentWeekInwards) {
        this.currentWeekInwards = currentWeekInwards;
    }

    public Integer getTotal() {
        return this.total;
    }

    public Ghoshwara total(Integer total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSelfGenerated() {
        return this.selfGenerated;
    }

    public Ghoshwara selfGenerated(Integer selfGenerated) {
        this.setSelfGenerated(selfGenerated);
        return this;
    }

    public void setSelfGenerated(Integer selfGenerated) {
        this.selfGenerated = selfGenerated;
    }

    public Integer getCurrentWeekCleared() {
        return this.currentWeekCleared;
    }

    public Ghoshwara currentWeekCleared(Integer currentWeekCleared) {
        this.setCurrentWeekCleared(currentWeekCleared);
        return this;
    }

    public void setCurrentWeekCleared(Integer currentWeekCleared) {
        this.currentWeekCleared = currentWeekCleared;
    }

    public Integer getCurrentWeekPendings() {
        return this.currentWeekPendings;
    }

    public Ghoshwara currentWeekPendings(Integer currentWeekPendings) {
        this.setCurrentWeekPendings(currentWeekPendings);
        return this;
    }

    public void setCurrentWeekPendings(Integer currentWeekPendings) {
        this.currentWeekPendings = currentWeekPendings;
    }

    public Integer getFirstWeek() {
        return this.firstWeek;
    }

    public Ghoshwara firstWeek(Integer firstWeek) {
        this.setFirstWeek(firstWeek);
        return this;
    }

    public void setFirstWeek(Integer firstWeek) {
        this.firstWeek = firstWeek;
    }

    public Integer getSecondWeek() {
        return this.secondWeek;
    }

    public Ghoshwara secondWeek(Integer secondWeek) {
        this.setSecondWeek(secondWeek);
        return this;
    }

    public void setSecondWeek(Integer secondWeek) {
        this.secondWeek = secondWeek;
    }

    public Integer getThirdWeek() {
        return this.thirdWeek;
    }

    public Ghoshwara thirdWeek(Integer thirdWeek) {
        this.setThirdWeek(thirdWeek);
        return this;
    }

    public void setThirdWeek(Integer thirdWeek) {
        this.thirdWeek = thirdWeek;
    }

    public Integer getFirstMonth() {
        return this.firstMonth;
    }

    public Ghoshwara firstMonth(Integer firstMonth) {
        this.setFirstMonth(firstMonth);
        return this;
    }

    public void setFirstMonth(Integer firstMonth) {
        this.firstMonth = firstMonth;
    }

    public Integer getSecondMonth() {
        return this.secondMonth;
    }

    public Ghoshwara secondMonth(Integer secondMonth) {
        this.setSecondMonth(secondMonth);
        return this;
    }

    public void setSecondMonth(Integer secondMonth) {
        this.secondMonth = secondMonth;
    }

    public Integer getThirdMonth() {
        return this.thirdMonth;
    }

    public Ghoshwara thirdMonth(Integer thirdMonth) {
        this.setThirdMonth(thirdMonth);
        return this;
    }

    public void setThirdMonth(Integer thirdMonth) {
        this.thirdMonth = thirdMonth;
    }

    public Integer getWithinSixMonths() {
        return this.withinSixMonths;
    }

    public Ghoshwara withinSixMonths(Integer withinSixMonths) {
        this.setWithinSixMonths(withinSixMonths);
        return this;
    }

    public void setWithinSixMonths(Integer withinSixMonths) {
        this.withinSixMonths = withinSixMonths;
    }

    public Integer getAboveSixMonths() {
        return this.aboveSixMonths;
    }

    public Ghoshwara aboveSixMonths(Integer aboveSixMonths) {
        this.setAboveSixMonths(aboveSixMonths);
        return this;
    }

    public void setAboveSixMonths(Integer aboveSixMonths) {
        this.aboveSixMonths = aboveSixMonths;
    }

    public Instant getDate() {
        return this.date;
    }

    public Ghoshwara date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Ghoshwara lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Ghoshwara lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public SecurityUser getSecurityUser() {
        return this.securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public Ghoshwara securityUser(SecurityUser securityUser) {
        this.setSecurityUser(securityUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ghoshwara)) {
            return false;
        }
        return id != null && id.equals(((Ghoshwara) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ghoshwara{" +
            "id=" + getId() +
            ", registerType='" + getRegisterType() + "'" +
            ", initialPendings=" + getInitialPendings() +
            ", currentWeekInwards=" + getCurrentWeekInwards() +
            ", total=" + getTotal() +
            ", selfGenerated=" + getSelfGenerated() +
            ", currentWeekCleared=" + getCurrentWeekCleared() +
            ", currentWeekPendings=" + getCurrentWeekPendings() +
            ", firstWeek=" + getFirstWeek() +
            ", secondWeek=" + getSecondWeek() +
            ", thirdWeek=" + getThirdWeek() +
            ", firstMonth=" + getFirstMonth() +
            ", secondMonth=" + getSecondMonth() +
            ", thirdMonth=" + getThirdMonth() +
            ", withinSixMonths=" + getWithinSixMonths() +
            ", date='" + getDate() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }

    public Integer getDailyPendings() {
        return dailyPendings;
    }

    public Ghoshwara dailyPendings(Integer dailyPendings) {
        this.setDailyPendings(dailyPendings);
        return this;
    }

    public void setDailyPendings(Integer dailyPendings) {
        this.dailyPendings = dailyPendings;
    }

    public Integer getAboveOneYear() {
        return this.aboveOneYear;
    }

    public Ghoshwara aboveOneYear(Integer aboveOneYear) {
        this.setAboveOneYear(aboveOneYear);
        return this;
    }

    public void setAboveOneYear(Integer aboveOneYear) {
        this.aboveOneYear = aboveOneYear;
    }
}
