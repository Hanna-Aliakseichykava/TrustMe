package org.trustme.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.trustme.service.PlacesService;
import org.trustme.service.model.Location;
import org.trustme.service.model.PlaceOnMapType;

import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Param;
import se.walkercrou.places.Place;

@Service
public class PlacesServiceImpl implements PlacesService {

	private static Logger LOG = LoggerFactory.getLogger(PlacesServiceImpl.class);

	private static final int RADIUS = 1000;
	private static final int MAXIMUM_RESULTS = 10;//GooglePlaces.MAXIMUM_RESULTS;

	//@Value("${api.key}")
		//private String apiKey = "undefined";
		private String apiKey = "AIzaSyAfHkXgg236YmiXVOsthl4eCPAhYMLuMds";

	private Map<Location, Map<PlaceOnMapType, List<Place>>> cache;

	@Override
	public List<Place> getPlaces(Location location, PlaceOnMapType type) {
		if (cache == null) {
			cache = new HashMap();
		}

		Map<PlaceOnMapType, List<Place>> placesByType = cache.get(location);

		if (placesByType == null) {
			placesByType = new HashMap();
			cache.put(location, placesByType);
		}

		List<Place> places = placesByType.get(type);
		if (places == null) {
			places = getPlacesByType(location, type);
			placesByType.put(type, places);
			LOG.info("TAKEN FROM API");
		} else {
			LOG.info("TAKEN FROM CACHE");
		}

		return places;
	}

	private List<Place> getPlacesByType(Location location, PlaceOnMapType type) {

		GooglePlaces client = new GooglePlaces(apiKey);

		List<Place> places = new ArrayList();
		try{
			places = client.getNearbyPlaces(location.getLatitude(), location.getLongitude(), RADIUS,
					MAXIMUM_RESULTS, Param.name("type").value(type.getCode()));
		} catch(Exception e) {
			LOG.info(e.getMessage());
			LOG.error("No Results found for: " + location + ", " + type);
		}

		return places;
	}
}

