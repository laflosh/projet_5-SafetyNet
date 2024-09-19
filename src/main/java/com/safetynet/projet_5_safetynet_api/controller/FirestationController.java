package com.safetynet.projet_5_safetynet_api.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.safetynet.projet_5_safetynet_api.model.Firestation;
import com.safetynet.projet_5_safetynet_api.service.FirestationService;

@RestController
public class FirestationController {

	@Autowired
	FirestationService firestationService;
	
	/**
	 * @return
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@GetMapping("/firestation")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Firestation> getAllFirestations() throws StreamReadException, DatabindException, IOException{
		
		return firestationService.getAllFirestations();
		
	}
	
}
