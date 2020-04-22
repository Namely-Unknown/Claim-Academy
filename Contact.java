package com.PhoneBook;

/** Contact Class
 *  Created 04/17/20
 *  @author BobDegnan
 */
public class Contact {

	private String firstName;
	private String middleName;
	private String lastName;
	private String phoneNumber;
	private Address address;
	
	// Constructor
	// The Address address requires a new Address(street, city, state, zip)
	public Contact(String firstName, String middleName, String lastName, String phoneNumber, Address address) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	// *****GETTERS & SETTERS *****
	
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	/**
	 * Method to return the formatted phone number
	 * @return formattedNumber - Formated as (###)-###-####
	 */
	public String getFormattedPhoneNumber() {
		String regexPattern = "(\\d{3})(\\d{3})(\\d+)";
		String regexFormat = "($1)-$2-$3";
		return this.phoneNumber.replaceFirst(regexPattern, regexFormat);
	}
	
	// @overridden method for toString.  Will return name in desired format
	public String toString() {
		return this.lastName + ", " + this.firstName + " " + this.middleName;
	}
	
	/**
	 * @overridden method for equals
	 * @param phoneNumber - String representation of a phone number
	 * @return boolean - True if phoneNumber matches this.phoneNumber
	 */
	public boolean equals(String phoneNumber) {
		// Check the provided phone number vs this instance of Contact phone number
		// Use ".equals()" and not "==" because these are string types
		return this.phoneNumber.equals(phoneNumber);
	}
}
