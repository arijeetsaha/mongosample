package com.example.mongo.mongodemo.operations;

import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.example.mongo.mongodemo.bean.Person;
import com.example.mongo.mongodemo.entity.PersonEntity;
import com.example.mongo.mongodemo.repo.PersonRepository;

@Component
public class SampleOperations {
	
	private static final Logger LOG = LoggerFactory.getLogger(SampleOperations.class);

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	PersonRepository repo;
	
	AtomicLong counter;
	
	@PostConstruct
	void initialize(){
		counter = new AtomicLong();
	}
	
	public void insertPerson(){
		Person p = new Person(counter.incrementAndGet(),"FN", "LN");
		mongoTemplate.insert(p, "person");
	}
	
	public Person findPersonById(long id){
		return mongoTemplate.findById(id, Person.class, "person");
	}
	
	public void updatePerson(Long id){
		Person p = findPersonById(id);
		LOG.debug("Person : {}",p);
		if(p != null){
			p.setLastName("Changed LN");
			mongoTemplate.save(p, "person");
		}
	}
	
	public PersonEntity findPersonUsingRepo(long id){
		return repo.findOne(id); 
	}
	
	public void insertPersonEntityRepo(){
		PersonEntity p = new PersonEntity(counter.incrementAndGet(),"F_N", "L_N");
		PersonEntity e = repo.save(p);
		LOG.debug("Person Entity : {}", e);
	}
	
}
