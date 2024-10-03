package com.safetynet.projet_5_safetynet_api.model.specific;

import java.util.List;

import com.safetynet.projet_5_safetynet_api.model.Firestation;

/**
 * The data's model for firestation and a list of person and getter/setter methods 
 * for unitary endpoint "/fire?address=<address>" 
 * and "/flood/stations?stations=<a list of station_numbers>"
 */
public class FirestationWithPersons {

	private Firestation firestation;
	
	private List<PersonWithMedicalrecordPhone> persons;
	
	public Firestation getFirestation() {
		return firestation;
	}
	
	public void setFirestation(Firestation firestation) {
		this.firestation = firestation;
	}
	
	public List<PersonWithMedicalrecordPhone> getPersons(){
		return persons;
	}
	
	public void setPersons(List<PersonWithMedicalrecordPhone> persons) {
		this.persons = persons;
	}
	
}
