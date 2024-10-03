package com.safetynet.projet_5_safetynet_api.model.specific;

/**
 * The data's model for parent and getter/setter methods for unitary endpoint "/childAlert?address=<address>"
 */
public class Parent {

	private String firstName;
	
	private String lastName;
	
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
	
}
