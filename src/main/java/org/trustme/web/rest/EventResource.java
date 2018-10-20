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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.trustme.domain.Place;
import org.trustme.service.EventService;
import org.trustme.service.dto.EventDto;
import org.trustme.web.rest.util.PaginationUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Scanner;

/**
 * REST controller for managing Place.
 */
@RestController
@RequestMapping("/api")
public class EventResource {

    private final Logger log = LoggerFactory.getLogger(PlaceResource.class);

    private static final String ENTITY_NAME = "event";

    @Autowired
    private EventService eventService;


    @GetMapping("/events")
    @Timed
    public ResponseEntity<List<EventDto>> getAllPlaces(
        Pageable pageable,
        @RequestParam("lat") Double lat,
        @RequestParam("lon") Double lon) throws IOException {

        log.debug("REST request to get a page of Events");
        //https://kudago.com/public-api/v1.4/events/?lat=55.7279&lon=37.5847&radius=1000&fields=id,title,categories,place,dates&actual_since=20181020

        List<EventDto> events = eventService.getAllEvents(
            lat, //55.7279d
            lon, //37.5847d
            "https://kudago.com/public-api/v1.4/events/?fields=id,title,categories,place,dates&actual_since=20181020"
        );

        Page<EventDto> page = new PageImpl<>(events, pageable, events.size());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/events");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
