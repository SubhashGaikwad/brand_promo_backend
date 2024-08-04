package com.techvg.eoffice.service.dto;

import com.techvg.eoffice.domain.enumeration.RegisterType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.eoffice.domain.Ghoshwara} entity.
 */
public class GhoshwaraDTO implements Serializable {

    private Long id;

    private RegisterType registerType;

    private Integer initialPendings;

    private Integer dailyPendings;

    private Integer currentWeekInwards;

    private Integer total;

    private Integer selfGenerated;

    private Integer currentWeekCleared;

    private Integer currentWeekPendings;

    private Integer firstWeek;

    private Integer secondWeek;

    private Integer thirdWeek;

    private Integer firstMonth;

    private Integer secondMonth;

    private Integer thirdMonth;

    private Integer withinSixMonths;

    private Integer aboveSixMonths;

    private Integer aboveOneYear;

    private Instant date;

    private Instant lastModified;

    private String lastModifiedBy;

    private SecurityUserDTO securityUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RegisterType getRegisterType() {
        return registerType;
    }

    public void setRegisterType(RegisterType registerType) {
        this.registerType = registerType;
    }

    public Integer getInitialPendings() {
        return initialPendings;
    }

    public void setInitialPendings(Integer initialPendings) {
        this.initialPendings = initialPendings;
    }

    public Integer getCurrentWeekInwards() {
        return currentWeekInwards;
    }

    public void setCurrentWeekInwards(Integer currentWeekInwards) {
        this.currentWeekInwards = currentWeekInwards;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSelfGenerated() {
        return selfGenerated;
    }

    public void setSelfGenerated(Integer selfGenerated) {
        this.selfGenerated = selfGenerated;
    }

    public Integer getCurrentWeekCleared() {
        return currentWeekCleared;
    }

    public void setCurrentWeekCleared(Integer currentWeekCleared) {
        this.currentWeekCleared = currentWeekCleared;
    }

    public Integer getCurrentWeekPendings() {
        return currentWeekPendings;
    }

    public void setCurrentWeekPendings(Integer currentWeekPendings) {
        this.currentWeekPendings = currentWeekPendings;
    }

    public Integer getFirstWeek() {
        return firstWeek;
    }

    public void setFirstWeek(Integer firstWeek) {
        this.firstWeek = firstWeek;
    }

    public Integer getSecondWeek() {
        return secondWeek;
    }

    public void setSecondWeek(Integer secondWeek) {
        this.secondWeek = secondWeek;
    }

    public Integer getThirdWeek() {
        return thirdWeek;
    }

    public void setThirdWeek(Integer thirdWeek) {
        this.thirdWeek = thirdWeek;
    }

    public Integer getFirstMonth() {
        return firstMonth;
    }

    public void setFirstMonth(Integer firstMonth) {
        this.firstMonth = firstMonth;
    }

    public Integer getSecondMonth() {
        return secondMonth;
    }

    public void setSecondMonth(Integer secondMonth) {
        this.secondMonth = secondMonth;
    }

    public Integer getThirdMonth() {
        return thirdMonth;
    }

    public void setThirdMonth(Integer thirdMonth) {
        this.thirdMonth = thirdMonth;
    }

    public Integer getWithinSixMonths() {
        return withinSixMonths;
    }

    public void setWithinSixMonths(Integer withinSixMonths) {
        this.withinSixMonths = withinSixMonths;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
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
        if (!(o instanceof GhoshwaraDTO)) {
            return false;
        }

        GhoshwaraDTO ghoshwaraDTO = (GhoshwaraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ghoshwaraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GhoshwaraDTO{" +
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
            ", securityUser=" + getSecurityUser() +
            "}";
    }

    public Integer getAboveSixMonths() {
        return aboveSixMonths;
    }

    public void setAboveSixMonths(Integer aboveSixMonths) {
        this.aboveSixMonths = aboveSixMonths;
    }

    public Integer getAboveOneYear() {
        return aboveOneYear;
    }

    public void setAboveOneYear(Integer aboveOneYear) {
        this.aboveOneYear = aboveOneYear;
    }

    public Integer getDailyPendings() {
        return dailyPendings;
    }

    public void setDailyPendings(Integer dailyPendings) {
        this.dailyPendings = dailyPendings;
    }
}
