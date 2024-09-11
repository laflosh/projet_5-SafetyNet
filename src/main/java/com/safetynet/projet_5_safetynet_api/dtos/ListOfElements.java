/**
 * 
 */
package com.safetynet.projet_5_safetynet_api.dtos;

import java.util.List;

import com.safetynet.projet_5_safetynet_api.model.FireStations;
import com.safetynet.projet_5_safetynet_api.model.MedicalRecords;
import com.safetynet.projet_5_safetynet_api.model.Person;

/**
 * 
 */
public class ListOfElements {

	protected List<Person> persons;
	
	protected List<FireStations> fireStations;
	
	protected List<MedicalRecords> medicalRecords;
	
	public List<Person> getPersons() {
		
		return persons;
		
	}
	
	public void setPersons(List<Person> persons) {
		
		this.persons = persons;
		
	}
	
	public List<FireStations> getFireStations() {
		
		return fireStations;
		
	}
	
	public void setFireStations(List<FireStations> fireStations) {
		
		this.fireStations = fireStations;
		
	}
	
	public List<MedicalRecords> getMedicalRecords() {
		
		return medicalRecords;
		
	}
	
	public void setMedicalRecords(List<MedicalRecords> medicalRecords) {
		
		this.medicalRecords = medicalRecords;
		
	}
	
}
