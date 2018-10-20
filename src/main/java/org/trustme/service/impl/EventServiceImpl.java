package org.trustme.service.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.trustme.domain.enumeration.PlaceType;
import org.trustme.service.EventService;
import org.trustme.service.dto.EventDto;
import org.trustme.service.dto.NearPlaceDto;

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

            List<String> categories = new ArrayList<>();
            JSONArray categoriesJsonArray = arr.getJSONObject(i).getJSONArray("categories");
            for (int j = 0; j < categoriesJsonArray.length(); j++) {
                categories.add(categoriesJsonArray.getString(i));
            }

            eventDto.setId(id);
            eventDto.setTitle(title);
            eventDto.setType(PlaceType.EVENT);
            eventDto.setJson(arr.getJSONObject(i).toString());
            eventDto.setCategories(categories);
            eventDto.setPlaceId(arr.getJSONObject(i).getJSONObject("place").getLong("id"));
            events.add(eventDto);
        }
        return events;
    }

    @Override
    public String getResponse(String url) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        InputStream response = connection.getInputStream();

        Scanner scanner = new Scanner(response);
        String responseBody = scanner.useDelimiter("\\A").next();
        System.out.println(responseBody);
        return responseBody;
    }

    @Override
    public NearPlaceDto getPlace(String url) throws IOException {

        List<NearPlaceDto> events = new ArrayList<>();
        String responseBody = getResponse(url);
        JSONObject obj = new JSONObject(responseBody);



            NearPlaceDto placeDto = new NearPlaceDto();

            Long id = obj.getLong("id");
            String title = obj.getString("title");



            placeDto.setId(id);
            placeDto.setTitle(title);
            placeDto.setLat(obj.getJSONObject("coords").getDouble("lat"));
            placeDto.setLon(obj.getJSONObject("coords").getDouble("lon"));

            events.add(placeDto);

        return placeDto;
    }
}
