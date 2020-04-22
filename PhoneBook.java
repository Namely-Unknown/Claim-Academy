package com.PhoneBook;

/**
 * PhoneBook Class to hold an array of contacts
 * Created 04/17/20
 * @author BobDegnan
 */

public class PhoneBook {
	
	// Pre-Seeded Array for test cases
	/*private Contact[] contactList = {new Contact("Rodger", "M", "Ericson", "3145552833", new Address("123 Main St.", "St. Louis", "MO", "63001")), 
			new Contact("Eric", "S", "Stewart", "3145558373", new Address("15917 Main Cove Ct.", "Ballwin", "MO", "63021")), 
				new Contact("Bill", "Richard Doyle", "Smith", "3145556487", new Address("502 Lemon Shire Dr.", "Manchester", "MO", "63011")), 
				new Contact("Nick", "M. S.", "Miller", "3145558371", new Address("508 Carter Place Dr.", "Manchester", "MO", "63011")), 
				new Contact("Carole", "N", "Thompson", "3145558375", new Address("1917 Main Cove Ct.", "Ballwin", "MO", "63021")),
				new Contact("Thomas", "L", "Gurney", "3125552733", new Address("123 Melanie Ct.", "St. Louis", "MO", "63001"))};
	*/

	// Main contactList array for the phone book
	private Contact[] contactList = new Contact[0];
	// Empty searchList initialized for when needing to return a list of Contacts based on a search
	private Contact[] searchList = new Contact[0];
	
	// Blank constructor
	public PhoneBook() {
	}
	
	// *****GETTERS & SETTERS*****
	public Contact[] getContactList() {
		return this.contactList;
	}
	
	public void setContactList(Contact[] contactList) {
		this.contactList = contactList;
	}
	
	public Contact[] getSearchList() {
		return this.searchList;
	}
	
	public void setSearchList(Contact[] contactList) {
		this.searchList = contactList;
	}
	
	/**
	 * Default method for listing contacts. Pushes the contactList to the overridden 
	 * method if another array (searchList) is not provided 
	 */
	public void listContacts() {
		this.listContacts(this.contactList);
	}
	
	/**
	 * Overloaded listContacts method to allow for specification of array to list contacts
	 * @param sourceArray - Contact[] type array
	 * @return VOID - Lists the contacts in the provided array
	 */
	public void listContacts(Contact[] sourceArray) {
		// Call a sort on the array prior to printing
		sourceArray = this.sortContacts(sourceArray);
		
		// Provide a header
		System.out.println("CONTACTS:");
		
		// Check if no contacts in the provided array, notify user, and return, if so
		if (sourceArray.length < 1) { 
			System.out.println("No results returned!");
			return;
		}
		
		// Initialize an integer to serve as a selection number for each contact
		int i = 1;
		
		// For each contact in the array, print its selection number, name, phone number, and address
		for (Contact c : sourceArray) {
			System.out.printf("\n%3d - %s\n\t%s\n\t%s\n", i++, c.toString(), c.getFormattedPhoneNumber(), 
					c.getAddress().toString());
		}	 
	}
	
	/**
	 * Method to selection sort contacts by LastFirstMiddle
	 * @param sourceArray - Contact[] array
	 * @return sourceArray - Sorted Contact[] array
	 */
	public Contact[] sortContacts(Contact[] sourceArray) {
		
		// Set for loop to work through each contact in the provided sourceArray
		for (int i = 0; i < sourceArray.length; i++) {
			
			// Get the current contact in loop and the concatenated lastName & firstName & middleName
			// from that contact in an unbroken string
			Contact currentContact = sourceArray[i];
			String currentContactString = String.join("", sourceArray[i].getLastName(), sourceArray[i].getFirstName(),
					 sourceArray[i].getMiddleName());
			
			// Set up a new integer to be used in the nested for looping 
			// Need to initialize outside of nested for loop because it will
			// be used after the loop to assign the new index to the "currentContact" selected above
			int j;
			
			// As long as the j index (left of the currentContact) concatenated string is larger than the currentContact
			// concatenated string, we need to keep going shuffling the j index "to the right" one index
			for (j = i - 1; j >= 0 && String.join("", sourceArray[j].getLastName(), sourceArray[j].getFirstName(),
							sourceArray[i].getMiddleName()).compareTo(currentContactString) >= 0; j--) {
				// Move current j index over into the sourceArray index slot one "to the right"
				sourceArray[j + 1] = sourceArray[j];
			}
			// Once we have broken the loop, it means the j index sits at a value that is earlier in alphabetical
			// order than our "currentContact".
			// Replace the element one to the right of this (currently a duplicate) with the currentContact
			sourceArray[j + 1] = currentContact;
		}
		// return result
		return sourceArray;
	}
	
	/**
	 * Default method for adding a contact.  Calls overloaded method passing the contactList as the sourceArray
	 * @param newContact - Contact object instance to be added
	 * @return tempArray - Contact[] array with newContact added
	 */
	public Contact[] addContact(Contact newContact) {
		return this.addContact(this.contactList, newContact);
	}
	
	/**
	 * Overloaded addContact method which will take an alternate array if provided
	 * @param sourceArray - An array for the method to add the contact into
	 * @param newContact - A Contact class object to be added into the provided array
	 * @return tempArray - A newly built array with the added newContact
	*/
	public Contact[] addContact(Contact[] sourceArray, Contact newContact) {
		
		// Set new tempArray one index larger than sourceArray and copy components from sourceArray		
		Contact[] tempArray = new Contact[sourceArray.length + 1];
		for (int i = 0; i < sourceArray.length; i++) {
			tempArray[i] = sourceArray[i];
		}
		
		// Assign the provided newContact to the last element of the tempArray	
		tempArray[sourceArray.length] = newContact;
		
		// return the tempArray so it can be assigned
		return tempArray;
	}
	
	/**
	 * Method to search for a contact based on a provided firstName
	 * @param firstName - String of desired contact to find
	 * @return void - Will call listContacts() method with the updated searchList array
	*/
	public void searchByFirstName(String firstName) {
		searchList = this.clearContacts();  // Call the method to return an empty array
		for (Contact c : this.contactList)  // Loop through all contacts and add any who match the firstName
			if (c.getFirstName().equalsIgnoreCase(firstName)) this.setSearchList(this.addContact(searchList, c));  

		this.listContacts(searchList); // List the contacts gathered by calling listContacts method
	}
	
	/**
	 * Method to search for a contact based on a provided lastName
	 * @param lastName - String of desired contact to find
	 * @return void - Will call listContacts() method with the updated searchList array
	*/
	public void searchByLastName(String lastName) {
		searchList = this.clearContacts(); // Call the method to return an empty array
		for (Contact c : this.contactList) // Loop through all contacts and add any who match the lastName
			if (c.getLastName().equalsIgnoreCase(lastName)) this.setSearchList(this.addContact(searchList, c));

		this.listContacts(searchList); // List the contacts gathered by calling listContacts method
	}

	/**
	 * Method to search for a contact based on a provided fullName
	 * @param fullName - String of desired contact to find
	 * @return void - Will call listContacts() method with the updated searchList array
	*/
	// TODO: use a StringBuffer instead of a String to save on heap
	public void searchByFullName(String fullName) {
		searchList = this.clearContacts(); // Call the method to return an empty array
		String testString = "";
		for (Contact c : this.contactList) { // Loop through all contacts and add any who match the fullName
			if (c.getMiddleName().length() != 0) { // If the middle name is longer than 0, add it in
				testString = c.getFirstName() + " " + c.getMiddleName() + " " + c.getLastName();
			} else { // Otherwise, we just need a space
				testString = c.getFirstName() + " " + c.getLastName();
			} // Test the testString vs the fullName var, ignoring case
			if (testString.equalsIgnoreCase(fullName)) this.setSearchList(this.addContact(searchList, c));
		}
		this.listContacts(searchList); // List the contacts gathered by calling listContacts method
	}
	
	/** 
	 * Method to search for a contact match based on a phone number
	 * @param phoneNumber - String representation of phoneNumber for desired contact
	 * @return void - Will call listContacts() method with the updated searchList array
	 */
	public void searchByPhoneNumber(String phoneNumber) {
		searchList = this.clearContacts(); // Call the method to return an empty array
		for (Contact c : this.contactList) {// Loop through all contacts and find the one who matches the unique number.
			if (c.equals(phoneNumber)) {
				this.setSearchList(this.addContact(searchList, c));
				break; // Save from continuing loop when not necessary
			}
		}
		this.listContacts(searchList); // Call method to list the contact
	}
	
	/**
	 * Method to return an empty Contact[] array.
	 * Used prior to searching so the searchArray is empty
	 * @param NONE
	 * @return Contact[0] - Empty Contact[] array
	 */
	public Contact[] clearContacts() {
		return new Contact[0];
	}
	
	/** 
	 * Method to remove a contact from the main contactList
	 * @param x - Contact object instance to be removed from this.contactList
	 * @return VOID
	 */
	public void deleteContact(Contact x) {
		// Set a newArray to hold a number of contacts equal to the old contactList - 1
		try {
		Contact[] newArray = new Contact[this.contactList.length - 1];
		int j = 0; // Set an integer for the new array index
		for (Contact c : this.contactList) { // Loop through all elements in current this.contactList
			if (!c.equals(x.getPhoneNumber())) newArray[j++] = c; // If Contact c does NOT match provided contact, 
			// add the Contact to the newArray index j and increment j
		}
		this.contactList = newArray; // Return newArray for assignment
		} catch (NegativeArraySizeException e) {
			System.out.println();
			System.out.println("No contacts in phonebook!");
		}
	}
}