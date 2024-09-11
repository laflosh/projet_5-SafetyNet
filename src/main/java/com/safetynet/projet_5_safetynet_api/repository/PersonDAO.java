package com.safetynet.projet_5_safetynet_api.repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.projet_5_safetynet_api.dtos.ListOfElements;
import com.safetynet.projet_5_safetynet_api.model.Person;

import jakarta.annotation.PostConstruct;

@Repository
public class PersonDAO {

	private static Logger logger = LogManager.getLogger("PersonDAO");
	
	@Autowired
	ObjectMapper objectMapper;
	
	ListOfElements elements = null;
	
	private File data = new File("C:\\Workspace\\_Openclassrooms\\projet_formation_java\\projet_5\\projet_5_safetynet_api\\data.json");
	
	/**
	 * @throws IOException 
	 * @throws DatabindException 
	 * @throws StreamReadException 
	 * 
	 */
	@PostConstruct
	public void initData() throws StreamReadException, DatabindException, IOException {
		
		if(elements == null) {
			
			logger.info("Fetching all persons saved in the JSON file.");
			elements = objectMapper.readValue(data, ListOfElements.class);
			
		}
		
	}
	
	/**
	 * @return
	 * @throws IOException 
	 * @throws DatabindException 
	 * @throws StreamReadException 
	 */
	public Iterable<Person> findAllPersons() throws StreamReadException, DatabindException, IOException {

		logger.info("Return all persons saved in the JSON file.");
		
		return elements.getPersons();
		
	}

	public Person savePerson(Person person) throws StreamWriteException, DatabindException, IOException {
		
		// ici faire la modification d'une personne
		List<Person> persons = elements.getPersons();
		
		persons.add(person);
		
		saveElements();
		
		return person;
		
		/*
		try {
			
			String personAsJson = objectMapper.writeValueAsString(person);
			JsonNode personNode = objectMapper.readTree(personAsJson);
			
			JsonNode root = objectMapper.readTree(data);
			JsonNode persons = root.path("persons");
			
			objectMapper.writeValue(persons, person);
			
			logger.info("The person is saved in the JSON file.");
			
			return personAsJson;
			
		} catch(IOException e) {
			
			logger.error("Error saving a person in the JSON file",e);
			
			return null;
			
		}
		*/
	}
	
	/**
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	protected void saveElements() throws StreamWriteException, DatabindException, IOException {
		
		objectMapper.writeValue(data, elements);
		
	}

}
