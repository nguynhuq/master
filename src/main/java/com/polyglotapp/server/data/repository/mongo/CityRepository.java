package com.polyglotapp.server.data.repository.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.polyglotapp.server.data.model.mongo.City;


@Repository
public class CityRepository {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
    @Cacheable(value = "cityCache", key = "#cityName")
	public City getCityByName (String cityName){
    	Query query = new Query(Criteria.where("name").is(cityName.toLowerCase()));
		return mongoTemplate.findOne(query, City.class);
	}
	
    @Cacheable(value = "cityCache", key = "#cityName")
	public long getPopulationCountByCity(String cityName) {
    	City city = getCityByName(cityName);
    	Long population = 0L;
		if (city != null) {
			population = city.getPopulation();
		}
		return population;
	}
	
    @Cacheable(value = "cityCache", key = "{#cityName, #radius}")
	public long getPopulationCountByCityAndRadius(String cityName, int radius) {
    	long popCount = 0L;
		Point point = getCityCoordinatesByName(cityName);
		if (point != null) {
    		NearQuery query = NearQuery.near(point).maxDistance(new Distance(radius, Metrics.KILOMETERS));		
    		GeoResults<City> results = mongoTemplate.geoNear(query, City.class);
			
    		for (GeoResult<City> result : results.getContent()) {			
    			popCount += result.getContent().getPopulation();		
    		}  	
    	}
		return popCount;
	}
    
	
    @Cacheable(value = "cityCache", key = "#cityName")
	public Point getCityCoordinatesByName(String cityName) {
		City city = getCityByName(cityName);
		Point location = null;
		if (city != null){
			location = new Point(city.getLocation()[0], city.getLocation()[1]);			
		}
		return location;
	}
    
    public void addCity(City city) {
    	if (mongoTemplate.findById(city.getId(), City.class) == null) {
        	//ensure the city name is stored in lower case 
        	city.setName(city.getName().toLowerCase());
    		mongoTemplate.save(city);
    	} else {
    		Query query = new Query(Criteria.where("id").is(city.getId()));
    		Update update = new Update().set("population", city.getPopulation());
    		mongoTemplate.findAndModify(query, update, City.class);
    	}
    }
}
