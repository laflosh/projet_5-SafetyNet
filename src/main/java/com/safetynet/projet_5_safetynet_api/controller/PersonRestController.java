package com.safetynet.projet_5_safetynet_api.controller;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.safetynet.projet_5_safetynet_api.model.Person;
import com.safetynet.projet_5_safetynet_api.service.PersonService;

@RestController
public class PersonRestController {

	@Autowired
	PersonService personService;
	
	/**
	 * @return
	 * @throws IOException 
	 * @throws DatabindException 
	 * @throws StreamReadException 
	 */
	@GetMapping("/person")
	public ResponseEntity<?> getPersons() throws StreamReadException, DatabindException, IOException{
		
		return ResponseEntity.ok().body(personService.getPersons());
		
	}
	
	/**
	 * @param person
	 * @param ucb
	 * @return
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@PostMapping("/person")
	public ResponseEntity<?> savePerson(@RequestBody Person person, UriComponentsBuilder ucb) throws StreamWriteException, DatabindException, IOException {
		
		if(person == null) {
			
			return (ResponseEntity<?>) ResponseEntity.badRequest();
			
		} else {
			
			  URI locationUri = ucb.path("/person")
					      .build()
					      .toUri();
			
			return ResponseEntity.created(locationUri).body(personService.savePerson(person));
			
		}
		
	}
	
	/**
	 * @param person
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@DeleteMapping("/person")
	public void deletePerson(@RequestBody final Person person) throws StreamWriteException, DatabindException, IOException{
		
		personService.deletePerson(person);
		
	}
	
	/**
	 * @param person
	 * @param ucb
	 * @return
	 * @throws IOException 
	 * @throws DatabindException 
	 * @throws StreamWriteException 
	 */
	@PutMapping("/person")
	public ResponseEntity<?> updatePerson(@RequestBody Person person, UriComponentsBuilder ucb) throws StreamWriteException, DatabindException, IOException{
		
		if(person == null) {
			
			return (ResponseEntity<?>) ResponseEntity.badRequest();
			
		} else {
			
			  URI locationUri = ucb.path("/person")
					      .build()
					      .toUri();
			
			return ResponseEntity.created(locationUri).body(personService.updatePerson(person));
			
		}
		
	}
	
}
