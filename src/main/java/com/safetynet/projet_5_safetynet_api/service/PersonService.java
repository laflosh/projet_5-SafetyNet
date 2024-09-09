package com.safetynet.projet_5_safetynet_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.projet_5_safetynet_api.model.Person;
import com.safetynet.projet_5_safetynet_api.repository.PersonDAO;

@Service
public class PersonService {

	@Autowired
	PersonDAO personDAO;

	public Iterable<Person> getPersons() {
		
		return personDAO.findAllPersons();
		
	}
	
}
