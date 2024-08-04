package com.techvg.eoffice.service.criteria;

import com.techvg.eoffice.domain.enumeration.RegisterType;
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
 * Criteria class for the {@link com.techvg.eoffice.domain.Ghoshwara} entity. This class is used
 * in {@link com.techvg.eoffice.web.rest.GhoshwaraResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ghoshwaras?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class GhoshwaraCriteria implements Serializable, Criteria {

    /**
     * Class for filtering RegisterType
     */
    public static class RegisterTypeFilter extends Filter<RegisterType> {

        public RegisterTypeFilter() {}

        public RegisterTypeFilter(RegisterTypeFilter filter) {
            super(filter);
        }

        @Override
        public RegisterTypeFilter copy() {
            return new RegisterTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private RegisterTypeFilter registerType;

    private IntegerFilter initialPendings;

    private IntegerFilter currentWeekInwards;

    private IntegerFilter total;

    private IntegerFilter selfGenerated;

    private IntegerFilter currentWeekCleared;

    private IntegerFilter weeklyPendings;

    private IntegerFilter firstWeek;

    private IntegerFilter secondWeek;

    private IntegerFilter thirdWeek;

    private IntegerFilter firstMonth;

    private IntegerFilter secondMonth;

    private IntegerFilter thirdMonth;

    private IntegerFilter withinSixMonths;

    private InstantFilter date;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter securityUserId;

    private Boolean distinct;

    public GhoshwaraCriteria() {}

    public GhoshwaraCriteria(GhoshwaraCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.registerType = other.registerType == null ? null : other.registerType.copy();
        this.initialPendings = other.initialPendings == null ? null : other.initialPendings.copy();
        this.currentWeekInwards = other.currentWeekInwards == null ? null : other.currentWeekInwards.copy();
        this.total = other.total == null ? null : other.total.copy();
        this.selfGenerated = other.selfGenerated == null ? null : other.selfGenerated.copy();
        this.currentWeekCleared = other.currentWeekCleared == null ? null : other.currentWeekCleared.copy();
        this.weeklyPendings = other.weeklyPendings == null ? null : other.weeklyPendings.copy();
        this.firstWeek = other.firstWeek == null ? null : other.firstWeek.copy();
        this.secondWeek = other.secondWeek == null ? null : other.secondWeek.copy();
        this.thirdWeek = other.thirdWeek == null ? null : other.thirdWeek.copy();
        this.firstMonth = other.firstMonth == null ? null : other.firstMonth.copy();
        this.secondMonth = other.secondMonth == null ? null : other.secondMonth.copy();
        this.thirdMonth = other.thirdMonth == null ? null : other.thirdMonth.copy();
        this.withinSixMonths = other.withinSixMonths == null ? null : other.withinSixMonths.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public GhoshwaraCriteria copy() {
        return new GhoshwaraCriteria(this);
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

    public RegisterTypeFilter getRegisterType() {
        return registerType;
    }

    public RegisterTypeFilter registerType() {
        if (registerType == null) {
            registerType = new RegisterTypeFilter();
        }
        return registerType;
    }

    public void setRegisterType(RegisterTypeFilter registerType) {
        this.registerType = registerType;
    }

    public IntegerFilter getInitialPendings() {
        return initialPendings;
    }

    public IntegerFilter initialPendings() {
        if (initialPendings == null) {
            initialPendings = new IntegerFilter();
        }
        return initialPendings;
    }

    public void setInitialPendings(IntegerFilter initialPendings) {
        this.initialPendings = initialPendings;
    }

    public IntegerFilter getCurrentWeekInwards() {
        return currentWeekInwards;
    }

    public IntegerFilter currentWeekInwards() {
        if (currentWeekInwards == null) {
            currentWeekInwards = new IntegerFilter();
        }
        return currentWeekInwards;
    }

    public void setCurrentWeekInwards(IntegerFilter currentWeekInwards) {
        this.currentWeekInwards = currentWeekInwards;
    }

    public IntegerFilter getTotal() {
        return total;
    }

    public IntegerFilter total() {
        if (total == null) {
            total = new IntegerFilter();
        }
        return total;
    }

    public void setTotal(IntegerFilter total) {
        this.total = total;
    }

    public IntegerFilter getSelfGenerated() {
        return selfGenerated;
    }

    public IntegerFilter selfGenerated() {
        if (selfGenerated == null) {
            selfGenerated = new IntegerFilter();
        }
        return selfGenerated;
    }

    public void setSelfGenerated(IntegerFilter selfGenerated) {
        this.selfGenerated = selfGenerated;
    }

    public IntegerFilter getCurrentWeekCleared() {
        return currentWeekCleared;
    }

    public IntegerFilter currentWeekCleared() {
        if (currentWeekCleared == null) {
            currentWeekCleared = new IntegerFilter();
        }
        return currentWeekCleared;
    }

    public void setCurrentWeekCleared(IntegerFilter currentWeekCleared) {
        this.currentWeekCleared = currentWeekCleared;
    }

    public IntegerFilter getWeeklyPendings() {
        return weeklyPendings;
    }

    public IntegerFilter weeklyPendings() {
        if (weeklyPendings == null) {
            weeklyPendings = new IntegerFilter();
        }
        return weeklyPendings;
    }

    public void setWeeklyPendings(IntegerFilter weeklyPendings) {
        this.weeklyPendings = weeklyPendings;
    }

    public IntegerFilter getFirstWeek() {
        return firstWeek;
    }

    public IntegerFilter firstWeek() {
        if (firstWeek == null) {
            firstWeek = new IntegerFilter();
        }
        return firstWeek;
    }

    public void setFirstWeek(IntegerFilter firstWeek) {
        this.firstWeek = firstWeek;
    }

    public IntegerFilter getSecondWeek() {
        return secondWeek;
    }

    public IntegerFilter secondWeek() {
        if (secondWeek == null) {
            secondWeek = new IntegerFilter();
        }
        return secondWeek;
    }

    public void setSecondWeek(IntegerFilter secondWeek) {
        this.secondWeek = secondWeek;
    }

    public IntegerFilter getThirdWeek() {
        return thirdWeek;
    }

    public IntegerFilter thirdWeek() {
        if (thirdWeek == null) {
            thirdWeek = new IntegerFilter();
        }
        return thirdWeek;
    }

    public void setThirdWeek(IntegerFilter thirdWeek) {
        this.thirdWeek = thirdWeek;
    }

    public IntegerFilter getFirstMonth() {
        return firstMonth;
    }

    public IntegerFilter firstMonth() {
        if (firstMonth == null) {
            firstMonth = new IntegerFilter();
        }
        return firstMonth;
    }

    public void setFirstMonth(IntegerFilter firstMonth) {
        this.firstMonth = firstMonth;
    }

    public IntegerFilter getSecondMonth() {
        return secondMonth;
    }

    public IntegerFilter secondMonth() {
        if (secondMonth == null) {
            secondMonth = new IntegerFilter();
        }
        return secondMonth;
    }

    public void setSecondMonth(IntegerFilter secondMonth) {
        this.secondMonth = secondMonth;
    }

    public IntegerFilter getThirdMonth() {
        return thirdMonth;
    }

    public IntegerFilter thirdMonth() {
        if (thirdMonth == null) {
            thirdMonth = new IntegerFilter();
        }
        return thirdMonth;
    }

    public void setThirdMonth(IntegerFilter thirdMonth) {
        this.thirdMonth = thirdMonth;
    }

    public IntegerFilter getWithinSixMonths() {
        return withinSixMonths;
    }

    public IntegerFilter withinSixMonths() {
        if (withinSixMonths == null) {
            withinSixMonths = new IntegerFilter();
        }
        return withinSixMonths;
    }

    public void setWithinSixMonths(IntegerFilter withinSixMonths) {
        this.withinSixMonths = withinSixMonths;
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
        final GhoshwaraCriteria that = (GhoshwaraCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(registerType, that.registerType) &&
            Objects.equals(initialPendings, that.initialPendings) &&
            Objects.equals(currentWeekInwards, that.currentWeekInwards) &&
            Objects.equals(total, that.total) &&
            Objects.equals(selfGenerated, that.selfGenerated) &&
            Objects.equals(currentWeekCleared, that.currentWeekCleared) &&
            Objects.equals(weeklyPendings, that.weeklyPendings) &&
            Objects.equals(firstWeek, that.firstWeek) &&
            Objects.equals(secondWeek, that.secondWeek) &&
            Objects.equals(thirdWeek, that.thirdWeek) &&
            Objects.equals(firstMonth, that.firstMonth) &&
            Objects.equals(secondMonth, that.secondMonth) &&
            Objects.equals(thirdMonth, that.thirdMonth) &&
            Objects.equals(withinSixMonths, that.withinSixMonths) &&
            Objects.equals(date, that.date) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(securityUserId, that.securityUserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            registerType,
            initialPendings,
            currentWeekInwards,
            total,
            selfGenerated,
            currentWeekCleared,
            weeklyPendings,
            firstWeek,
            secondWeek,
            thirdWeek,
            firstMonth,
            secondMonth,
            thirdMonth,
            withinSixMonths,
            date,
            lastModified,
            lastModifiedBy,
            securityUserId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GhoshwaraCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (registerType != null ? "registerType=" + registerType + ", " : "") +
            (initialPendings != null ? "initialPendings=" + initialPendings + ", " : "") +
            (currentWeekInwards != null ? "currentWeekInwards=" + currentWeekInwards + ", " : "") +
            (total != null ? "total=" + total + ", " : "") +
            (selfGenerated != null ? "selfGenerated=" + selfGenerated + ", " : "") +
            (currentWeekCleared != null ? "currentWeekCleared=" + currentWeekCleared + ", " : "") +
            (weeklyPendings != null ? "weeklyPendings=" + weeklyPendings + ", " : "") +
            (firstWeek != null ? "firstWeek=" + firstWeek + ", " : "") +
            (secondWeek != null ? "secondWeek=" + secondWeek + ", " : "") +
            (thirdWeek != null ? "thirdWeek=" + thirdWeek + ", " : "") +
            (firstMonth != null ? "firstMonth=" + firstMonth + ", " : "") +
            (secondMonth != null ? "secondMonth=" + secondMonth + ", " : "") +
            (thirdMonth != null ? "thirdMonth=" + thirdMonth + ", " : "") +
            (withinSixMonths != null ? "withinSixMonths=" + withinSixMonths + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
