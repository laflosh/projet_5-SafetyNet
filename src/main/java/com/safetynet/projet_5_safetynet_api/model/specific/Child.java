package com.safetynet.projet_5_safetynet_api.model.specific;

/**
 * The data's model for child and getter/setter methods for unitary endpoint "/childAlert?address=<address>"
 */
public class Child {

	private String firstName;
	
	private String lastName;
	
	private String birthdate;
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;		
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getBirthdate() {
		return birthdate;
	}
	
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	
}
