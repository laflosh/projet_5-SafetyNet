package com.safetynet.projet_5_safetynet_api.model.specific;

/**
 * The data's model for person with specific info and getter/setter methods 
 * for unitary endpoint "/personInfolastName=<lastName>"
 */
public class PersonWithMedicalrecordEmail {

	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String birthdate;
	
	private String[] medications;
	
	private String[] allergies;
	
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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getBirthdate() {
		return birthdate;
	}
	
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	
	public String[] getMedications() {
		return medications;
	}
	
	public void setMedications(String[] medications) {
		this.medications = medications;
	}
	
	public String[] getAllergies() {
		return allergies;
	}
	
	public void setAllergies(String[] allergies) {
		this.allergies = allergies;
	}
	
	
}
