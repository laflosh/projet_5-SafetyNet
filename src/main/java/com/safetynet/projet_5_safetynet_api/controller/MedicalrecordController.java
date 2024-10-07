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
import com.safetynet.projet_5_safetynet_api.model.Medicalrecord;
import com.safetynet.projet_5_safetynet_api.service.MedicalrecordService;

/**
 * The controller is for the management of the CRUD methods for medicalrecord
 */
@RestController
@RequestMapping("/medicalrecord")
public class MedicalrecordController {

	private static Logger logger = LogManager.getLogger("MedicalrecordController");
	
	@Autowired
	MedicalrecordService medicalrecordService;
	
	/**
	 * Recovers all the medicalrecords and return it
	 * 
	 * @return A list of medicalrecords
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@GetMapping("/all")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Medicalrecord> getAllMedicalrecords() throws StreamReadException, DatabindException, IOException{
		
		logger.info("Trying to acces to get all medicalrecords");
		return medicalrecordService.getAllMedicalrecords();
		
	}
	
	/**
	 * Create a new medical record in the json file and return it
	 * 
	 * @param A object medicalrecord
	 * @param ucb
	 * @return The object created 
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@PostMapping
	public ResponseEntity<?> saveAMedicalrecord(@RequestBody Medicalrecord medicalrecord,  UriComponentsBuilder ucb) throws StreamWriteException, DatabindException, IOException{
		
		logger.info("Trying to save a medicalrecord in the json file");
		if(medicalrecord == null) {
			
			return (ResponseEntity<?>) ResponseEntity.badRequest().header("error", "medicalrecord is null").build();
			
		} else {
			
			  URI locationUri = ucb.path("/medicalrecord")
					      .build()
					      .toUri();
			
			return ResponseEntity.created(locationUri).body(medicalrecordService.saveAMedicalrecord(medicalrecord));
			
		}
		
	}
	
	/**
	 * Delete a medical record depending of the firstname and the lastname
	 * 
	 * @param firstName of the medicalrecord
	 * @param lastName of the medicalrecord
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@DeleteMapping
	@ResponseStatus(code = HttpStatus.OK)
	public void deleteAMedicalrecord(@RequestParam(required = false, value = "firstName") String firstName, @RequestParam(required = false, value = "lastName") String lastName) throws StreamWriteException, DatabindException, IOException {
		
		logger.info("Trying to delete a medicalrecord in the json file");
		medicalrecordService.deleteAMedicalrecord(firstName, lastName);
		
	}
	
	/**
	 * Update a medicalrecord in the json file and return it
	 * 
	 * @param A modify object medicalrecord
	 * @return The object updated
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@PutMapping
	public ResponseEntity<?> updateAMedicalRecord(@RequestBody Medicalrecord medicalrecord) throws StreamWriteException, DatabindException, IOException{
		
		logger.info("Trying to update a medicalrecord in the json file");
		if(medicalrecord == null) {
				
				return (ResponseEntity<?>) ResponseEntity.badRequest().header("error", "medicalrecord is null").build();
				
			} else {
				
				return ResponseEntity.ok().body(medicalrecordService.updateAMedicalRecord(medicalrecord));
				
		}
		
	}
	
}
