package org.trustme.service.weatherUtils.weatherUtil;

public interface WeatherComfortableCalculator {

    int getWetherWeight(Integer humid,
                        Double temp,
                        Double wind);

}
