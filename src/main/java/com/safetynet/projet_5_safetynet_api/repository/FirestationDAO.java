package com.safetynet.projet_5_safetynet_api.repository;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.safetynet.projet_5_safetynet_api.dtos.ListOfElements;
import com.safetynet.projet_5_safetynet_api.model.Firestation;
import com.safetynet.projet_5_safetynet_api.util.DataManager;

import jakarta.annotation.PostConstruct;

@Repository
public class FirestationDAO {

	private static Logger logger = LogManager.getLogger("FirestationDAO");
	
	@Autowired
	DataManager dataManager;
	
	ListOfElements elements = null;
	
	/**
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
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
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public List<Firestation> getAllFirestations() throws StreamReadException, DatabindException, IOException {

		logger.info("Return all firestations saved in the JSON file.");
		return elements.getFirestations();
		
	}
	
	public Firestation saveAFirestation(Firestation firestation) throws StreamWriteException, DatabindException, IOException {
		
		List<Firestation> firestations = elements.getFirestations();
		
		firestations.add(firestation);
		
		dataManager.writeData(elements);
		
		logger.info("The firestation is saved in the JSON file.");
		return firestation;
		
	}

}
