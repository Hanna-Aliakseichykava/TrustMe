package org.trustme.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trustme.service.EventService;
import org.trustme.service.dto.EventDto;
import org.trustme.service.dto.NearPlaceDto;
import org.trustme.web.rest.util.PaginationUtil;

import java.io.IOException;
import java.util.List;

/**
 * REST controller for managing Nearest Places.
 */
@RestController
@RequestMapping("/api")
public class NearPlaceResource {

    private final Logger log = LoggerFactory.getLogger(NearPlaceResource.class);

    private static final String ENTITY_NAME = "near_place";

    @Autowired
    private EventService eventService;


    @GetMapping("/near-places/{nearPlaceId}")
    @Timed
    public ResponseEntity<NearPlaceDto> getPlace(
        Pageable pageable,
        @PathVariable("nearPlaceId") Long nearPlaceId //id=2755
    ) throws IOException {

        log.debug("REST request to get a page of Nearest Places");

        NearPlaceDto place = eventService.getPlace(
            "https://kudago.com/public-api/v1.4/places/" + nearPlaceId.toString()
        );



        return new ResponseEntity<NearPlaceDto>(place, HttpStatus.OK);
    }
}
