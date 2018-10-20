package org.trustme.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.trustme.domain.Visited;
import org.trustme.service.VisitedService;
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
 * REST controller for managing Visited.
 */
@RestController
@RequestMapping("/api")
public class VisitedResource {

    private final Logger log = LoggerFactory.getLogger(VisitedResource.class);

    private static final String ENTITY_NAME = "visited";

    private VisitedService visitedService;

    public VisitedResource(VisitedService visitedService) {
        this.visitedService = visitedService;
    }

    /**
     * POST  /visiteds : Create a new visited.
     *
     * @param visited the visited to create
     * @return the ResponseEntity with status 201 (Created) and with body the new visited, or with status 400 (Bad Request) if the visited has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/visiteds")
    @Timed
    public ResponseEntity<Visited> createVisited(@RequestBody Visited visited) throws URISyntaxException {
        log.debug("REST request to save Visited : {}", visited);
        if (visited.getId() != null) {
            throw new BadRequestAlertException("A new visited cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Visited result = visitedService.save(visited);
        return ResponseEntity.created(new URI("/api/visiteds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /visiteds : Updates an existing visited.
     *
     * @param visited the visited to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated visited,
     * or with status 400 (Bad Request) if the visited is not valid,
     * or with status 500 (Internal Server Error) if the visited couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/visiteds")
    @Timed
    public ResponseEntity<Visited> updateVisited(@RequestBody Visited visited) throws URISyntaxException {
        log.debug("REST request to update Visited : {}", visited);
        if (visited.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Visited result = visitedService.save(visited);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, visited.getId().toString()))
            .body(result);
    }

    /**
     * GET  /visiteds : get all the visiteds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of visiteds in body
     */
    @GetMapping("/visiteds")
    @Timed
    public ResponseEntity<List<Visited>> getAllVisiteds(Pageable pageable) {
        log.debug("REST request to get a page of Visiteds");
        Page<Visited> page = visitedService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/visiteds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /visiteds/:id : get the "id" visited.
     *
     * @param id the id of the visited to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the visited, or with status 404 (Not Found)
     */
    @GetMapping("/visiteds/{id}")
    @Timed
    public ResponseEntity<Visited> getVisited(@PathVariable Long id) {
        log.debug("REST request to get Visited : {}", id);
        Optional<Visited> visited = visitedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(visited);
    }

    /**
     * DELETE  /visiteds/:id : delete the "id" visited.
     *
     * @param id the id of the visited to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/visiteds/{id}")
    @Timed
    public ResponseEntity<Void> deleteVisited(@PathVariable Long id) {
        log.debug("REST request to delete Visited : {}", id);
        visitedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
