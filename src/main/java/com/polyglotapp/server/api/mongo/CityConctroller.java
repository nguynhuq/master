package com.polyglotapp.server.api.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.polyglotapp.server.data.model.mongo.City;
import com.polyglotapp.server.data.repository.mongo.CityRepository;
import com.polyglotapp.server.exception.CityNotFoundException;


@Controller
@RequestMapping("/population")
public class CityConctroller {
	
	@Autowired
	CityRepository cityRepository;
	
	@RequestMapping(value = "/place/{placeName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> getPopulationCount(@PathVariable String placeName){
		
		Long population = cityRepository.getPopulationCountByCity(placeName);
		if (population == 0L){
			throw new CityNotFoundException();
		}
		String message = "The population count in " + placeName + " is " + String.valueOf(population);
		return new ResponseEntity<String>(message,HttpStatus.OK );
	}
	
	@RequestMapping(value = "/place/{placeName}/radius/{radius}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> getPopulationCountInRadius(@PathVariable String placeName, @PathVariable int radius){
		
		Long population = cityRepository.getPopulationCountByCityAndRadius(placeName, radius);
		if (population == 0L){
			throw new CityNotFoundException();
		}
	
		String message = "The population count in " + placeName + " in a radius of " + String.valueOf(radius) + " km, is " + String.valueOf(population);
		return new ResponseEntity<String>(message,HttpStatus.OK );
	}
	
	@RequestMapping(value = "/addplace", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void addCity(@RequestBody City city){
		city.setName(city.getName().toLowerCase());
		if (city.getLocation() != null) {
			cityRepository.addCity(city);
		}
	}
	
	
}
