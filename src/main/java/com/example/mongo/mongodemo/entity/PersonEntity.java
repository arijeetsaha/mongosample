package com.example.mongo.mongodemo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.mongo.mongodemo.bean.Address;

@Document
public class PersonEntity {

	@Id
	private Long id;
	private String firstname;
	private String lastname;
	@DBRef
	private Address address;
	
	

	public Long getId() {
		return id;
	}
	public PersonEntity(Long id, String firstname, String lastname) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	@Override
	public String toString() {
		return "PersonEntity [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + "]";
	}
	
}
