package com.safetynet.projet_5_safetynet_api.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
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
	
	@PostMapping("/firestation")
	public ResponseEntity<?> saveAFirestation(@RequestBody Firestation firestation, UriComponentsBuilder ucb) throws StreamWriteException, DatabindException, IOException{
		
		if(firestation == null) {
			
			return (ResponseEntity<?>) ResponseEntity.badRequest();
			
		} else {
			
			  URI locationUri = ucb.path("/firestation")
					      .build()
					      .toUri();
			
			return ResponseEntity.created(locationUri).body(firestationService.saveAFirestation(firestation));
			
		}
		
	}
	
}
