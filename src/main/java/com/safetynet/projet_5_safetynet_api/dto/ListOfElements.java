package com.safetynet.projet_5_safetynet_api.dto;

import java.util.List;

import com.safetynet.projet_5_safetynet_api.model.Firestation;
import com.safetynet.projet_5_safetynet_api.model.Medicalrecord;
import com.safetynet.projet_5_safetynet_api.model.Person;

/**
 * 
 */
public class ListOfElements {

	protected List<Person> persons;
	
	protected List<Firestation> firestations;
	
	protected List<Medicalrecord> medicalrecords;
	
	public List<Person> getPersons() {
		
		return persons;
		
	}
	
	public void setPersons(List<Person> persons) {
		
		this.persons = persons;
		
	}
	
	public List<Firestation> getFirestations() {
		
		return firestations;
		
	}
	
	public void setFirestations(List<Firestation> firestations) {
		
		this.firestations = firestations;
		
	}
	
	public List<Medicalrecord> getMedicalrecords() {
		
		return medicalrecords;
		
	}
	
	public void setMedicalrecords(List<Medicalrecord> medicalrecords) {
		
		this.medicalrecords = medicalrecords;
		
	}
	
}
