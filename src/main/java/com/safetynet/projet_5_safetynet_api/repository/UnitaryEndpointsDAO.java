package com.safetynet.projet_5_safetynet_api.repository;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
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
		
		//Map for counting how many adults and children depending of the station number
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		LocalDate today = LocalDate.now().minusYears(18);
		
		for(Person person : persons) {
			
			for(Firestation firestationRequest : firestationsRequest) {
				
				if(firestationRequest.getAddress().equals(person.getAddress())) {
					
					filterPersons.add(person);
					
					for(Medicalrecord medicalrecord : medicalrecords) {
						
						if(medicalrecord.getFirstName().equalsIgnoreCase(person.getFirstName()) && medicalrecord.getLastName().equalsIgnoreCase(person.getLastName())) {
							
							Date input = sdf.parse(medicalrecord.getBirthdate());
							LocalDate birthdate = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
							
							if(birthdate.isBefore(today)) {
								
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

	/**
	 * @param address
	 * @return
	 * @throws ParseException
	 */
	public List<Object> getAllChildrenDependingOnTheAddress(String address) throws ParseException {
		
		//List for adding the child depending of the address and housemembers of the child
		List<Object> filterChildrenList = new ArrayList<Object>();
		List<Object> houseMembersList = new ArrayList<Object>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		LocalDate today = LocalDate.now().minusYears(18);
		
		for(Person person : persons) {
			
			if(person.getAddress().equals(address)) {
				
				for(Medicalrecord medicalrecord : medicalrecords) {
					
					if(medicalrecord.getFirstName().equalsIgnoreCase(person.getFirstName()) && medicalrecord.getLastName().equalsIgnoreCase(person.getLastName())) {
						
						Date input = sdf.parse(medicalrecord.getBirthdate());
						LocalDate birthdate = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						
						if(birthdate.isAfter(today)) {
							
							Map<String, String> child = new HashMap<String, String>();
							
							child.put("firstName", person.getFirstName());
							child.put("lastName", person.getLastName());
							child.put("birthdate", medicalrecord.getBirthdate());
							
							List<Map.Entry<String, String>> childList = child.entrySet().stream().collect(Collectors.toList());
							
							filterChildrenList.addAll(childList);
							
						} else {
							
							Map<String, String> houseMember = new HashMap<String, String>();
							
							houseMember.put("firstName", person.getFirstName());
							houseMember.put("lastName", person.getLastName());
							
							List<Map.Entry<String, String>> houseMemberList = houseMember.entrySet().stream().collect(Collectors.toList());
							
							houseMembersList.addAll(houseMemberList);
							
						}
						
					}
					
				}
				
			}
			
		}

		//Fusion two filter List
		filterChildrenList.add(houseMembersList);
		
		return filterChildrenList;
		
	}

	/**
	 * @param firestationNumber
	 * @return
	 */
	public List<String> getAllThePhoneNumberDependingOnTheFirestationNumber(int firestationNumber) {
		
		List<String> phoneNumberList = new ArrayList<String>();
		
		//firestationsRequest will have only the stations and address equal to the stationNumber variable
		List<Firestation> firestationsRequest = new ArrayList<Firestation>();
		for(Firestation firestation : firestations) {
			
			if(firestation.getStation() == firestationNumber) {
				
				firestationsRequest.add(firestation);
				
			}
			
		}
		
		//Add the phone number for the person who has the same address as the firestation
		for(Person person : persons) {
			
			for(Firestation firestationRequest : firestationsRequest) {
				
				if(firestationRequest.getAddress().equals(person.getAddress())) {
					
					phoneNumberList.add(person.getPhone());
					
				}
				
			}
			
		}
		
		return phoneNumberList;
	}

	public List<String> getAllEmailAddressDependingOfTheCity(String city) {
		
		List<String> emailList = new ArrayList<String>();
		
		for(Person person : persons) {
			
			if(person.getCity().equals(city)) {
				
				emailList.add(person.getEmail());
				
			}
			
		}
		
		return emailList;
		
	}

}
