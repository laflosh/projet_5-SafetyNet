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
import com.safetynet.projet_5_safetynet_api.dto.ListOfElements;
import com.safetynet.projet_5_safetynet_api.model.Person;
import com.safetynet.projet_5_safetynet_api.util.DataManager;

import jakarta.annotation.PostConstruct;

/**
 * Class for managing the data about person of the CRUD methods
 */
@Repository
public class PersonDAO {

	private static Logger logger = LogManager.getLogger(PersonDAO.class);
	
	@Autowired
	DataManager dataManager;
	
	ListOfElements elements = null;
	
	/**
	 * Recovers all the data with DataManager object
	 * 
	 * @throws IOException 
	 * @throws DatabindException 
	 * @throws StreamReadException 
	 * 
	 */
	@PostConstruct
	public void initData() throws StreamReadException, DatabindException, IOException {
		
		if(elements == null) {
			
			elements = dataManager.getAllData();
			logger.info("Fetching all data saved in the JSON file.");
			
		}
		
	}
	
	/**
	 * Return the list of persons of the ListOfElements's object
	 * 
	 * @return A list of persons
	 * @throws IOException 
	 * @throws DatabindException 
	 * @throws StreamReadException 
	 */
	public List<Person> findAllPersons() throws StreamReadException, DatabindException, IOException {

		logger.info("Return all persons saved in the JSON file.");
		return elements.getPersons();
		
	}

	/**
	 * Add a object person in the person's list and writing to the json file
	 * 
	 * @param A object person
	 * @return The object created
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public Person savePerson(Person person) throws StreamWriteException, DatabindException, IOException {
		
		List<Person> persons = elements.getPersons();
		
		persons.add(person);
		
		dataManager.writeData(elements);
		
		logger.info("The person is saved in the JSON file.");
		return person;
		
	}

	/**
	 * Delete a person in the person's list depending of the fristname and the lastname 
	 * and wrinting to the json file
	 * 
	 * @param firstName of a person
	 * @param lastName of a person
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public void deletePerson(String firstName,String lastName) throws StreamWriteException, DatabindException, IOException {

		List<Person> persons = elements.getPersons();
		
		Iterator<Person> personsIterator = persons.iterator();
		
		while(personsIterator.hasNext()) {
			
			Person person = personsIterator.next();
			
			if(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
				
				personsIterator.remove();
				
			}
			
		}
		
		dataManager.writeData(elements);
		logger.info("The person is deleted in the JSON file.");
		
	}
	
	/**
	 * Update a person object in the person's list finding by the firstname and the lastname 
	 * and wrinting to the json file
	 * 
	 * @param updatePerson
	 * @return the object updated
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
			
		dataManager.writeData(elements);
		logger.info("The person is updated in the JSON file.");
		
		return updatePerson;
	}

}
