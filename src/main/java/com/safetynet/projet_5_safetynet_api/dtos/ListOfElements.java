package com.safetynet.projet_5_safetynet_api.dtos;

import java.util.List;

import com.safetynet.projet_5_safetynet_api.model.Firestations;
import com.safetynet.projet_5_safetynet_api.model.Medicalrecords;
import com.safetynet.projet_5_safetynet_api.model.Person;

/**
 * 
 */
public class ListOfElements {

	protected List<Person> persons;
	
	protected List<Firestations> firestations;
	
	protected List<Medicalrecords> medicalrecords;
	
	public List<Person> getPersons() {
		
		return persons;
		
	}
	
	public void setPersons(List<Person> persons) {
		
		this.persons = persons;
		
	}
	
	public List<Firestations> getFirestations() {
		
		return firestations;
		
	}
	
	public void setFirestations(List<Firestations> firestations) {
		
		this.firestations = firestations;
		
	}
	
	public List<Medicalrecords> getMedicalrecords() {
		
		return medicalrecords;
		
	}
	
	public void setMedicalrecords(List<Medicalrecords> medicalrecords) {
		
		this.medicalrecords = medicalrecords;
		
	}
	
}
