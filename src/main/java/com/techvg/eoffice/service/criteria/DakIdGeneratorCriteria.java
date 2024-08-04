package com.techvg.eoffice.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.techvg.eoffice.domain.DakIdGenerator} entity. This class is used
 * in {@link com.techvg.eoffice.web.rest.DakIdGeneratorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dak-id-generators?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class DakIdGeneratorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter nextValInward;

    private LongFilter nextValOutward;

    private LongFilter organizationId;

    private Boolean distinct;

    public DakIdGeneratorCriteria() {}

    public DakIdGeneratorCriteria(DakIdGeneratorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nextValInward = other.nextValInward == null ? null : other.nextValInward.copy();
        this.nextValOutward = other.nextValOutward == null ? null : other.nextValOutward.copy();
        this.organizationId = other.organizationId == null ? null : other.organizationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DakIdGeneratorCriteria copy() {
        return new DakIdGeneratorCriteria(this);
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

    public LongFilter getNextValInward() {
        return nextValInward;
    }

    public LongFilter nextValInward() {
        if (nextValInward == null) {
            nextValInward = new LongFilter();
        }
        return nextValInward;
    }

    public void setNextValInward(LongFilter nextValInward) {
        this.nextValInward = nextValInward;
    }

    public LongFilter getNextValOutward() {
        return nextValOutward;
    }

    public LongFilter nextValOutward() {
        if (nextValOutward == null) {
            nextValOutward = new LongFilter();
        }
        return nextValOutward;
    }

    public void setNextValOutward(LongFilter nextValOutward) {
        this.nextValOutward = nextValOutward;
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
        final DakIdGeneratorCriteria that = (DakIdGeneratorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nextValInward, that.nextValInward) &&
            Objects.equals(nextValOutward, that.nextValOutward) &&
            Objects.equals(organizationId, that.organizationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nextValInward, nextValOutward, organizationId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DakIdGeneratorCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nextValInward != null ? "nextValInward=" + nextValInward + ", " : "") +
            (nextValOutward != null ? "nextValOutward=" + nextValOutward + ", " : "") +
            (organizationId != null ? "organizationId=" + organizationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
