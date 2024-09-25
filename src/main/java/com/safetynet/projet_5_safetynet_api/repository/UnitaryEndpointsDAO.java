package com.safetynet.projet_5_safetynet_api.repository;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.safetynet.projet_5_safetynet_api.dtos.ListOfElements;
import com.safetynet.projet_5_safetynet_api.model.Firestation;
import com.safetynet.projet_5_safetynet_api.model.Medicalrecord;
import com.safetynet.projet_5_safetynet_api.model.Person;
import com.safetynet.projet_5_safetynet_api.util.DataManager;

import jakarta.annotation.PostConstruct;

@Repository
public class UnitaryEndpointsDAO {
	
	private static Logger logger = LogManager.getLogger("UnitaryEndpointsDAO");
	
	@Autowired
	DataManager dataManager;
	
	ListOfElements elements = null;
	
	List<Person> persons = null;
	List<Firestation> firestations = null;
	List<Medicalrecord> medicalrecords = null;
	
	/**
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@PostConstruct
	public void initData() throws StreamReadException, DatabindException, IOException {
		
		if(elements == null) {
			
			logger.info("Fetching all data saved in the JSON file.");
			elements = dataManager.getAllData();
			persons = elements.getPersons();
			firestations = elements.getFirestations();
			medicalrecords = elements.getMedicalrecords();
			
		}
		
	}

	/**
	 * @param stationNumber
	 * @return
	 * @throws ParseException 
	 */
	public List<Object> getPersonsDependingOnTheStationNumber(int stationNumber) throws ParseException {
		
		//Map for counting how many adult and child depending of the station number
		Map<String, Integer> countPersonMap = new HashMap<String, Integer>();
		countPersonMap.put("adult", 0);
		countPersonMap.put("child", 0);
		
		//firestationsRequest will have only the stations and address equal to the stationNumber variable
		List<Firestation> firestationsRequest = new ArrayList<Firestation>();
		for(Firestation firestation : firestations) {
			
			if(firestation.getStation() == stationNumber) {
				
				firestationsRequest.add(firestation);
				
			}
			
		}
		
		//Keep only the persons who have the same address as the stationNumber 
		//Count adult and child depending on the birthdate in medicalrecords
		List<Object> filterPersons = new ArrayList<Object>();
		
		for(Person person : persons) {
			
			for(Firestation firestationRequest : firestationsRequest) {
				
				if(firestationRequest.getAddress().equals(person.getAddress())) {
					
					filterPersons.add(person);
					
					for(Medicalrecord medicalrecord : medicalrecords) {
						
						if(medicalrecord.getFirstName().equals(person.getFirstName()) && medicalrecord.getLastName().equals(person.getLastName())) {
							
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							Date birthdate = sdf.parse(medicalrecord.getBirthdate());
							Date today = new Date();
							String todayAsString = sdf.format(today);
							today = sdf.parse(todayAsString);
							
							long yearCount = (today.getTime() - birthdate.getTime()) * 315576 * 100000;
							
							if(yearCount > 18) {
								
								countPersonMap.put("adult", countPersonMap.get("adult") + 1);
								
							} else {
								
								countPersonMap.put("child", countPersonMap.get("child") + 1);
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
		
		
		//Convert the Map counter to a List to add it in the filterPersons List
		List<Map.Entry<String, Integer>> countPersonList = countPersonMap.entrySet().stream().collect(Collectors.toList());
		
		filterPersons.addAll(countPersonList);
		
		logger.info("Return all the Persons with the same address of the stationNumber saved in the JSON file.");
		return filterPersons;
		
	}

	public List<?> getAllChildrenDependingOnTheAddress(String address) {
		
		return null;
		
	}

}
