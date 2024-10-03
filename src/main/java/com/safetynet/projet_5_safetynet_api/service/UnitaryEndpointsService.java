package com.safetynet.projet_5_safetynet_api.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.projet_5_safetynet_api.model.specific.FirestationWithPersons;
import com.safetynet.projet_5_safetynet_api.model.specific.HouseMember;
import com.safetynet.projet_5_safetynet_api.model.specific.PersonWithMedicalrecordEmail;
import com.safetynet.projet_5_safetynet_api.model.specific.PersonsWithCount;
import com.safetynet.projet_5_safetynet_api.repository.UnitaryEndpointsDAO;

@Service
public class UnitaryEndpointsService {

	@Autowired
	UnitaryEndpointsDAO unitaryEndpointsDAO;
	
	public PersonsWithCount getPersonsDependingOnTheStationNumber(int stationNumber) throws ParseException {

		return unitaryEndpointsDAO.getPersonsDependingOnTheStationNumber(stationNumber);
		
	}

	public HouseMember getAllChildrenDependingOnTheAddress(String address) throws ParseException {

		return unitaryEndpointsDAO.getAllChildrenDependingOnTheAddress(address);
		
	}

	public List<String> getAllThePhoneNumberDependingOnTheFirestationNumber(int firestation) {

		return unitaryEndpointsDAO.getAllThePhoneNumberDependingOnTheFirestationNumber(firestation);
		
	}
	
	public FirestationWithPersons getAllPersonsLivingAndTheStationNumberDependingOfTheAddress(String address) {

		return unitaryEndpointsDAO.getAllPersonsLivingAndTheStationNumberDependingOfTheAddress(address);
		
	}
	
	public List<FirestationWithPersons> getAllPersonsByFirestationNumber(Integer[] stationNumbers) {

		return unitaryEndpointsDAO.getAllPersonsByFirestationNumber(stationNumbers);
		
	}
	
	public List<PersonWithMedicalrecordEmail> getAllInformationsOnAPersonDependingLastName(String lastName) {

		return unitaryEndpointsDAO.getAllInformationsOnAPersonDependingLastName(lastName);
		
	}

	public List<String> getAllEmailAddressDependingOfTheCity(String city) {

		return unitaryEndpointsDAO.getAllEmailAddressDependingOfTheCity(city);
		
	}

}
