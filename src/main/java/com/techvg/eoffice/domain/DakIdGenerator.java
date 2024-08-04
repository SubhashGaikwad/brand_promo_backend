package com.techvg.eoffice.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DakIdGenerator.
 */
@Entity
@Table(name = "dak_id_generator")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DakIdGenerator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "next_val_inward")
    private Long nextValInward;

    @Column(name = "next_val_outward")
    private Long nextValOutward;

    @OneToOne
    @JoinColumn(unique = true)
    private Organization organization;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DakIdGenerator id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNextValInward() {
        return this.nextValInward;
    }

    public DakIdGenerator nextValInward(Long nextValInward) {
        this.setNextValInward(nextValInward);
        return this;
    }

    public void setNextValInward(Long nextValInward) {
        this.nextValInward = nextValInward;
    }

    public Long getNextValOutward() {
        return this.nextValOutward;
    }

    public DakIdGenerator nextValOutward(Long nextValOutward) {
        this.setNextValOutward(nextValOutward);
        return this;
    }

    public void setNextValOutward(Long nextValOutward) {
        this.nextValOutward = nextValOutward;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public DakIdGenerator organization(Organization organization) {
        this.setOrganization(organization);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DakIdGenerator)) {
            return false;
        }
        return id != null && id.equals(((DakIdGenerator) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DakIdGenerator{" +
            "id=" + getId() +
            ", nextValInward=" + getNextValInward() +
            ", nextValOutward=" + getNextValOutward() +
            "}";
    }
}
