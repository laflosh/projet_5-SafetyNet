package com.safetynet.projet_5_safetynet_api.specificModels;

import java.util.List;

import com.safetynet.projet_5_safetynet_api.model.Firestation;

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
