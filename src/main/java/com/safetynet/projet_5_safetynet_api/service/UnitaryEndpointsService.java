package com.safetynet.projet_5_safetynet_api.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.projet_5_safetynet_api.model.Person;
import com.safetynet.projet_5_safetynet_api.repository.UnitaryEndpointsDAO;

@Service
public class UnitaryEndpointsService {

	@Autowired
	UnitaryEndpointsDAO unitaryEndpointsDAO;
	
	public List<Object> getPersonsDependingOnTheStationNumber(int stationNumber) throws ParseException {

		return unitaryEndpointsDAO.getPersonsDependingOnTheStationNumber(stationNumber);
		
	}

	public List<Object> getAllChildrenDependingOnTheAddress(String address) throws ParseException {

		return unitaryEndpointsDAO.getAllChildrenDependingOnTheAddress(address);
		
	}

	public List<String> getAllThePhoneNumberDependingOnTheFirestationNumber(int firestation) {

		return unitaryEndpointsDAO.getAllThePhoneNumberDependingOnTheFirestationNumber(firestation);
		
	}

	public List<String> getAllEmailAddressDependingOfTheCity(String city) {

		return unitaryEndpointsDAO.getAllEmailAddressDependingOfTheCity(city);
		
	}

}
