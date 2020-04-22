package com.PhoneBook;
import java.util.Scanner;

/** Application to run the phonebook
 *  Created on 04/17/20
 *  @author BobDegnan
 */
public class Application {

	// Create a main menu option array for use in the mainMenu
	static String[] menuOptions = {"Exit", "Add New Entry", "Search By First Name", "Search By Last Name", "Search By Full Name", 
			"Search By Telephone Number", "Delete Record", "Update Record", "Show All Contacts"};

	/**
	 * mainMenu triggered by the main() method after creating a new PhoneBook object instance
	 * @author BobDegnan
	 */
	public static void mainMenu(PhoneBook phoneBook) {
			
		// Initialize variable to hold user response and set up scanner
		int intResponse = 0;
		Scanner input = new Scanner(System.in);
		
		// Always loop the menu until we get a break
		while (true) {
			// If the response from the user is not a menu option, just print it again
			while (intResponse < 1 || intResponse > menuOptions.length) {
				
				// Print options from menuOptions array
				for (int i = 1; i < menuOptions.length; i++) {
					System.out.printf("%d - %s\n", i, menuOptions[i]);
				}
				// Print the exit option last and prompt input
				System.out.printf("%d - %s\n", 0, menuOptions[0]);
				System.out.println();
				System.out.print("ENTER SELECTION: ");
				
				// Get input
				intResponse = input.nextInt();
				
				// Check if the user selected to exit
				if (menuOptions[intResponse].equals("Exit")) return;
				
				// Switch statement to call proper method from user input
				switch(intResponse)	{
					case 1:		// Add new contact
						System.out.println();
						// Prompt user for proper format and get input assigned to newContactData
						System.out.println("Enter Data as FIRST MIDDLE LAST, STREET, CITY, STATE, ZIP, #########");
						System.out.println();
						System.out.print("CONTACT DATA: ");
						input = new Scanner(System.in);
						String newContactData = input.nextLine();
						// Put newContactData into parseContactInput and send to phoneBook via .addContact()
						phoneBook.setContactList(phoneBook.addContact(parseContactInput(newContactData)));					
						break;
					case 2:		//searchByFirstName();
						System.out.println();
						// Prompt user for input and save to firstName variable
						System.out.print("Enter First Name: ");
						input = new Scanner(System.in);
						String firstName = input.next();
						// Call the method from the phonebook to search for the name
						phoneBook.searchByFirstName(firstName);
						// Make sure we had the search list populated and call the contactListMenu() method
						if (phoneBook.getSearchList().length > 0) contactListMenu(phoneBook, phoneBook.getSearchList()); 
							System.out.println();
						break;
					case 3:		//searchByLastName();
						System.out.println();
						// Prompt user for input and save to lastName variable
						System.out.print("Enter Last Name: ");
						input = new Scanner(System.in);
						String lastName = input.next();
						// Call the proper search method from the phonebook
						phoneBook.searchByLastName(lastName);
						// Make sure we had the search list populated and call the contactListMenu() method
						if (phoneBook.getSearchList().length > 0) contactListMenu(phoneBook, phoneBook.getSearchList());
							System.out.println();
						break;
					case 4:		//searchByFullName();
						System.out.println();
						// Prompt user for input and save to fullName variable
						System.out.print("Enter Full Name: ");
						input = new Scanner(System.in);
						String fullName = input.nextLine();
						// Call the proper search method from phonebook
						phoneBook.searchByFullName(fullName);
						// Make sure we had the search list populated and call the contactListMenu() method
						if (phoneBook.getSearchList().length > 0) contactListMenu(phoneBook, phoneBook.getSearchList()); 
							System.out.println();
						break;
					case 5:		//searchByTelephoneNumber();
						System.out.println();
						// Prompt user for telephone number and save to phoneNumber
						System.out.print("Enter Phone Number: ");
						input = new Scanner(System.in);
						String phoneNumber = input.next();
						// Can send straight to the contactOptionsMenu b/c the phoneNumber is used as 
						// the id to get the correct Contact.  try catch in that method checks for valid #
						contactOptionsMenu(phoneBook, phoneNumber); 
						System.out.println();
						break;
					case 6:		//deleteRecord();
						// Prompt user for input phoneNumb
						System.out.print("\nEnter Phone Number of Record to Delete: ");
						input = new Scanner(System.in);
						String phoneNumb = input.next();
						// Set a String to hold the deleted record name and the contact to delete
						String deletedRecord = null;
						Contact deletedContact = null;
						// Enter a for loop to find the proper contact
						for (Contact c : phoneBook.getContactList()) {
							if (c.equals(phoneNumb)) {
								deletedRecord = c.toString();  // Store name as record to delete
								deletedContact = c;  // Store contact to delete
							}
						}
						// Call method to delete contact
						phoneBook.deleteContact(deletedContact);
						// If the deletedRecord is not still null (AKA we deleted someone) then display who we deleted
						if (deletedRecord != null) System.out.printf("Deleted information for %s\n", deletedRecord);
						System.out.println();
						
						break;
					case 7:		//updateRecord();
						System.out.println();
						// Prompt user for input
						System.out.print("Enter Phone Number: ");
						input = new Scanner(System.in);
						String phone = input.next();
						// Open the menu for that contact
						contactOptionsMenu(phoneBook, phone); 
						System.out.println();
						break;
					case 8: // List All Contacts -- WORKING
						// Tell the phonebook to list all contacts and show the selection menu to drill down
						phoneBook.listContacts();
						contactListMenu(phoneBook, phoneBook.getContactList());
						break;
				}
				// Reset intResponse to force the menu to populate again once the program returns from other methods
				intResponse = 0;
			}
		}
	}
	
	/**
	 * Method to prompt user for an integer input corresponding to a contact
	 * @param phoneBook - the program phonebook
	 * @param contactArray - the array of contacts that we are looking at
	 */
	public static void contactListMenu(PhoneBook phoneBook, Contact[] contactArray) {
		System.out.println();
		// Initialize variables
		int intResponse = -1;
		String strResponse = "";
		
		// Set up scanner
		Scanner input = new Scanner(System.in);
		// As long as we do not have a number corresponding to a contact, keep prompting
		while (intResponse < 0 || intResponse > contactArray.length) {
			System.out.print("Enter Number of Contact or 0 for Main Menu: ");
			intResponse = input.nextInt();
		}
		// Go to Main Menu if entered "0"
		if (intResponse == 0) return;
		
		// Take the response and remove 1 for the proper index.  Get that phone number and send it to the 
		// menu for individual contacts
		String contactId = contactArray[intResponse - 1].getPhoneNumber();		
		contactOptionsMenu(phoneBook, contactId);
	}
	
	/**
	 * Menu options specific to a contact
	 * @param phoneBook - PhoneBook used in program
	 * @param contactId - phoneNumber of desired contact
	 */
	// TODO: can probably make this accept a contact instead of getting a contact, sending its number, 
	// then sorting the contact again
	public static void contactOptionsMenu(PhoneBook phoneBook, String contactId) {
		
		System.out.println();  // Add space from input line
		
		// Initialize variables
		String strResponse = "";
		Contact thisContact = null;
		
		// Look for the corresponding contact to the contactId provided
		for (Contact c : phoneBook.getContactList()) {
			if (c.equals(contactId)) {
				thisContact = c;
			}
		}
		
		// Wrap in try catch because if the number was not valid, there will be an exception since
		// thisContact var will still be null
		try {
			thisContact.toString();
		} catch (NullPointerException e) {
			System.out.println("Sorry, no contact matches that number");
			return;
		}
		
		// Call method to print the contact
		printContact(thisContact);
		
		// Provide the user with the options for the contact
		System.out.println();
		System.out.printf("1 - Edit First Name\n2 - Edit Middle Name\n3 - Edit Last Name\n4 - Edit Phone\n5 - Edit Street\n6 - Edit City"
				+ "\n7 - Edit State\n8 - Edit ZIP\n0 - Main Menu\n\nSELECTION: ");
		Scanner input = new Scanner(System.in);

		// Get the user response and send it through the switch
		int intResponse = input.nextInt();
		if (intResponse == 0) return;
		
		// All of these are getting user response and calling the correct setter method from the
		// appropriate class methods
		switch(intResponse) {
		case 1: // Update First Name
			System.out.printf("\nInput New First Name: ");
			input = new Scanner(System.in);
			strResponse = input.nextLine();
			thisContact.setFirstName(strResponse);
			break;
		case 2: // Update Middle Name
			System.out.printf("\nInput New Middle Name: ");
			input = new Scanner(System.in);
			strResponse = input.nextLine();
			thisContact.setMiddleName(strResponse);
			break;
		case 3: // Update Last Name
			System.out.printf("\nInput New Last Name: ");
			input = new Scanner(System.in);
			strResponse = input.nextLine();
			thisContact.setLastName(strResponse);
			break;
		case 4:		// Update Phone Number
			System.out.println();
			System.out.print("Input new #########: ");
			input = new Scanner(System.in);
			strResponse = input.next();
			thisContact.setPhoneNumber(strResponse);
			// In this case, we need to tell the contactId the new number
			// for when we call the menu again
			contactId = thisContact.getPhoneNumber();
			break;
		case 5:  // Update Street
			System.out.printf("\nInput New Street: ");
			input = new Scanner(System.in);
			strResponse = input.nextLine();
			thisContact.getAddress().setStreet(strResponse);
			break;
		case 6:  // Update City
			System.out.printf("\nInput New City: ");
			input = new Scanner(System.in);
			strResponse = input.nextLine();
			thisContact.getAddress().setCity(strResponse);
			break;
		case 7:  // Update State
			System.out.printf("\nInput New State: ");
			input = new Scanner(System.in);
			strResponse = input.next();
			thisContact.getAddress().setState(strResponse);
			break;
		case 8:  // Update Street
			System.out.printf("\nInput New Zip: ");
			input = new Scanner(System.in);
			strResponse = input.next();
			thisContact.getAddress().setZip(strResponse);
			break;
		}
		
		// Call the menu again so the user can see the updated contact and continue editing other fields
		contactOptionsMenu(phoneBook, contactId);

	}
	

	/**
	 * Print the contact in the desired format
	 * @param contact
	 */
	public static void printContact(Contact contact) {
		// Print with the name, phone, and address on separate lines, indenting phone and address
			System.out.printf("\n%s\n\t%s\n\t%s\n", contact.toString(), contact.getFormattedPhoneNumber(), contact.getAddress().toString());
	}
	
	// WORKING
	/** 
	 * Method to take the anticipated new contact input structure and parse it into a contact to be added
	 * Anticipated Format: firstName middleName lastName, street, city, state, zip, phoneNumber (as #########)
	 * @param contactDataString - Input provided by user representing the new contact
	 * @return newContact - New contact generated from construction of parsed data array
	 */
	public static Contact parseContactInput(String infoSource) {
		System.out.println(); // Add space from data entry line
		// Set new array to hold necessary data
		String[] dataArray = new String[8];
		
		// Set helpful variables for later use.  These are all the comma delimiters in the string input
		int nameComma = infoSource.indexOf(",");
		int streetComma = infoSource.indexOf(",", nameComma + 1);
		int cityComma = infoSource.indexOf(",", streetComma + 1);
		int stateComma = infoSource.indexOf(",", cityComma + 1);
		int zipComma = infoSource.indexOf(",", stateComma + 1);
		
		// Extract the contact Name
		// The first name should be all of the string up to the first space
		dataArray[0] = infoSource.substring(0, infoSource.indexOf(" ")).trim();
		// The last name is all from the first comma, back through to a space proceeding that comma
		dataArray[2] = infoSource.substring(infoSource.lastIndexOf(" ", nameComma) + 1, nameComma).trim();
		// the middle name is from the first space to the start of the last name
		dataArray[1] = infoSource.substring(infoSource.indexOf(" ") + 1, infoSource.indexOf(dataArray[2])).trim();
		
		// Extract Address.  The address starts immediately after the nameComma
		dataArray[4] = infoSource.substring(nameComma + 2, streetComma).trim(); // Get street
		dataArray[5] = infoSource.substring(streetComma + 2, cityComma).trim(); // Get city
		dataArray[6] = infoSource.substring(cityComma + 2, stateComma).trim(); // Get state
		dataArray[7] = infoSource.substring(stateComma + 2, zipComma).trim(); // Get zip
		
		// Extract Phone.  This is the last piece of data provided and should be from the last space onward
		dataArray[3] = infoSource.substring(infoSource.lastIndexOf(" ") + 1).trim();
		
		// Assign the data to a new contact
		Contact newContact = new Contact(dataArray[0], dataArray[1], dataArray[2], dataArray[3],
				new Address (dataArray[4], dataArray[5], dataArray[6], dataArray[7]));
		
		return newContact;
	}
	
	/**
	 * Main Method to kick off program
	 * @param args
	 */
	public static void main(String[] args) {
		// Create a new PhoneBook and send that sucker through to start the fun	
		PhoneBook myPhoneBook = new PhoneBook();
		mainMenu(myPhoneBook);
	}
}
