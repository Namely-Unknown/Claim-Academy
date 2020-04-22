package com.PhoneBook;

/** Address Class
 *  Created 04/17/20
 *  @author BobDegnan
 */

public class Address {

	private String street;
	private String city;
	private String state;
	private String zip;
	
	// Constructor
	public Address(String street, String city, String state, String zip) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	// ***** GETTERS & SETTERS *****
	
	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	// @overridden toString method
	public String toString() {
		return this.street + ", " + this.city + ", " + this.state + " " + this.zip;
		// 124 Main St, Ballwin, MO 63021
	}
	
	// Do not create an equals method because we will not be checking based on this.
	
}
