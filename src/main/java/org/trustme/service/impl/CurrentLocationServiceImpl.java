package org.trustme.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.trustme.service.CurrentLocationService;
import org.trustme.service.model.Location;
import org.json.JSONException;
import org.json.JSONObject;



@Service
public class CurrentLocationServiceImpl implements CurrentLocationService {

	private static Logger LOG = LoggerFactory.getLogger(CurrentLocationServiceImpl.class);

	//@Value("${api.key}")
	//private String apiKey = "undefined";
	private String apiKey = "AIzaSyAfHkXgg236YmiXVOsthl4eCPAhYMLuMds";

	@Override
	public Location getCurrentLocation(boolean mock) {
		if (mock) {
			return new Location(new Double(53.9168), new Double(30.3449));
			//{ "location": {  "lat": 53.868439699999996,  "lng": 30.2950485 }, "accuracy": 6139.0}
		}

		return getCurrentLocation();
	}

	@Override
	public Location getCurrentLocation() {

		String jsonText = sendPost("https://www.googleapis.com/geolocation/v1/geolocate?key=" + apiKey);

		LOG.info(jsonText);

		return toLocation(jsonText);

	}

	private String sendPost(String url) {

		try {
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			// add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String urlParameters = "";

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			LOG.debug("\nSending 'POST' request to URL : " + url);
			LOG.debug("Post parameters : " + urlParameters);
			LOG.debug("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			String textResponse = response.toString();
			// print result
			LOG.debug(textResponse);

			return textResponse;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Location toLocation(String jsonText) {
		Location location = null;

		try {
			JSONObject json = null;
			json = new JSONObject(jsonText);
			location = new Location(json.getJSONObject("location").getDouble("lat"), json.getJSONObject("location").getDouble("lng"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}

		return location;
	}
}

