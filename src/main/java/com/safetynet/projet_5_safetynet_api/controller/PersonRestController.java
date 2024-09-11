package com.safetynet.projet_5_safetynet_api.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
		//return personService.getPersons();
		
	}
	
	@PostMapping("/person")
	public ResponseEntity<?> savePerson(@RequestBody Person person) throws StreamWriteException, DatabindException, IOException {
		
		return ResponseEntity.ok().body(personService.savePerson(person));
		//return personService.savePerson(person);
		
	}
	
}
