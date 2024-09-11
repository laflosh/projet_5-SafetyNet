package com.safetynet.projet_5_safetynet_api.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.safetynet.projet_5_safetynet_api.model.Person;
import com.safetynet.projet_5_safetynet_api.repository.PersonDAO;

@Service
public class PersonService {

	@Autowired
	PersonDAO personDAO;

	public Iterable<Person> getPersons() throws StreamReadException, DatabindException, IOException {
		
		return personDAO.findAllPersons();
		
	}

	public Person savePerson(Person person) throws StreamWriteException, DatabindException, IOException {
		
		Person savedPerson = personDAO.savePerson(person);
		
		return savedPerson;
		
	}
	
}
