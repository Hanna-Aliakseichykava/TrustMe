package org.trustme.service.impl;

import org.trustme.service.WeatherDescService;
import org.trustme.domain.WeatherDesc;
import org.trustme.repository.WeatherDescRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing WeatherDesc.
 */
@Service
@Transactional
public class WeatherDescServiceImpl implements WeatherDescService {

    private final Logger log = LoggerFactory.getLogger(WeatherDescServiceImpl.class);

    private WeatherDescRepository weatherDescRepository;

    public WeatherDescServiceImpl(WeatherDescRepository weatherDescRepository) {
        this.weatherDescRepository = weatherDescRepository;
    }

    /**
     * Save a weatherDesc.
     *
     * @param weatherDesc the entity to save
     * @return the persisted entity
     */
    @Override
    public WeatherDesc save(WeatherDesc weatherDesc) {
        log.debug("Request to save WeatherDesc : {}", weatherDesc);
        return weatherDescRepository.save(weatherDesc);
    }

    /**
     * Get all the weatherDescs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<WeatherDesc> findAll() {
        log.debug("Request to get all WeatherDescs");
        return weatherDescRepository.findAll();
    }


    /**
     * Get one weatherDesc by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WeatherDesc> findOne(Long id) {
        log.debug("Request to get WeatherDesc : {}", id);
        return weatherDescRepository.findById(id);
    }

    /**
     * Delete the weatherDesc by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WeatherDesc : {}", id);
        weatherDescRepository.deleteById(id);
    }
}
