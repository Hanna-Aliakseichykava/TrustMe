package org.trustme.service;

import org.trustme.domain.WeatherDesc;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing WeatherDesc.
 */
public interface WeatherDescService {

    /**
     * Save a weatherDesc.
     *
     * @param weatherDesc the entity to save
     * @return the persisted entity
     */
    WeatherDesc save(WeatherDesc weatherDesc);

    /**
     * Get all the weatherDescs.
     *
     * @return the list of entities
     */
    List<WeatherDesc> findAll();


    /**
     * Get the "id" weatherDesc.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<WeatherDesc> findOne(Long id);

    /**
     * Delete the "id" weatherDesc.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
