package com.example.mongo.mongodemo.config;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
@EnableMongoRepositories(basePackages="com.example")
public class MongoFactoryConfig {

	private final static Logger LOGGER = LoggerFactory.getLogger(MongoFactoryConfig.class);

	@Bean
	public MongoDbFactory mongoDbFactory() {
		LOGGER.debug("Initializing Mongo Factory ----------->");
		return new SimpleMongoDbFactory(mongoClient(), "demo_db");
	}

	@Bean
	public MongoClient mongoClient() {
		LOGGER.debug("Initializing Mongo Client ----------->");
		List<ServerAddress> addr = new ArrayList<ServerAddress>();
		ServerAddress address;
		try {
			address = new ServerAddress("localhost", 27017);
			addr.add(address);
		} catch (UnknownHostException e) {
			LOGGER.error(e.getMessage(), e);
		}
		Builder builder = MongoClientOptions.builder();
		builder = builder.minConnectionsPerHost(2).connectionsPerHost(10).maxConnectionIdleTime(60000);
		MongoClientOptions mongoClientOptions = builder.build();
		//MongoCredential credentials = MongoCredential.createCredential(userName, database, password)
		MongoClient c = new MongoClient(addr, mongoClientOptions);
		return c;
	}

	@Bean
	public Mongo mongo() {
		return mongoClient();
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		LOGGER.debug("Initializing Mongo Template ----------->");
		MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory()),
				new MongoMappingContext());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(), converter);
		mongoTemplate.setReadPreference(com.mongodb.ReadPreference.secondaryPreferred());
		return mongoTemplate;

	}

}
