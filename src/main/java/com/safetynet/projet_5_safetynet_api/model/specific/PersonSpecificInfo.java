package com.safetynet.projet_5_safetynet_api.model.specific;

/**
 * The data's model for person with specific info and getter/setter methods 
 * for unitary endpoint "/firestation?stationNumber=<station_number>"
 */
public class PersonSpecificInfo {

	private String firstName;
	
	private String lastName;
	
	private String address;
	
	private String phone;
	
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
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
