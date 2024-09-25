package com.safetynet.projet_5_safetynet_api.controller;

import java.text.ParseException;
import java.util.List;

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
	
	@GetMapping("/childAlert")
	@ResponseStatus(code = HttpStatus.OK)
	public List<?> getAllChildrenDependingOnTheAddress(@RequestParam(value = "address") String address){
		
		return unitaryEndpointsService.getAllChildrenDependingOnTheAddress(address);
		
	}
	
}
