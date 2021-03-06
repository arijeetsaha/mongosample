package com.example.mongo.mongodemo.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AddressEntity {
	@Id
	private ObjectId id;
	private String houseNumber;
	private String streetNumber;
	private String streetName;
	private String city;
	private String zipCode;
	public String getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	public String getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	@Override
	public String toString() {
		return "Address [houseNumber=" + houseNumber + ", streetNumber=" + streetNumber + ", streetName=" + streetName
				+ ", city=" + city + ", zipCode=" + zipCode + "]";
	}
	
}
