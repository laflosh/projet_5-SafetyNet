package com.safetynet.projet_5_safetynet_api.repository;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.safetynet.projet_5_safetynet_api.dto.ListOfElements;
import com.safetynet.projet_5_safetynet_api.model.Firestation;
import com.safetynet.projet_5_safetynet_api.model.Medicalrecord;
import com.safetynet.projet_5_safetynet_api.model.Person;
import com.safetynet.projet_5_safetynet_api.model.specific.Child;
import com.safetynet.projet_5_safetynet_api.model.specific.FirestationWithPersons;
import com.safetynet.projet_5_safetynet_api.model.specific.HouseMember;
import com.safetynet.projet_5_safetynet_api.model.specific.Parent;
import com.safetynet.projet_5_safetynet_api.model.specific.PersonSpecificInfo;
import com.safetynet.projet_5_safetynet_api.model.specific.PersonWithMedicalrecordEmail;
import com.safetynet.projet_5_safetynet_api.model.specific.PersonWithMedicalrecordPhone;
import com.safetynet.projet_5_safetynet_api.model.specific.PersonsWithCount;
import com.safetynet.projet_5_safetynet_api.util.DataManager;

import jakarta.annotation.PostConstruct;

/**
 * Class for managing the data about the unitary endpoints 
 */
@Repository
public class UnitaryEndpointsDAO {
	
	private static Logger logger = LogManager.getLogger(UnitaryEndpointsDAO.class);
	
	@Autowired
	DataManager dataManager;
	
	ListOfElements elements = null;
	
	List<Person> persons = null;
	List<Firestation> firestations = null;
	List<Medicalrecord> medicalrecords = null;
	
	/**
	 * Recovers all the data with DataManager object
	 * 
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
	 * Recovers all the person with the same address as the firestation depending of the firestation number in parameter
	 * During the process, a count of the child and adult will be made
	 * 
	 * @param stationNumber
	 * @return PersonWithCount
	 * @throws ParseException 
	 */
	public PersonsWithCount getPersonsDependingOnTheStationNumber(int stationNumber) throws ParseException {
		
		PersonsWithCount personWithCount = new PersonsWithCount();
		
		//firestationsRequest will have only the stations and address equal to the stationNumber variable
		List<Firestation> firestationsRequest = new ArrayList<Firestation>();
		for(Firestation firestation : firestations) {
			
			if(firestation.getStation() == stationNumber) {
				
				firestationsRequest.add(firestation);
				
			}
			
		}
		
		//Keep only the persons who have the same address as the stationNumber 
		//Count adult and child depending on the birthdate in medicalrecords
		List<PersonSpecificInfo> filterPersons = new ArrayList<PersonSpecificInfo>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		LocalDate checkMajority = LocalDate.now().minusYears(18);
		
		for(Person person : persons) {
			
			for(Firestation firestationRequest : firestationsRequest) {
				
				if(firestationRequest.getAddress().equals(person.getAddress())) {
					
					PersonSpecificInfo personSpecifiInfo = new PersonSpecificInfo();
					
					personSpecifiInfo.setFirstName(person.getFirstName());
					personSpecifiInfo.setLastName(person.getLastName());
					personSpecifiInfo.setAddress(person.getAddress());
					personSpecifiInfo.setPhone(person.getPhone());
					
					filterPersons.add(personSpecifiInfo);
					
					for(Medicalrecord medicalrecord : medicalrecords) {
						
						if(medicalrecord.getFirstName().equalsIgnoreCase(person.getFirstName()) && medicalrecord.getLastName().equalsIgnoreCase(person.getLastName())) {
							
							Date input = sdf.parse(medicalrecord.getBirthdate());
							LocalDate birthdate = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
							
							if(birthdate.isBefore(checkMajority)) {
								
								if(personWithCount.getAdultCount() <= 0) {
									
									personWithCount.setAdultCount(1);
									
								} else {
									
									personWithCount.setAdultCount(personWithCount.getAdultCount() + 1);
									
								}
													
							} else {
								
								if(personWithCount.getChildCount() <= 0) {
									
									personWithCount.setChildCount(1);
									
								} else {
									
									personWithCount.setChildCount(personWithCount.getChildCount() + 1);
									
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
			personWithCount.setPersons(filterPersons);
			
		}
		
		logger.info("Return all the Persons with the same address of the stationNumber saved in the JSON file.");
		return personWithCount;
		
	}

	/**
	 * If it exist, recovers all the children with the same address as the parameter
	 * If they have parents, return it too
	 * 
	 * @param address
	 * @return HouseMember
	 * @throws ParseException
	 */
	public HouseMember getAllChildrenDependingOnTheAddress(String address) throws ParseException {
		
		HouseMember houseMember = new HouseMember();
		
		//List for adding the child depending of the address and parent of the child
		List<Child> childList = new ArrayList<Child>();
		List<Parent> parentList = new ArrayList<Parent>();
		
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
						
						if(birthdate.isAfter(checkMajority)) {
							
							Child child = new Child();
							
							child.setFirstName(person.getFirstName());
							child.setLastName(person.getLastName());
							child.setBirthdate(medicalrecord.getBirthdate());
							
							childList.add(child);
							
						} else {
							
							Parent parent = new Parent();
							
							parent.setFirstName(person.getFirstName());
							parent.setLastName(person.getLastName());
							
							parentList.add(parent);
							
						}
						
					}
					
				}
				
			}
			
			houseMember.setChild(childList);
			houseMember.setParent(parentList);
			
		}
		
		logger.info("Return all children depending of the address and their house members saved in the JSON file.");
		return houseMember;
		
	}

	/**
	 * Recovers all the phone numbers of the persons who have the same address as the firestation
	 * depending of the station number parameter
	 * 
	 * @param firestationNumber
	 * @return A list of phone number
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
		
		logger.info("Return the phone numbers denpending oh the firestation number given saved in the JSON file.");
		return phoneNumberList;
	}
	
	/**
	 * Recovers all the persons informations living at the same address as the parameter and the firestation
	 * 
	 * @param address
	 * @return FirestationWithPersons
	 */
	public FirestationWithPersons getAllPersonsLivingAndTheStationNumberDependingOfTheAddress(String address) {

		Firestation firestationRequest = new Firestation();
		
		for(Firestation firestation : firestations) {
			
			if(firestation.getAddress().equals(address)) {
				
				firestationRequest.setAddress(firestation.getAddress());
				firestationRequest.setStation(firestation.getStation());
				
			}
			
		}
		
		FirestationWithPersons firestationWithpersons = new FirestationWithPersons (); 
		List<PersonWithMedicalrecordPhone> livingPersons = new ArrayList<PersonWithMedicalrecordPhone>();
		
		firestationWithpersons.setFirestation(firestationRequest);
		
		for(Person person : persons) {
			
			if(person.getAddress().equals(firestationRequest.getAddress())) {
				
				for(Medicalrecord medicalrecord : medicalrecords) {
					
					if(medicalrecord.getFirstName().equalsIgnoreCase(person.getFirstName()) && medicalrecord.getLastName().equalsIgnoreCase(person.getLastName())){
						
						PersonWithMedicalrecordPhone personWithMedicalrecord = new PersonWithMedicalrecordPhone();
						
						personWithMedicalrecord.setFirstName(person.getFirstName());
						personWithMedicalrecord.setLastName(person.getLastName());
						personWithMedicalrecord.setBirthdate(medicalrecord.getBirthdate());
						personWithMedicalrecord.setPhone(person.getPhone());
						personWithMedicalrecord.setMedications(medicalrecord.getMedications());
						personWithMedicalrecord.setAllergies(medicalrecord.getAllergies());
						
						livingPersons.add(personWithMedicalrecord);
						
					}
					
				}
				
			}
			
			firestationWithpersons.setPersons(livingPersons);
			
		}
		
		logger.info("Return all the Persons living in the same address and the firestation saved in the JSON file.");
		return firestationWithpersons;
		
	}
	
	/**
	 * Recovers all the persons informations and their firestation depending of the station number
	 * 
	 * @param A list of stationNumbers
	 * @return A list of FirestationWithPersons
	 */
	public List<FirestationWithPersons> getAllPersonsByFirestationNumber(Integer[] stationNumbers) {
		
		List<Firestation> requestFirestations = new ArrayList<Firestation>();
		
		for(int stationNumber : stationNumbers) {
			
			for(Firestation firestation : firestations) {
				
				if(firestation.getStation() == stationNumber) {
					
					requestFirestations.add(firestation);
					
				}
				
			}
			
		}
		
		List<FirestationWithPersons> personsInfoDependingFirestation = new ArrayList<FirestationWithPersons>();
		
		for(Firestation requestFirestation : requestFirestations) {
			
			FirestationWithPersons firestationWithPersons = new FirestationWithPersons();
			List<PersonWithMedicalrecordPhone> personsInfo = new ArrayList<PersonWithMedicalrecordPhone>();
			
			firestationWithPersons.setFirestation(requestFirestation);
			
			for(Person person : persons) {
				
				if(requestFirestation.getAddress().equals(person.getAddress())) {
					
					for(Medicalrecord medicalrecord : medicalrecords) {
						
						if(medicalrecord.getLastName().equals(person.getLastName()) && medicalrecord.getFirstName().equals(person.getFirstName())) {
							
							PersonWithMedicalrecordPhone personWithMedicalrecord = new PersonWithMedicalrecordPhone();
						
							personWithMedicalrecord.setFirstName(person.getFirstName());
							personWithMedicalrecord.setLastName( person.getLastName());
							personWithMedicalrecord.setBirthdate(medicalrecord.getBirthdate());
							personWithMedicalrecord.setPhone(person.getPhone());
							personWithMedicalrecord.setMedications(medicalrecord.getMedications());
							personWithMedicalrecord.setAllergies(medicalrecord.getAllergies());
							
							personsInfo.add(personWithMedicalrecord);
							
						}
						
					}
					
				}
				
				firestationWithPersons.setPersons(personsInfo);
				
			}
			
			personsInfoDependingFirestation.add(firestationWithPersons);
			
		}
		
		return personsInfoDependingFirestation;
		
	}
	
	/**
	 * Recovers all the persons informations with the same lastname as the parameter
	 * 
	 * @param lastName
	 * @return A list of PersonWithMedicalrecordEmail
	 */
	public List<PersonWithMedicalrecordEmail> getAllInformationsOnAPersonDependingLastName(String lastName) {

		List<PersonWithMedicalrecordEmail> personsInfo = new ArrayList<PersonWithMedicalrecordEmail>();
		
		for(Person person : persons) {
			
			if(person.getLastName().equals(lastName)) {
				
				for(Medicalrecord medicalrecord : medicalrecords) {
					
					if(medicalrecord.getLastName().equals(person.getLastName()) && medicalrecord.getFirstName().equals(person.getFirstName())) {
						
						PersonWithMedicalrecordEmail personWithMedicalrecordEmail = new PersonWithMedicalrecordEmail();
						
						personWithMedicalrecordEmail.setFirstName(person.getFirstName());
						personWithMedicalrecordEmail.setLastName(person.getLastName());
						personWithMedicalrecordEmail.setBirthdate(medicalrecord.getBirthdate());
						personWithMedicalrecordEmail.setEmail(person.getEmail());
						personWithMedicalrecordEmail.setMedications(medicalrecord.getMedications());
						personWithMedicalrecordEmail.setAllergies(medicalrecord.getAllergies());
						
						personsInfo.add(personWithMedicalrecordEmail);
						
					}
					
				}
				
			}
			
		}
		
		logger.info("Return all the Persons informations saved in the JSON file.");
		return personsInfo;
		
	}


	/**
	 * Recovers all the email address of the persons living in the city parameter
	 * 
	 * @param city
	 * @return A list of email
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
