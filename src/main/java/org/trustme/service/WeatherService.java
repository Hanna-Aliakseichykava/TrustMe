package org.trustme.service;

import org.trustme.domain.Weather;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Weather.
 */
public interface WeatherService {

    /**
     * Save a weather.
     *
     * @param weather the entity to save
     * @return the persisted entity
     */
    Weather save(Weather weather);

    /**
     * Get all the weathers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Weather> findAll(Pageable pageable);


    /**
     * Get the "id" weather.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Weather> findOne(Long id);

    /**
     * Delete the "id" weather.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
