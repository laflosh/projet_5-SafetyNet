package com.safetynet.projet_5_safetynet_api.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.projet_5_safetynet_api.model.Person;
import com.safetynet.projet_5_safetynet_api.service.UnitaryEndpointsService;

@RestController
public class UnitaryEndpointsController {

	@Autowired
	UnitaryEndpointsService unitaryEndpointsService;
	
	/**
	 * @param stationNumber
	 * @return
	 * @throws ParseException 
	 */
	@GetMapping("/firestation/{stationNumber}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Object> getPersonsDependingOnTheStationNumber(@PathVariable("stationNumber") int stationNumber) throws ParseException{
		
		return unitaryEndpointsService.getPersonsDependingOnTheStationNumber(stationNumber);
		
	}
	
	/**
	 * @param address
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("/childAlert")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Object> getAllChildrenDependingOnTheAddress(@RequestParam(value = "address") String address) throws ParseException{
		
		return unitaryEndpointsService.getAllChildrenDependingOnTheAddress(address);
		
	}
	
	/**
	 * @param firestation
	 * @return
	 */
	@GetMapping("/phoneAlert")
	@ResponseStatus(code = HttpStatus.OK)
	public List<String> getAllThePhoneNumberDependingOnTheFirestationNumber(@RequestParam(value = "firestation") int firestation){
		
		return unitaryEndpointsService.getAllThePhoneNumberDependingOnTheFirestationNumber(firestation);
		
	}
	
	/**
	 * @param address
	 * @return
	 */
	@GetMapping("/fire")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Object> getAllPersonsLivingAndTheStationNumberDependingOfTheAddress(@RequestParam(value = "address") String address){
		
		return unitaryEndpointsService.getAllPersonsLivingAndTheStationNumberDependingOfTheAddress(address);
		
	}
	
	@GetMapping("/flood/stations")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Object> getAllPersonsByFirestationNumber(@RequestParam(value = "stations")Integer[] stationNumbers){
		
		return unitaryEndpointsService.getAllPersonsByFirestationNumber(stationNumbers);
		
	}
	
	/**
	 * @param lastName
	 * @return
	 */
	@GetMapping("/{lastName}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Object> getAllInformationsOnAPersonDependingLastName(@PathVariable("lastName") String  lastName){
		
		return unitaryEndpointsService.getAllInformationsOnAPersonDependingLastName(lastName);
		
	}
	
	/**
	 * @param city
	 * @return
	 */
	@GetMapping("/communityEmail")
	@ResponseStatus(code = HttpStatus.OK)
	public List<String> getAllEmailAddressDependingOfTheCity(@RequestParam(value = "city") String city){
		
		return unitaryEndpointsService.getAllEmailAddressDependingOfTheCity(city);
		
	}
	
}
