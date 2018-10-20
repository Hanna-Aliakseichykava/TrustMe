package org.trustme.service.model;

public class Location {

	private double latitude;
	private double longitude;

	public Location(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Location [latitude=" + latitude + ", longitude=" + longitude + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof Location)) {
			return false;
		}
		Location other = (Location) o;

		double latitudeDiff = this.latitude - other.latitude;
		double longitudeDiff = this.longitude - other.longitude;
		if (Math.abs(latitudeDiff) <= 0.000001 && Math.abs(longitudeDiff) <= 0.000001) {
			return true;
		}

		return false;
	}

}

