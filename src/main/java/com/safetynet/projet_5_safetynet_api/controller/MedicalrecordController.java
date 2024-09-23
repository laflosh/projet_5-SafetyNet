package com.safetynet.projet_5_safetynet_api.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

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

@RestController
@RequestMapping("/medicalrecord")
public class MedicalrecordController {

	@Autowired
	MedicalrecordService medicalrecordService;
	
	/**
	 * @return
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<Medicalrecord> getAllMedicalrecords() throws StreamReadException, DatabindException, IOException{
		
		return medicalrecordService.getAllMedicalrecords();
		
	}
	
	/**
	 * @param medicalrecord
	 * @param ucb
	 * @return
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@PostMapping
	public ResponseEntity<?> saveAMedicalrecord(@RequestBody Medicalrecord medicalrecord,  UriComponentsBuilder ucb) throws StreamWriteException, DatabindException, IOException{
		
		if(medicalrecord == null) {
			
			return (ResponseEntity<?>) ResponseEntity.badRequest();
			
		} else {
			
			  URI locationUri = ucb.path("/medicalrecord")
					      .build()
					      .toUri();
			
			return ResponseEntity.created(locationUri).body(medicalrecordService.saveAMedicalrecord(medicalrecord));
			
		}
		
	}
	
	/**
	 * @param firstName
	 * @param lastName
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@DeleteMapping
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteAMedicalrecord(@RequestParam(required = false, value = "firstName") String firstName, @RequestParam(required = false, value = "lastName") String lastName) throws StreamWriteException, DatabindException, IOException {
		
		medicalrecordService.deleteAMedicalrecord(firstName, lastName);
		
	}
	
	/**
	 * @param medicalrecord
	 * @return
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@PutMapping
	public ResponseEntity<?> updateAMedicalRecord(@RequestBody Medicalrecord medicalrecord) throws StreamWriteException, DatabindException, IOException{
		
	if(medicalrecord == null) {
			
			return (ResponseEntity<?>) ResponseEntity.badRequest();
			
		} else {
			
			return ResponseEntity.ok().body(medicalrecordService.updateAMedicalRecord(medicalrecord));
			
		}
		
	}
	
}
