package com.safetynet.projet_5_safetynet_api.model;

public class MedicalRecords {

	private String firstName;
	
	private String lastName;
	
	private String birthday;
	
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
	
	public String getBirthday() {
		
		return birthday;
		
	}
	
	public void setBirthday(String birthday) {
		
		this.birthday = birthday;
		
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
