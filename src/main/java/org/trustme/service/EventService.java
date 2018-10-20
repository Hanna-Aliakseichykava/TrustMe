package org.trustme.service;

import org.trustme.service.dto.EventDto;
import org.trustme.service.dto.NearPlaceDto;

import java.io.IOException;
import java.util.List;

public interface EventService {
    String getResponse(String lat, String lon, String url) throws IOException;

    List<EventDto> getAllEvents(double lat, double lon, String url) throws IOException;

    String getResponse(String url) throws IOException;

    NearPlaceDto getPlace(String url) throws IOException;
}
