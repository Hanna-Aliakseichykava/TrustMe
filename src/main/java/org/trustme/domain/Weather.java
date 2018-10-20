package org.trustme.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Weather.
 */
@Entity
@Table(name = "weather")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Weather implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "humid")
    private Integer humid;

    @Column(name = "temp")
    private Double temp;

    @Column(name = "temp_min")
    private Double tempMin;

    @Column(name = "temp_max")
    private Double tempMax;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "weight")
    private Integer weight;

    @Lob
    @Column(name = "json")
    private String json;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHumid() {
        return humid;
    }

    public Weather humid(Integer humid) {
        this.humid = humid;
        return this;
    }

    public void setHumid(Integer humid) {
        this.humid = humid;
    }

    public Double getTemp() {
        return temp;
    }

    public Weather temp(Double temp) {
        this.temp = temp;
        return this;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public Weather tempMin(Double tempMin) {
        this.tempMin = tempMin;
        return this;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public Weather tempMax(Double tempMax) {
        this.tempMax = tempMax;
        return this;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public LocalDate getDate() {
        return date;
    }

    public Weather date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getWeight() {
        return weight;
    }

    public Weather weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getJson() {
        return json;
    }

    public Weather json(String json) {
        this.json = json;
        return this;
    }

    public void setJson(String json) {
        this.json = json;
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
        Weather weather = (Weather) o;
        if (weather.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), weather.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Weather{" +
            "id=" + getId() +
            ", humid=" + getHumid() +
            ", temp=" + getTemp() +
            ", tempMin=" + getTempMin() +
            ", tempMax=" + getTempMax() +
            ", date='" + getDate() + "'" +
            ", weight=" + getWeight() +
            ", json='" + getJson() + "'" +
            "}";
    }
}
