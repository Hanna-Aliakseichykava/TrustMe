package org.trustme.service.impl;

import org.trustme.service.VisitedService;
import org.trustme.domain.Visited;
import org.trustme.repository.VisitedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Visited.
 */
@Service
@Transactional
public class VisitedServiceImpl implements VisitedService {

    private final Logger log = LoggerFactory.getLogger(VisitedServiceImpl.class);

    private VisitedRepository visitedRepository;

    public VisitedServiceImpl(VisitedRepository visitedRepository) {
        this.visitedRepository = visitedRepository;
    }

    /**
     * Save a visited.
     *
     * @param visited the entity to save
     * @return the persisted entity
     */
    @Override
    public Visited save(Visited visited) {
        log.debug("Request to save Visited : {}", visited);
        return visitedRepository.save(visited);
    }

    /**
     * Get all the visiteds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Visited> findAll(Pageable pageable) {
        log.debug("Request to get all Visiteds");
        return visitedRepository.findAll(pageable);
    }


    /**
     * Get one visited by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Visited> findOne(Long id) {
        log.debug("Request to get Visited : {}", id);
        return visitedRepository.findById(id);
    }

    /**
     * Delete the visited by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Visited : {}", id);
        visitedRepository.deleteById(id);
    }
}
