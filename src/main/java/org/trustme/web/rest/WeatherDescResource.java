package org.trustme.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.trustme.domain.WeatherDesc;
import org.trustme.service.WeatherDescService;
import org.trustme.web.rest.errors.BadRequestAlertException;
import org.trustme.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WeatherDesc.
 */
@RestController
@RequestMapping("/api")
public class WeatherDescResource {

    private final Logger log = LoggerFactory.getLogger(WeatherDescResource.class);

    private static final String ENTITY_NAME = "weatherDesc";

    private WeatherDescService weatherDescService;

    public WeatherDescResource(WeatherDescService weatherDescService) {
        this.weatherDescService = weatherDescService;
    }

    /**
     * POST  /weather-descs : Create a new weatherDesc.
     *
     * @param weatherDesc the weatherDesc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new weatherDesc, or with status 400 (Bad Request) if the weatherDesc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/weather-descs")
    @Timed
    public ResponseEntity<WeatherDesc> createWeatherDesc(@RequestBody WeatherDesc weatherDesc) throws URISyntaxException {
        log.debug("REST request to save WeatherDesc : {}", weatherDesc);
        if (weatherDesc.getId() != null) {
            throw new BadRequestAlertException("A new weatherDesc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WeatherDesc result = weatherDescService.save(weatherDesc);
        return ResponseEntity.created(new URI("/api/weather-descs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /weather-descs : Updates an existing weatherDesc.
     *
     * @param weatherDesc the weatherDesc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated weatherDesc,
     * or with status 400 (Bad Request) if the weatherDesc is not valid,
     * or with status 500 (Internal Server Error) if the weatherDesc couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/weather-descs")
    @Timed
    public ResponseEntity<WeatherDesc> updateWeatherDesc(@RequestBody WeatherDesc weatherDesc) throws URISyntaxException {
        log.debug("REST request to update WeatherDesc : {}", weatherDesc);
        if (weatherDesc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WeatherDesc result = weatherDescService.save(weatherDesc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, weatherDesc.getId().toString()))
            .body(result);
    }

    /**
     * GET  /weather-descs : get all the weatherDescs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of weatherDescs in body
     */
    @GetMapping("/weather-descs")
    @Timed
    public List<WeatherDesc> getAllWeatherDescs() {
        log.debug("REST request to get all WeatherDescs");
        return weatherDescService.findAll();
    }

    /**
     * GET  /weather-descs/:id : get the "id" weatherDesc.
     *
     * @param id the id of the weatherDesc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the weatherDesc, or with status 404 (Not Found)
     */
    @GetMapping("/weather-descs/{id}")
    @Timed
    public ResponseEntity<WeatherDesc> getWeatherDesc(@PathVariable Long id) {
        log.debug("REST request to get WeatherDesc : {}", id);
        Optional<WeatherDesc> weatherDesc = weatherDescService.findOne(id);
        return ResponseUtil.wrapOrNotFound(weatherDesc);
    }

    /**
     * DELETE  /weather-descs/:id : delete the "id" weatherDesc.
     *
     * @param id the id of the weatherDesc to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/weather-descs/{id}")
    @Timed
    public ResponseEntity<Void> deleteWeatherDesc(@PathVariable Long id) {
        log.debug("REST request to delete WeatherDesc : {}", id);
        weatherDescService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
