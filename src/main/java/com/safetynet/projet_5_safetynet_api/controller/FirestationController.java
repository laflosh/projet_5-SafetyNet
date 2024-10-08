package com.safetynet.projet_5_safetynet_api.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.safetynet.projet_5_safetynet_api.model.Firestation;
import com.safetynet.projet_5_safetynet_api.service.FirestationService;

/**
 * The controller is for the management of the CRUD methods for firestation
 */
@RestController
@RequestMapping("/firestation")
public class FirestationController {

	private static final Logger logger = LogManager.getLogger(FirestationController.class);
	
	@Autowired
	FirestationService firestationService;
	
	/**
	 * Recovers all firestation and return it
	 * 
	 * @return A List of firestations
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@GetMapping("/all")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Firestation> getAllFirestations() throws StreamReadException, DatabindException, IOException{
		
		logger.info("Trying to acces to get all firestations");
		return firestationService.getAllFirestations();
		
	}
	
	/**
	 * Create a firestation object in the json file and return it
	 * 
	 * @param A object firestation
	 * @param ucb
	 * @return the firestation created
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@PostMapping
	public ResponseEntity<?> saveAFirestation(@RequestBody Firestation firestation, UriComponentsBuilder ucb) throws StreamWriteException, DatabindException, IOException{
		
		logger.info("Trying to save a firestation in json file");
		if(firestation == null) {
			
			return (ResponseEntity<?>) ResponseEntity.badRequest().header("error", "firestation is null").build();
			
		} else {
			
			  URI locationUri = ucb.path("/firestation")
					      .build()
					      .toUri();
			
			return ResponseEntity.created(locationUri).body(firestationService.saveAFirestation(firestation));
			
		}
		
	}
	
	/**
	 * Delete a firestation in the json file depending of the address
	 * 
	 * @param the address of the firestation
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@DeleteMapping
	@ResponseStatus(code = HttpStatus.OK)
	public void deleteAFirestation(@RequestParam(required = false, value = "address") String address) throws StreamWriteException, DatabindException, IOException {
		
		logger.info("Trying to delete a firestation in the json file");
		firestationService.deleteAFirestation(address);
		
	}
	
	/**
	 * Update a firestation in the json file and return it
	 * 
	 * @param A modify object firestation
	 * @return the firestation updated
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@PutMapping
	public ResponseEntity<?> updateAFirestation(@RequestBody Firestation firestation) throws StreamWriteException, DatabindException, IOException{
		
		logger.info("Trying to update a firestation in the json file");
		if(firestation == null) {
			
			return (ResponseEntity<?>) ResponseEntity.badRequest().header("error", "firestation is null").build();
			
		} else {
			
			return ResponseEntity.ok().body(firestationService.updateAFirestation(firestation));
			
		}
		
	}
	
}
