package com.safetynet.projet_5_safetynet_api.controller;

import java.text.ParseException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.projet_5_safetynet_api.model.specific.FirestationWithPersons;
import com.safetynet.projet_5_safetynet_api.model.specific.HouseMember;
import com.safetynet.projet_5_safetynet_api.model.specific.PersonWithMedicalrecordEmail;
import com.safetynet.projet_5_safetynet_api.model.specific.PersonsWithCount;
import com.safetynet.projet_5_safetynet_api.service.UnitaryEndpointsService;

/**
 * The controller is about the management of the unitary endpoints
 */
@RestController
public class UnitaryEndpointsController {

	private static Logger logger = LogManager.getLogger("UnitaryEndpointsController");
	
	@Autowired
	UnitaryEndpointsService unitaryEndpointsService;
	
	/**
	 * Recovers all the persons depending of the station number with a count of child and adult and return it
	 * 
	 * @param stationNumber
	 * @return A list of persons
	 * @throws ParseException 
	 */
	@GetMapping("/firestation")
	@ResponseStatus(code = HttpStatus.OK)
	public PersonsWithCount getPersonsDependingOnTheStationNumber(@RequestParam(value = "stationNumber") int stationNumber) throws ParseException{
		
		logger.info("Trying to acces to get all persons depending of the firestation");
		return unitaryEndpointsService.getPersonsDependingOnTheStationNumber(stationNumber);
		
	}
	
	/**
	 * Recovers all the children and their housemember if they exist and return it, depending of an address
	 * 
	 * @param address
	 * @return A object HouseMember with child(ren) and their parents
	 * @throws ParseException
	 */
	@GetMapping("/childAlert")
	@ResponseStatus(code = HttpStatus.OK)
	public HouseMember getAllChildrenDependingOnTheAddress(@RequestParam(value = "address") String address) throws ParseException{
		
		logger.info("Trying to acces to get all children and their parents depending the address");
		return unitaryEndpointsService.getAllChildrenDependingOnTheAddress(address);
		
	}
	
	/**
	 * Recovers all the phone number of persons depending of station number of firestation
	 * 
	 * @param firestation
	 * @return A list of phone numbers
	 */
	@GetMapping("/phoneAlert")
	@ResponseStatus(code = HttpStatus.OK)
	public List<String> getAllThePhoneNumberDependingOnTheFirestationNumber(@RequestParam(value = "firestation") int firestation){
		
		logger.info("Trying to acces to get all phone numbers of persons depending of an address");
		return unitaryEndpointsService.getAllThePhoneNumberDependingOnTheFirestationNumber(firestation);
		
	}
	
	/**
	 * Recovers all the persons with the same address as a firestation and the firestation
	 * 
	 * @param address
	 * @return A object FirestationWithPersons
	 */
	@GetMapping("/fire")
	@ResponseStatus(code = HttpStatus.OK)
	public FirestationWithPersons getAllPersonsLivingAndTheStationNumberDependingOfTheAddress(@RequestParam(value = "address") String address){
		
		logger.info("Trying to acces to get all persons living near a firestation depending of an address");
		return unitaryEndpointsService.getAllPersonsLivingAndTheStationNumberDependingOfTheAddress(address);
		
	}
	
	/**
	 * Recovers all the persons with their firestation depending of the station number of a firestation
	 * 
	 * @param A list of stationNumbers
	 * @return A list of FirestationWithPersons
	 */
	@GetMapping("/flood/stations")
	@ResponseStatus(code = HttpStatus.OK)
	public List<FirestationWithPersons> getAllPersonsByFirestationNumber(@RequestParam(value = "stations")Integer[] stationNumbers){
		
		logger.info("Trying to acces to get all persons living near a firestation depending of a station number");
		return unitaryEndpointsService.getAllPersonsByFirestationNumber(stationNumbers);
		
	}
	
	/**
	 * Recovers all the persons and their information depending of a lastname
	 * 
	 * @param lastName of a person
	 * @return A list of person
	 */
	@GetMapping("/{lastName}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PersonWithMedicalrecordEmail> getAllInformationsOnAPersonDependingLastName(@PathVariable("lastName") String  lastName){
		
		logger.info("Trying to acces to get all persons informations depending the last name");
		return unitaryEndpointsService.getAllInformationsOnAPersonDependingLastName(lastName);
		
	}
	
	/**
	 * Recovers all the email address depending of the city where they living
	 * 
	 * @param city
	 * @return A list of email
	 */
	@GetMapping("/communityEmail")
	@ResponseStatus(code = HttpStatus.OK)
	public List<String> getAllEmailAddressDependingOfTheCity(@RequestParam(value = "city") String city){
		
		logger.info("Trying to acces to get all email address of persons depending the city");
		return unitaryEndpointsService.getAllEmailAddressDependingOfTheCity(city);
		
	}
	
}
