package com.polyglotapp.server.data.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="city")
public class City {

	@Id
	private String id;
	private String name;
	private String country;
	//2d index should be created in mongoDB on this field
	@Field("location")
	private double [] location;
	private Long population;
	
	
	public City(){
		
	}
	
	public City(String name, String country, Long population, double longitude, double latitude) {
		this.name = name;
		this.country = country;
		this.population = population;
		this.location = new double[] {longitude, latitude};
		this.id = name + country;
	}
	
	public String getId() {
		return id;	
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setLocation(double longitude, double latitude){
		this.location = new double[] {longitude, latitude};
	}
	
	public double [] getLocation(){
		return this.location;
	}
	
	public void setPopulation(Long population){
		this.population = population;
	}
	
	public Long getPopulation(){
		return this.population;
	}
	
	public void setCountry(String country){
		this.country = country;
	}
	
	public String getCountry(){
		return this.country;
	}
	
	@Override
    public String toString() {
		return String.format("City[id='%s', Name='%s', Population='%d', Longitude='%f', latitude='%f']"
				, id, name, population, location[0], location[1]);
	}
	
}
