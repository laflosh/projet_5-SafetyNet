package com.safetynet.projet_5_safetynet_api.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.projet_5_safetynet_api.model.Person;

@Repository
public class PersonDAO {

	private static Logger logger = LogManager.getLogger("PersonDAO");
	
	@Autowired
	ObjectMapper objectMapper;
	
	private File data = new File("C:\\Workspace\\_Openclassrooms\\projet_formation_java\\projet_5\\projet_5_safetynet_api\\data.json");
	
	public Iterable<Person> findAllPersons() {

		List<Person> listPersons = new ArrayList<Person>();
		
		try {
			
			JsonNode root = objectMapper.readTree(data);
			JsonNode persons = root.path("persons");
			
			listPersons = objectMapper.treeToValue(persons, new TypeReference<List<Person>>(){});
		
			logger.info("Fetching all persons saved in the json file.");
			
			return listPersons;
			
		} catch (IOException e) {

			logger.error("Error fetching persons", e);
			
			return null;
		}
		
	}

}
