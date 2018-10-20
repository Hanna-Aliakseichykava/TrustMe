package org.trustme.service.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.trustme.service.EventService;
import org.trustme.service.dto.EventDto;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class EventServiceImpl  implements EventService {

    @Override
    public String getResponse(String lat, String lon, String url) throws IOException {
        URLConnection connection = new URL(url + "&radius=1000&lat=" + lat + "&lon=" + lon).openConnection();
        InputStream response = connection.getInputStream();

        Scanner scanner = new Scanner(response);
        String responseBody = scanner.useDelimiter("\\A").next();
        System.out.println(responseBody);
        return responseBody;
    }

    @Override
    public List<EventDto> getAllEvents(double lat, double lon, String url) throws IOException {

        List<EventDto> events = new ArrayList<>();

        String responseBody = getResponse(String.valueOf(lat), String.valueOf(lon), url);
        JSONObject obj = new JSONObject(responseBody);
        JSONArray arr = obj.getJSONArray("results");

        for (int i = 0; i < arr.length(); i++) {
            EventDto eventDto = new EventDto();

            Long id = arr.getJSONObject(i).getLong("id");
            String title = arr.getJSONObject(i).getString("title");

            eventDto.setId(id);
            eventDto.setTitle(title);
            events.add(eventDto);
        }
        return events;
    }
}
