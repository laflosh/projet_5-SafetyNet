/**
 * 
 */
package com.safetynet.projet_5_safetynet_api.dtos;

import java.util.List;

import com.safetynet.projet_5_safetynet_api.model.Person;

/**
 * 
 */
public class ListOfElements {

	protected List<Person> persons;
	
	//protected List<FireStations> fireStations;
	
	//protected List<MedicalRecords> medicalRecords;
	
	public List<Person> getPersons() {
		
		return persons;
		
	}
	
	public void setPersons(List<Person> persons) {
		
		this.persons = persons;
		
	}
	
}
