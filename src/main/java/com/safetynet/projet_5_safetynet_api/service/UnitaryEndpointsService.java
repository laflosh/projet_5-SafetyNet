package com.safetynet.projet_5_safetynet_api.service;

import java.text.ParseException;
import java.util.List;

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

	public List<?> getAllChildrenDependingOnTheAddress(String address) {

		return unitaryEndpointsDAO.getAllChildrenDependingOnTheAddress(address);
		
	}

}
