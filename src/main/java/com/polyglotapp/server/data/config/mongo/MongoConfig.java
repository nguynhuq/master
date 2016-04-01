package com.polyglotapp.server.data.config.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;

@Configuration
@PropertySource(value = { "classpath:mongodb.properties" })
public class MongoConfig {

	@Autowired
    private Environment environment;
	
	@Bean
	public MongoDbFactory MongoDbFactory() throws Exception, MongoException {
		String host = environment.getProperty("mongodb.client");
		int port = Integer.parseInt(environment.getProperty("mongodb.port"));
		String dbname = environment.getProperty("mongodb.dbname");
        return new SimpleMongoDbFactory(new MongoClient(host,port), dbname);
    }
	
	@Bean
	public MongoTemplate MongoTemplate() throws Exception, MongoException {
        MongoTemplate mongoTemplate = new MongoTemplate(MongoDbFactory());
        return mongoTemplate;
    }
	
}
