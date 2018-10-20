package org.trustme.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A WeatherDesc.
 */
@Entity
@Table(name = "weather_desc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WeatherDesc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "short_desc")
    private String shortDesc;

    @Column(name = "long_desc")
    private String longDesc;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeight() {
        return weight;
    }

    public WeatherDesc weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public WeatherDesc shortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
        return this;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public WeatherDesc longDesc(String longDesc) {
        this.longDesc = longDesc;
        return this;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WeatherDesc weatherDesc = (WeatherDesc) o;
        if (weatherDesc.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), weatherDesc.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WeatherDesc{" +
            "id=" + getId() +
            ", weight=" + getWeight() +
            ", shortDesc='" + getShortDesc() + "'" +
            ", longDesc='" + getLongDesc() + "'" +
            "}";
    }
}
