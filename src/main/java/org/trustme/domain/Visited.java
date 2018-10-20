package org.trustme.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Visited.
 */
@Entity
@Table(name = "visited")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Visited implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "score")
    private Integer score;

    @Column(name = "jhi_date")
    private LocalDate date;

    @OneToOne    @JoinColumn(unique = true)
    private Weather weather;

    @OneToOne    @JoinColumn(unique = true)
    private Place place;

    @OneToOne    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public Visited score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public LocalDate getDate() {
        return date;
    }

    public Visited date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Weather getWeather() {
        return weather;
    }

    public Visited weather(Weather weather) {
        this.weather = weather;
        return this;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Place getPlace() {
        return place;
    }

    public Visited place(Place place) {
        this.place = place;
        return this;
    }

    public void setPlace(Place place) {
        this.place = place;
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
        Visited visited = (Visited) o;
        if (visited.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), visited.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Visited{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", date='" + getDate() + "'" +
            "}";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Visited user(User user) {
        this.setUser(user);
        return this;
    }
}
