package org.trustme.domain.weatherObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.trustme.domain.Weather;
import org.trustme.domain.weatherObject.weatherUtil.WeatherComfortableCalculator;
import org.trustme.domain.weatherObject.weatherUtil.WeatherComfortableCalculator_Impl;
import org.trustme.domain.weatherObject.weatherUtil.WeatherTimeUtils;
import org.trustme.domain.weatherObject.weatherUtil.WeatherTimeUtils_Impl;

import java.io.IOException;
import java.time.LocalDate;

public class WeitherParser {

    JSONWeitherGetter jsonWeitherGetter = new JSONWeitherGetter();
    WeatherTimeUtils timeUtils = new WeatherTimeUtils_Impl();
    WeatherComfortableCalculator weatherComfortableCalculator = new WeatherComfortableCalculator_Impl();
//

    public Weather getWeitherForToday(double lat, double lon) throws IOException {

        String responseBody = jsonWeitherGetter.getWeither(String.valueOf(lat), String.valueOf(lon));
        JSONObject obj = new JSONObject(responseBody);
        JSONArray arr = obj.getJSONArray("list");
        String currenttime = timeUtils.roundAndGetTimeToDiscret();
        System.out.println(currenttime);


        for (int i = 0; i < arr.length(); i++) {
            if (arr.getJSONObject(i).getString("dt_txt").equals(currenttime)) {

                Weather weather = new Weather();
                System.out.println("Id " + arr.getJSONObject(i).getJSONObject("main").getString("temp"));

                Double temp = (arr.getJSONObject(i).getJSONObject("main").getDouble("temp") - 273.15);

                Double temp_min = arr.getJSONObject(i).getJSONObject("main").getDouble("temp_min") - 273.15;
                Double temp_max = arr.getJSONObject(i).getJSONObject("main").getDouble("temp_max") - 273.15;
                int humidity = arr.getJSONObject(i).getJSONObject("main").getInt("humidity");

                weather.setDate(LocalDate.now());
                weather.setHumid(humidity);
                weather.setTemp(temp);
                weather.setTempMax(temp_max);
                weather.setTempMin(temp_min);

                WeatherComfortableCalculator weatherComfortableCalculator ;
//                weather.setWeight(getWeitherWeight());
//                weather.setJson();
                return weather;
            }
        }
        return null;
    }
}
