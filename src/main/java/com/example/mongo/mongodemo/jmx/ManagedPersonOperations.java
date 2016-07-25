package com.example.mongo.mongodemo.jmx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.example.mongo.mongodemo.operations.SampleOperations;
import com.example.mongo.mongodemo.scheduler.SampleScheduler;

@Component
@ManagedResource(
		objectName="modelmbean:type=spring-annotated")
public class ManagedPersonOperations {
	
	@Autowired
	SampleOperations operations;
	
	@Autowired
	SampleScheduler scheduler;
	
	@ManagedOperation(description = "Insert Person")
	public void insertPerson(){
		operations.insertPerson();
	}
	
	@ManagedOperation(description = "Saving a person")
	public void savePerson(long id){
		operations.updatePerson(id);
	}
	
	@ManagedOperation(description = "Search a person")
	public void findPersonEntityRepo(long id){
		operations.findPersonUsingRepo(id);
	}
	
	@ManagedOperation(description = "Save a person")
	public void savePersonEntityRepo(long id){
		operations.insertPersonEntityRepo();
	}
	
	/*@ManagedOperation(description = "Starting Scheduler")
	public void schedule(){
		scheduler.schedule();
	}*/
}
