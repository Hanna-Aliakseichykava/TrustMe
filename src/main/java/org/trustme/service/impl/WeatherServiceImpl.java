package org.trustme.service.impl;

import org.trustme.service.WeatherService;
import org.trustme.domain.Weather;
import org.trustme.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Weather.
 */
@Service
@Transactional
public class WeatherServiceImpl implements WeatherService {

    private final Logger log = LoggerFactory.getLogger(WeatherServiceImpl.class);

    private WeatherRepository weatherRepository;

    public WeatherServiceImpl(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    /**
     * Save a weather.
     *
     * @param weather the entity to save
     * @return the persisted entity
     */
    @Override
    public Weather save(Weather weather) {
        log.debug("Request to save Weather : {}", weather);
        return weatherRepository.save(weather);
    }

    /**
     * Get all the weathers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Weather> findAll(Pageable pageable) {
        log.debug("Request to get all Weathers");
        return weatherRepository.findAll(pageable);
    }


    /**
     * Get one weather by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Weather> findOne(Long id) {
        log.debug("Request to get Weather : {}", id);
        return weatherRepository.findById(id);
    }

    /**
     * Delete the weather by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Weather : {}", id);
        weatherRepository.deleteById(id);
    }
}
