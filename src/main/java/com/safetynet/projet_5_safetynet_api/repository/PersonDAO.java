package com.safetynet.projet_5_safetynet_api.repository;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.safetynet.projet_5_safetynet_api.dtos.ListOfElements;
import com.safetynet.projet_5_safetynet_api.model.Person;
import com.safetynet.projet_5_safetynet_api.util.DataManager;

import jakarta.annotation.PostConstruct;

@Repository
public class PersonDAO {

	private static Logger logger = LogManager.getLogger("PersonDAO");
	
	@Autowired
	DataManager dataManager;
	
	ListOfElements elements = null;
	
	/**
	 * @throws IOException 
	 * @throws DatabindException 
	 * @throws StreamReadException 
	 * 
	 */
	@PostConstruct
	public void initData() throws StreamReadException, DatabindException, IOException {
		
		if(elements == null) {
			
			logger.info("Fetching all data saved in the JSON file.");
			elements = dataManager.getAllData();
			
		}
		
	}
	
	/**
	 * @return
	 * @throws IOException 
	 * @throws DatabindException 
	 * @throws StreamReadException 
	 */
	public List<Person> findAllPersons() throws StreamReadException, DatabindException, IOException {

		logger.info("Return all persons saved in the JSON file.");
		return elements.getPersons();
		
	}

	public Person savePerson(Person person) throws StreamWriteException, DatabindException, IOException {
		
		List<Person> persons = elements.getPersons();
		
		persons.add(person);
		
		dataManager.writeData(elements);
		
		logger.info("The person is saved in the JSON file.");
		return person;
		
	}
	
	/**
	 * @param deletePerson
	 * @throws IOException 
	 * @throws DatabindException 
	 * @throws StreamWriteException 
	 */
	public void deletePerson(String firstName,String lastName) throws StreamWriteException, DatabindException, IOException {

		List<Person> persons = elements.getPersons();
		
		//persons.removeIf(person -> person.getFirstName().equals(deletePerson.getFirstName()) && person.getLastName().equals(deletePerson.getLastName()));
		
		//persons.stream().forEach(null);
		
		Iterator<Person> personsIterator = persons.iterator();
		
		while(personsIterator.hasNext()) {
			
			Person person = personsIterator.next();
			
			if(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
				
				personsIterator.remove();
				
			}
			
		}
		
		logger.info("The person is deleted in the JSON file.");
		dataManager.writeData(elements);
		
	}
	
	/**
	 * @param updatePerson
	 * @return
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public Person updatePerson(Person updatePerson) throws StreamWriteException, DatabindException, IOException {
	
		List<Person> persons = elements.getPersons();
		
		Iterator<Person> personsIterator = persons.iterator();
		
		while(personsIterator.hasNext()) {
			
			Person person = personsIterator.next();
			
			if(person.getFirstName().equals(updatePerson.getFirstName()) && person.getLastName().equals(updatePerson.getLastName())) {
				
					person.setAddress(updatePerson.getAddress());
					person.setCity(updatePerson.getCity());
					person.setZip(updatePerson.getZip());
					person.setPhone(updatePerson.getPhone());
					person.setEmail(updatePerson.getEmail());
					
					updatePerson = person;
				
			}
				
		}
			
		logger.info("The person is updated in the JSON file.");
		dataManager.writeData(elements);
		
		return updatePerson;
	}

}
