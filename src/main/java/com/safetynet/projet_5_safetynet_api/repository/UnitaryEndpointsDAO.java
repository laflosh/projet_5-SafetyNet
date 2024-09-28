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
		LocalDate checkMajority = LocalDate.now().minusYears(18);
		
		for(Person person : persons) {
			
			for(Firestation firestationRequest : firestationsRequest) {
				
				if(firestationRequest.getAddress().equals(person.getAddress())) {
					
					filterPersons.add(person);
					
					for(Medicalrecord medicalrecord : medicalrecords) {
						
						if(medicalrecord.getFirstName().equalsIgnoreCase(person.getFirstName()) && medicalrecord.getLastName().equalsIgnoreCase(person.getLastName())) {
							
							Date input = sdf.parse(medicalrecord.getBirthdate());
							LocalDate birthdate = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
							
							if(birthdate.isBefore(checkMajority)) {
								
								countPersonMap.put("adult", countPersonMap.get("adult") + 1);
								
							} else {
								
								countPersonMap.put("child", countPersonMap.get("child") + 1);
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
		
		
		//Add the Map counter to the filterList
		filterPersons.add(countPersonMap);
		
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
		LocalDate checkMajority = LocalDate.now().minusYears(18);
		
		//Add the child who living in the address
		//Else add house members in a another List who living in the address
		for(Person person : persons) {
			
			if(person.getAddress().equals(address)) {
				
				for(Medicalrecord medicalrecord : medicalrecords) {
					
					if(medicalrecord.getFirstName().equalsIgnoreCase(person.getFirstName()) && medicalrecord.getLastName().equalsIgnoreCase(person.getLastName())) {
						
						Date input = sdf.parse(medicalrecord.getBirthdate());
						LocalDate birthdate = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						
						Map<String, String> personInfo = new HashMap<String, String>();
						
						if(birthdate.isAfter(checkMajority)) {
							
							personInfo.put("firstName", person.getFirstName());
							personInfo.put("lastName", person.getLastName());
							personInfo.put("birthdate", medicalrecord.getBirthdate());
							
							filterChildrenList.add(personInfo);
							
						} else {
							
							personInfo.put("firstName", person.getFirstName());
							personInfo.put("lastName", person.getLastName());
							
							houseMembersList.add(personInfo);
							
						}
						
					}
					
				}
				
			}
			
		}

		//Add the houseMembersList filter List in the filterChildrenList
		filterChildrenList.add(houseMembersList);
		
		logger.info("Return all children depending of the address and their house members saved in the JSON file.");
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
		
		logger.info("Return the phone numbers de^pending oh the firestation number given saved in the JSON file.");
		return phoneNumberList;
	}
	
	/**
	 * @param address
	 * @return
	 */
	public List<Object> getAllPersonsLivingAndTheStationNumberDependingOfTheAddress(String address) {

		Firestation firestationRequest = new Firestation();
		
		List<Object> livingPersons = new ArrayList<Object>();
		
		for(Firestation firestation : firestations) {
			
			if(firestation.getAddress().equals(address)) {
				
				firestationRequest.setAddress(firestation.getAddress());
				firestationRequest.setStation(firestation.getStation());
				
			}
			
		}
		
		for(Person person : persons) {
			
			if(person.getAddress().equals(firestationRequest.getAddress())) {
				
				for(Medicalrecord medicalrecord : medicalrecords) {
					
					if(medicalrecord.getFirstName().equalsIgnoreCase(person.getFirstName()) && medicalrecord.getLastName().equalsIgnoreCase(person.getLastName())){
						
						Map<String, Object> houseMember = new HashMap<String, Object>();
						
						houseMember.put("firstName", person.getFirstName());
						houseMember.put("lastName", person.getLastName());
						houseMember.put("birthdate", medicalrecord.getBirthdate());
						houseMember.put("phone", person.getPhone());
						houseMember.put("medications", medicalrecord.getMedications());
						houseMember.put("allergies", medicalrecord.getAllergies());
						
						livingPersons.add(houseMember);
						
					}
					
				}
				
			}
			
		}
		
		//Add the firestation with the same address as house members
		livingPersons.add(firestationRequest);
		
		logger.info("Return all the Persons living in the same address and the firestation saved in the JSON file.");
		return livingPersons;
		
	}
	
	public List<Object> getAllPersonsByFirestationNumber(Integer[] stationNumbers) {
		
		List<Firestation> requestFirestations = new ArrayList<Firestation>();
		List<Object> personsInfoDependingFirestation = new ArrayList<Object>();
		
		for(int stationNumber : stationNumbers) {
			
			for(Firestation firestation : firestations) {
				
				if(firestation.getStation() == stationNumber) {
					
					requestFirestations.add(firestation);
					
				}
				
			}
			
		}
		
		for(Firestation requestFirestation : requestFirestations) {
			
			List<Object> personsInfo = new ArrayList<Object>();
			personsInfoDependingFirestation.add(requestFirestation);
			
			for(Person person : persons) {
				
				if(requestFirestation.getAddress().equals(person.getAddress())) {
					
					for(Medicalrecord medicalrecord : medicalrecords) {
						
						if(medicalrecord.getLastName().equals(person.getLastName()) && medicalrecord.getFirstName().equals(person.getFirstName())) {
							
							Map<String, Object> infoMap = new HashMap<String, Object>();
						
							infoMap.put("firstName", person.getFirstName());
							infoMap.put("lastName", person.getLastName());
							infoMap.put("birthdate", medicalrecord.getBirthdate());
							infoMap.put("phone", person.getPhone());
							infoMap.put("address", person.getAddress());
							infoMap.put("medications", medicalrecord.getMedications());
							infoMap.put("allergies", medicalrecord.getAllergies());
							
							personsInfo.add(infoMap);
							
						}
						
					}
					
				}
				
			}
			
			personsInfoDependingFirestation.add(personsInfo);
			
		}
		
		return personsInfoDependingFirestation;
		
	}
	
	/**
	 * @param lastName
	 * @return
	 */
	public List<Object> getAllInformationsOnAPersonDependingLastName(String lastName) {

		List<Object> personsInfo = new ArrayList<Object>();
		
		for(Person person : persons) {
			
			if(person.getLastName().equals(lastName)) {
				
				for(Medicalrecord medicalrecord : medicalrecords) {
					
					if(medicalrecord.getLastName().equals(person.getLastName()) && medicalrecord.getFirstName().equals(person.getFirstName())) {
						
						Map<String, Object> infoMap = new HashMap<String, Object>();
						
						infoMap.put("firstName", person.getFirstName());
						infoMap.put("lastName", person.getLastName());
						infoMap.put("birthdate", medicalrecord.getBirthdate());
						infoMap.put("email", person.getEmail());
						infoMap.put("medications", medicalrecord.getMedications());
						infoMap.put("allergies", medicalrecord.getAllergies());
						
						personsInfo.add(infoMap);
						
					}
					
				}
				
			}
			
		}
		
		logger.info("Return all the Persons informations saved in the JSON file.");
		return personsInfo;
		
	}


	/**
	 * @param city
	 * @return
	 */
	public List<String> getAllEmailAddressDependingOfTheCity(String city) {
		
		//List of email address for the persons living in the variable city
		List<String> emailList = new ArrayList<String>();
		
		//Add the email address in the List
		for(Person person : persons) {
			
			if(person.getCity().equals(city)) {
				
				emailList.add(person.getEmail());
				
			}
			
		}
		
		logger.info("Return all email address of persons in a city given saved in the JSON file.");
		return emailList;
		
	}

}
