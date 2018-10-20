package org.trustme.service;

import java.util.List;

import org.trustme.service.model.Location;
import org.trustme.service.model.PlaceOnMapType;

import se.walkercrou.places.Place;

public interface PlacesService {

	List<Place> getPlaces(Location location, PlaceOnMapType type);

}

