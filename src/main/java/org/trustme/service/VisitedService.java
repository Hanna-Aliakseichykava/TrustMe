package org.trustme.service;

import org.trustme.domain.Visited;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Visited.
 */
public interface VisitedService {

    /**
     * Save a visited.
     *
     * @param visited the entity to save
     * @return the persisted entity
     */
    Visited save(Visited visited);

    /**
     * Get all the visiteds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Visited> findAll(Pageable pageable);


    /**
     * Get the "id" visited.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Visited> findOne(Long id);

    /**
     * Delete the "id" visited.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
