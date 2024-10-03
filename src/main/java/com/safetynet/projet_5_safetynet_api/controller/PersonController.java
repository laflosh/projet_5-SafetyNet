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
import com.safetynet.projet_5_safetynet_api.model.Person;
import com.safetynet.projet_5_safetynet_api.service.PersonService;

/**
 * The controller is for the management of the CRUD methods for person
 */
@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	PersonService personService;
	
	/**
	 * Recovers all persons in the json file
	 * 
	 * @return A List of persons
	 * @throws IOException 
	 * @throws DatabindException 
	 * @throws StreamReadException 
	 */
	@GetMapping("/all")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Person> getPersons() throws StreamReadException, DatabindException, IOException{
		
		return personService.getPersons();
		
	}
	
	/**
	 * Create a person in the json file and return it
	 * 
	 * @param A object person
	 * @param ucb
	 * @return The object created
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@PostMapping
	public ResponseEntity<?> savePerson(@RequestBody Person person, UriComponentsBuilder ucb) throws StreamWriteException, DatabindException, IOException {
		
		if(person == null) {
			
			return (ResponseEntity<?>) ResponseEntity.badRequest().header("error", "person is null").build();
			
		} else {
			
			  URI locationUri = ucb.path("/person")
					      .build()
					      .toUri();
			
			return ResponseEntity.created(locationUri).body(personService.savePerson(person));
			
		}
		
	}
	

	/**
	 * Delete a object person depending of the firstname and lastname
	 * 
	 * @param firstName of a person
	 * @param lastName of a person
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@DeleteMapping
	@ResponseStatus(code = HttpStatus.OK)
	public void deletePerson(@RequestParam(required = false, value = "firstName") String firstName,@RequestParam(required = false, value = "lastName") String lastName ) throws StreamWriteException, DatabindException, IOException{
		
		personService.deletePerson(firstName,lastName);
		
	}
	
	/**
	 * Update a person in the json file and return it
	 * 
	 * @param A modify object person
	 * @return The person updated
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@PutMapping
	public ResponseEntity<?> updatePerson(@RequestBody Person person) throws StreamWriteException, DatabindException, IOException{
		
		if(person == null) {
			
			return (ResponseEntity<?>) ResponseEntity.badRequest().header("error", "person is null").build();
			
		} else {
			
			return ResponseEntity.ok().body(personService.updatePerson(person));
			
		}
		
	}
	
}
