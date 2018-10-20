package org.trustme.service.model;

public enum PlaceOnMapType {

	AMUSEMENT_PARK("amusement_park"),
	AQUARIUM("aquarium"),
	ART_GALLERY("art_gallery"),
	BAKERY("bakery"),
	BAR("bar"),
	BEAUTY_SALON("beauty_salon"),
	BOOK_STORE("book_store"),
	CAFE("cafe"),
	DEPARTMENT_STORE("department_store"),
	GYM("gym"),
	MOVIE_THEATER("movie_theater"),
	MUSEUM("museum"), 
	NIGHT_CLUB("night_club"),
	PARK("park"),
	RESTAURANT("restaurant"), 
	TADIUM("stadium"), 
	ZOO("zoo");

	private String code;

	private PlaceOnMapType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
