package com.safetynet.projet_5_safetynet_api.service;

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
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
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
import com.safetynet.projet_5_safetynet_api.repository.FirestationDAO;
import com.safetynet.projet_5_safetynet_api.repository.MedicalrecordDAO;
import com.safetynet.projet_5_safetynet_api.repository.PersonDAO;

@Service
public class UnitaryEndpointsService {

	private static Logger logger = LogManager.getLogger(UnitaryEndpointsService.class);
	
	@Autowired
	FirestationDAO firestationDAO;
	
	@Autowired
	PersonDAO personDAO;
	
	@Autowired
	MedicalrecordDAO medicalrecordDAO;
	
	/**
	 * Recovers all the person with the same address as the firestation depending of the firestation number in parameter
	 * During the process, a count of the child and adult will be made
	 * 
	 * @param stationNumber
	 * @return PersonWithCount
	 * @throws ParseException
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public PersonsWithCount getPersonsDependingOnTheStationNumber(int stationNumber) throws ParseException, StreamReadException, DatabindException, IOException {

		PersonsWithCount personWithCount = new PersonsWithCount();
		
		List<Firestation> firestations = getAListOfFirestationByStatioNumber(stationNumber);
		//Keep only the persons who have the same address as the stationNumber 
		List<PersonSpecificInfo> personSpecificInfo = getPersonSpecifiInfoByFirestaionAddress(firestations);
		personWithCount.setPersons(personSpecificInfo);

		//Count adult and child depending on the birthdate in medicalrecords
		countAdultAndChildByBirthdate(personSpecificInfo, personWithCount);
		
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
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public HouseMember getAllChildrenDependingOnTheAddress(String address) throws ParseException, StreamReadException, DatabindException, IOException {

		HouseMember houseMember = new HouseMember();
		//Get the person living at the same address
		List<Person> persons = getPersonsLivingAtTheSameAddress(address);
		
		//adding the child depending of the address and parent of the child
		//Add the child who living in the address
		//Else add house members in a another List who living in the address
		settingTheHouseMemberWithChildAndParent(persons, houseMember);
		
		logger.info("Return all children depending of the address and their house members saved in the JSON file.");
		return houseMember;
		
	}

	/**
	 * Recovers all the phone numbers of the persons who have the same address as the firestation
	 * depending of the station number parameter
	 * 
	 * @param firestationNumber
	 * @return A list of phone number
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public List<String> getAllThePhoneNumberDependingOnTheFirestationNumber(int firestation) throws StreamReadException, DatabindException, IOException {

		List<Firestation> firestations = getAListOfFirestationByStatioNumber(firestation);
		
		//Add the phone number for the person who has the same address as the firestation
		logger.info("Return the phone numbers denpending oh the firestation number given saved in the JSON file.");
		return getPhoneNumberOfPersonDependingOfStationNumber(firestations);
		
	}
	
	/**
	 * Recovers all the persons informations living at the same address as the parameter and the firestation
	 * 
	 * @param address
	 * @return FirestationWithPersons
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public FirestationWithPersons getAllPersonsLivingAndTheStationNumberDependingOfTheAddress(String address) throws StreamReadException, DatabindException, IOException {

		FirestationWithPersons firestationWithPersons = new FirestationWithPersons (); 
		Firestation firestation = getFirestationByAddress(address);
		
		firestationWithPersons.setFirestation(firestation);
		
		settingPersonsLivingNearAFirestation(firestation, firestationWithPersons);
		
		logger.info("Return all the Persons living in the same address and the firestation saved in the JSON file.");
		return firestationWithPersons;
		
	}
	
	/**
	 * Recovers all the persons informations and their firestation depending of the station number
	 * 
	 * @param A list of stationNumbers
	 * @return A list of FirestationWithPersons
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public List<FirestationWithPersons> getAllPersonsByFirestationNumber(Integer[] stationNumbers) throws StreamReadException, DatabindException, IOException {

		List<Firestation> firestations =  getAListOfFirestationByAListOfStatioNumber(stationNumbers);
		
		List<FirestationWithPersons> personsInfoDependingFirestation = new ArrayList<FirestationWithPersons>();
		
		for(Firestation firestation : firestations) {
			
			FirestationWithPersons firestationWithPersons = new FirestationWithPersons();
			firestationWithPersons.setFirestation(firestation);
			
			List<PersonWithMedicalrecordPhone> personsInfo = settingPersonsLivingNearAFirestation(firestation);
			
			firestationWithPersons.setPersons(personsInfo);
			
			personsInfoDependingFirestation.add(firestationWithPersons);
			
		}
		
		return personsInfoDependingFirestation;
		
	}
	
	/**
	 * Recovers all the persons informations with the same lastname as the parameter
	 * 
	 * @param lastName
	 * @return A list of PersonWithMedicalrecordEmail
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public List<PersonWithMedicalrecordEmail> getAllInformationsOnAPersonDependingLastName(String lastName) throws StreamReadException, DatabindException, IOException {

		logger.info("Return all the Persons informations saved in the JSON file.");
		return getInformationsOnAPersonByLastNameWithEmail(lastName);
		
	}

	/**
	 * Recovers all the email address of the persons living in the city parameter
	 * 
	 * @param city
	 * @return A list of email
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public List<String> getAllEmailAddressDependingOfTheCity(String city) throws StreamReadException, DatabindException, IOException {

		logger.info("Return all email address of persons in a city given saved in the JSON file.");
		return getAllEmailAddressOfPersonDependingOfTheCity(city);
		
	}
	
	/**
	 * firestationsRequest will have only the stations and address equal to the stationNumber variable
	 * 
	 * @param stationNumber
	 * @return
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	List<Firestation> getAListOfFirestationByStatioNumber(int stationNumber) throws StreamReadException, DatabindException, IOException{
		
		List<Firestation> firestations = firestationDAO.getAllFirestations();
		List<Firestation> requestFirestation = new ArrayList<Firestation>();
		
		for(Firestation firestation : firestations) {
			
			if(firestation.getStation() == stationNumber) {
				
				requestFirestation.add(firestation);
				
			}
			
		}
		
		return requestFirestation;
		
	}
	
	/**
	 * firestationsRequest will have only the stations and address equal to a List of stationNumber variable
	 * 
	 * @param stationNumber
	 * @return
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	List<Firestation> getAListOfFirestationByAListOfStatioNumber(Integer[] stationNumbers) throws StreamReadException, DatabindException, IOException{
		
		List<Firestation> firestations = firestationDAO.getAllFirestations();
		List<Firestation> requestFirestations = new ArrayList<Firestation>();
		
		for(int stationNumber : stationNumbers) {
			
			for(Firestation firestation : firestations) {
				
				if(firestation.getStation() == stationNumber) {
					
					requestFirestations.add(firestation);
					
				}
				
			}
			
		}
		
		return requestFirestations;
		
	}
	
	/**
	 * firestationsRequest will have only the stations and address equal to the address variable
	 * 
	 * @param address
	 * @return
	 * @throws IOException 
	 * @throws DatabindException 
	 * @throws StreamReadException 
	 */
	Firestation getFirestationByAddress(String address) throws StreamReadException, DatabindException, IOException{
		
		List<Firestation> firestations = firestationDAO.getAllFirestations();
		Firestation requestFirestation = new Firestation();
		
		for(Firestation firestation : firestations) {
			
			if(firestation.getAddress().equals(address)) {
				
				requestFirestation.setAddress(firestation.getAddress());
				requestFirestation.setStation(firestation.getStation());
				
			}
			
		}
		
		return requestFirestation;
		
	}
	
	/**
	 * @param firestations
	 * @return
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	List<PersonSpecificInfo> getPersonSpecifiInfoByFirestaionAddress(List<Firestation> firestations) throws StreamReadException, DatabindException, IOException{
		
		List<Person> persons = personDAO.findAllPersons();
		List<PersonSpecificInfo> filterPersons = new ArrayList<PersonSpecificInfo>();
		
		for(Person person : persons) {
			
			for(Firestation firestation : firestations) {
				
				if(firestation.getAddress().equals(person.getAddress())) {
					
					PersonSpecificInfo personSpecifiInfo = new PersonSpecificInfo();
					
					personSpecifiInfo.setFirstName(person.getFirstName());
					personSpecifiInfo.setLastName(person.getLastName());
					personSpecifiInfo.setAddress(person.getAddress());
					personSpecifiInfo.setPhone(person.getPhone());
					
					filterPersons.add(personSpecifiInfo);
				}
				
			}
			
		}
		
		return filterPersons;
	}
	
	/**
	 * @param persons
	 * @param personWithCount
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 * @throws ParseException
	 */
	void countAdultAndChildByBirthdate(List<PersonSpecificInfo> persons, PersonsWithCount personWithCount) throws StreamReadException, DatabindException, IOException, ParseException {
	
		List<Medicalrecord> medicalrecords = medicalrecordDAO.getAllMedicalrecords();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		LocalDate checkMajority = LocalDate.now().minusYears(18);
		
		for(PersonSpecificInfo person : persons) {
				
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
	
	/**
	 * @param address
	 * @return
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	List<Person> getPersonsLivingAtTheSameAddress(String address) throws StreamReadException, DatabindException, IOException{
		
		List<Person> persons = personDAO.findAllPersons();
		List<Person> filterPerson = new ArrayList<Person>();
		
		for(Person person : persons) {
			
			if(person.getAddress().equals(address)) {
				
				filterPerson.add(person);
				
			}
			
		}
		
		return filterPerson;
		
	}
	
	/**
	 * @param persons
	 * @param houseMember
	 * @throws ParseException
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	void settingTheHouseMemberWithChildAndParent(List<Person> persons, HouseMember houseMember) throws ParseException, StreamReadException, DatabindException, IOException {
		
		List<Medicalrecord> medicalrecords = medicalrecordDAO.getAllMedicalrecords();
		List<Child> childList = new ArrayList<Child>();
		List<Parent> parentList = new ArrayList<Parent>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		LocalDate checkMajority = LocalDate.now().minusYears(18);
		
		for(Person person : persons) {
				
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
			
			houseMember.setChild(childList);
			houseMember.setParent(parentList);
			
		}
		
	}
	
	/**
	 * @param firestations
	 * @return
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	List<String> getPhoneNumberOfPersonDependingOfStationNumber(List<Firestation> firestations) throws StreamReadException, DatabindException, IOException{
		
		List<Person> persons = personDAO.findAllPersons();
		List<String> phoneNumberList = new ArrayList<String>();
		
		for(Person person : persons) {
			
			for(Firestation firestation : firestations) {
				
				if(firestation.getAddress().equals(person.getAddress())) {
					
					phoneNumberList.add(person.getPhone());
					
				}
				
			}
			
		}
		
		return phoneNumberList;
		
	}
	
	/**
	 * @param A firestation
	 * @param firestationWithPersons
	 * @throws IOException 
	 * @throws DatabindException 
	 * @throws StreamReadException 
	 */
	void settingPersonsLivingNearAFirestation(Firestation firestation, FirestationWithPersons firestationWithPersons) throws StreamReadException, DatabindException, IOException{
		
		List<Medicalrecord> medicalrecords = medicalrecordDAO.getAllMedicalrecords();
		List<Person> persons = personDAO.findAllPersons();
		List<PersonWithMedicalrecordPhone> livingPersons = new ArrayList<PersonWithMedicalrecordPhone>();
		
		for(Person person : persons) {
			
			if(person.getAddress().equals(firestation.getAddress())) {
				
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
			
			firestationWithPersons.setPersons(livingPersons);
			
		}
		
	}
	
	/**
	 * @param firestation
	 * @return
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	List<PersonWithMedicalrecordPhone> settingPersonsLivingNearAFirestation(Firestation firestation) throws StreamReadException, DatabindException, IOException{
		
		List<Medicalrecord> medicalrecords = medicalrecordDAO.getAllMedicalrecords();
		List<Person> persons = personDAO.findAllPersons();
		List<PersonWithMedicalrecordPhone> personsInfo = new ArrayList<PersonWithMedicalrecordPhone>();
		
		for(Person person : persons) {
			
			if(firestation.getAddress().equals(person.getAddress())) {
				
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
			
		}
		
		return personsInfo;
		
	}
	
	/**
	 * @param lastName
	 * @return
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	List<PersonWithMedicalrecordEmail> getInformationsOnAPersonByLastNameWithEmail(String lastName) throws StreamReadException, DatabindException, IOException{
		
		List<Medicalrecord> medicalrecords = medicalrecordDAO.getAllMedicalrecords();
		List<Person> persons = personDAO.findAllPersons();
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
		
		return personsInfo;
		
	}
	
	/**
	 * @param city
	 * @return
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	List<String> getAllEmailAddressOfPersonDependingOfTheCity(String city) throws StreamReadException, DatabindException, IOException{
		
		List<Person> persons = personDAO.findAllPersons();
		//List of email address for the persons living in the variable city
		List<String> emailList = new ArrayList<String>();
		
		//Add the email address in the List
		for(Person person : persons) {
			
			if(person.getCity().equals(city)) {
				
				emailList.add(person.getEmail());
				
			}
			
		}
		
		return emailList;
		
	}

}
