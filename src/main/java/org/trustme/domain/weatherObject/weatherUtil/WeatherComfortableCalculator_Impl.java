package org.trustme.domain.weatherObject.weatherUtil;

import java.util.HashMap;
import java.util.Map;

public class WeatherComfortableCalculator_Impl implements WeatherComfortableCalculator {

    private Integer humid;
    private Double temp;
    private Double wind;


    private Map<Integer, Integer> comfortZone = new HashMap();

    public void setHumid(Integer humid) {
        this.humid = humid;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }


    public void setWind(Double wind) {
        this.wind = wind;
    }

    private void setValues() {
        comfortZone.put(921, 0);
        comfortZone.put(911, 1);
        comfortZone.put(0, 2);
        comfortZone.put(17, 3);
        comfortZone.put(24, 4);
        comfortZone.put(30, 5);
        comfortZone.put(40, 6);
    }


    @Override
    public int getWetherWeight() {

        double F = 100 - humid;
        double EET =
            temp * (1 - 0.003 * F)
                - 0.385 * Math.pow(wind, 0.59)
                * ((36.6 - temp) + 0.622 * (wind - 1))
                + ((0.0015 * wind + 0.0008)
                * ((36.6 - temp) + 0.0167)) * wind;

        setValues();

        if (EET < -21) {
            return comfortZone.get(921);
        } else if (EET < -11) {
            return comfortZone.get(911);

        } else if (EET < 0) {
            return comfortZone.get(0);
        } else if (EET < 17) {
            return comfortZone.get(17);
        } else if (EET < 24) {
            return comfortZone.get(24);
        } else if (EET < 30) {
            return comfortZone.get(30);
        } else if (EET >= 30) {
            return comfortZone.get(40);
        }
        return 0;
    }
}
