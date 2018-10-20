package org.trustme.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.trustme.domain.Weather;
import org.trustme.service.WeatherService;
import org.trustme.web.rest.errors.BadRequestAlertException;
import org.trustme.web.rest.util.HeaderUtil;
import org.trustme.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Weather.
 */
@RestController
@RequestMapping("/api")
public class WeatherResource {

    private final Logger log = LoggerFactory.getLogger(WeatherResource.class);

    private static final String ENTITY_NAME = "weather";

    private WeatherService weatherService;

    public WeatherResource(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * POST  /weathers : Create a new weather.
     *
     * @param weather the weather to create
     * @return the ResponseEntity with status 201 (Created) and with body the new weather, or with status 400 (Bad Request) if the weather has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/weathers")
    @Timed
    public ResponseEntity<Weather> createWeather(@RequestBody Weather weather) throws URISyntaxException {
        log.debug("REST request to save Weather : {}", weather);
        if (weather.getId() != null) {
            throw new BadRequestAlertException("A new weather cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Weather result = weatherService.save(weather);
        return ResponseEntity.created(new URI("/api/weathers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /weathers : Updates an existing weather.
     *
     * @param weather the weather to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated weather,
     * or with status 400 (Bad Request) if the weather is not valid,
     * or with status 500 (Internal Server Error) if the weather couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/weathers")
    @Timed
    public ResponseEntity<Weather> updateWeather(@RequestBody Weather weather) throws URISyntaxException {
        log.debug("REST request to update Weather : {}", weather);
        if (weather.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Weather result = weatherService.save(weather);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, weather.getId().toString()))
            .body(result);
    }

    /**
     * GET  /weathers : get all the weathers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of weathers in body
     */
    @GetMapping("/weathers")
    @Timed
    public ResponseEntity<List<Weather>> getAllWeathers(Pageable pageable) {
        log.debug("REST request to get a page of Weathers");
        Page<Weather> page = weatherService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/weathers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /weathers/:id : get the "id" weather.
     *
     * @param id the id of the weather to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the weather, or with status 404 (Not Found)
     */
    @GetMapping("/weathers/{id}")
    @Timed
    public ResponseEntity<Weather> getWeather(@PathVariable Long id) {
        log.debug("REST request to get Weather : {}", id);
        Optional<Weather> weather = weatherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(weather);
    }

    /**
     * DELETE  /weathers/:id : delete the "id" weather.
     *
     * @param id the id of the weather to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/weathers/{id}")
    @Timed
    public ResponseEntity<Void> deleteWeather(@PathVariable Long id) {
        log.debug("REST request to delete Weather : {}", id);
        weatherService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
