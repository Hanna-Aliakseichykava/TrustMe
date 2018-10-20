package org.trustme.service.weatherUtils.weatherUtil;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class WeatherTimeUtils_Impl implements WeatherTimeUtils {

    Map<Integer, String> discretTime = new HashMap();
    //    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    LocalDate currentTime = LocalDate.now();
    Map<Integer, Integer> mapHoursToDiscretTimeKey = new HashMap<Integer, Integer>();


    public WeatherTimeUtils_Impl() {
        discretTime.put(0, getCurrentDate() + " 09:00:00");
        discretTime.put(1, getCurrentDate() + " 12:00:00");
        discretTime.put(2, getCurrentDate() + " 15:00:00");
        discretTime.put(3, getCurrentDate() + " 18:00:00");
        discretTime.put(4, getCurrentDate() + " 21:00:00");
        discretTime.put(5, getCurrentDate() + " 24:00:00");
        discretTime.put(6, getCurrentDate() + " 03:00:00");
        discretTime.put(7, getCurrentDate() + " 06:00:00");

        mapHoursToDiscretTimeKey.put(9, 0);
        mapHoursToDiscretTimeKey.put(12, 1);
        mapHoursToDiscretTimeKey.put(15, 2);
        mapHoursToDiscretTimeKey.put(18, 3);
        mapHoursToDiscretTimeKey.put(21, 4);
        mapHoursToDiscretTimeKey.put(24, 5);
        mapHoursToDiscretTimeKey.put(3, 6);
        mapHoursToDiscretTimeKey.put(6, 7);
    }

    public String roundAndGetTimeToDiscret() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int hours = currentTime.getHours();

        for (int i = 3; i < 24; i += 3) {
            int y = i != 24 ? i + 3 : 3;
            if (i <= hours && hours <= y) {

                String str = discretTime.get(mapHoursToDiscretTimeKey.get(y));
                return str;
            }
        }
        return discretTime.get(mapHoursToDiscretTimeKey.get(15));
    }

    private String getCurrentDate() {

        LocalDate localDateTime = LocalDate.now();
        return localDateTime.getYear() + "-" + currentTime.getMonthValue() + "-" + currentTime.getDayOfMonth();
    }
}
