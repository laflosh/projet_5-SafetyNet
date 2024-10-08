package com.safetynet.projet_5_safetynet_api.model.specific;

import java.util.List;

/**
 * The data's model for person with count adult and child and getter/setter methods 
 * for unitary endpoint "/firestation?stationNumber=<station_number>"
 */
public class PersonsWithCount {

	private List<PersonSpecificInfo> persons;
	
	private int adult = 0;
	
	private int child = 0;
	
	public List<PersonSpecificInfo> getPersons(){
		return persons;
	}
	
	public void setPersons(List<PersonSpecificInfo> persons) {
		this.persons = persons;
	}
	
	public int getAdultCount() {
		return adult;
	}
	
	public void setAdultCount(int count) {
		this.adult = count;
	}
	
	public int getChildCount() {
		return child;
	}
	
	public void setChildCount(int count) {
		this.child = count;
	}
	
}
