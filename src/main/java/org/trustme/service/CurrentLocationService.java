package org.trustme.service;

import org.trustme.service.model.Location;

public interface CurrentLocationService {

	Location getCurrentLocation(boolean mock);
	Location getCurrentLocation();
}

