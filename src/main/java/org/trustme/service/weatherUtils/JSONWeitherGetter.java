package org.trustme.service.weatherUtils;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

@Component
public class JSONWeitherGetter {

    public String getWeither(String lat, String lon) throws IOException {
        String url = "http://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&APPID=e7e93e9b638b16f48a81d41c5285b805";

        URLConnection connection = new URL(url).openConnection();
        InputStream response = connection.getInputStream();

        Scanner scanner = new Scanner(response);
        String responseBody = scanner.useDelimiter("\\A").next();
        System.out.println(responseBody);
        return responseBody;
    }
}
