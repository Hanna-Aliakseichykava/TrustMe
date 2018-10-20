package com.epam.mapsexample;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.epam.mapsexample.db.model.AppUser;
import com.epam.mapsexample.db.service.UserService;
import com.epam.mapsexample.model.Location;
import com.epam.mapsexample.model.PlaceType;
import com.epam.mapsexample.service.CurrentLocationService;
import com.epam.mapsexample.service.PlacesService;

import se.walkercrou.places.Place;

@SpringBootApplication
public class App {

	private static Logger LOG = LoggerFactory.getLogger(App.class);

	@Autowired
	CurrentLocationService locationService;

	@Autowired
	PlacesService placesService;
	
	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			LOG.debug("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				LOG.debug(beanName);
			}

			/*Location location = locationService.getCurrentLocation(true);
			LOG.info(location.toString());

			List<Place> places = placesService.getPlaces(location, PlaceType.CAFE);
			LOG.info(places.toString());

			List<Place> places2 = placesService.getPlaces(location, PlaceType.PARK);
			LOG.info(places2.toString());

			// Check cache
			List<Place> places3 = placesService.getPlaces(location, PlaceType.CAFE);
			LOG.info(places3.toString());*/
			
			List<AppUser> users = userService.getAllUsers();
			LOG.info("Users: " + users);

		};
	}

}