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
import com.safetynet.projet_5_safetynet_api.model.Medicalrecord;
import com.safetynet.projet_5_safetynet_api.util.DataManager;

import jakarta.annotation.PostConstruct;

/**
 * Class for managing the data about medicalrecord of the CRUD methods
 */
@Repository
public class MedicalrecordDAO {

	private static Logger logger = LogManager.getLogger(MedicalrecordDAO.class);
	
	@Autowired
	DataManager dataManager;
	
	ListOfElements elements = null;
	
	/**
	 * Recovers all the data with DataManager object
	 * 
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@PostConstruct
	public void initData() throws StreamReadException, DatabindException, IOException {
		
		if(elements == null) {
			
			elements = dataManager.getAllData();
			logger.info("Fetching all data saved in the JSON file.");
			
		}
		
	}
	
	/**
	 * Return the list of medicalrecord in the object ListOfElements
	 * 
	 * @return A list of medicalrecords
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public List<Medicalrecord> getAllMedicalrecords() throws StreamReadException, DatabindException, IOException {
		
		logger.info("Return all medicalrecords saved in the JSON file.");
		return elements.getMedicalrecords();
		
	}
	
	/**
	 * Add a object medicalrecord in the medicalrecord's list and writing to the json file
	 * 
	 * @param A object medicalrecord
	 * @return The object created
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public Object saveAMedicalrecord(Medicalrecord medicalrecord) throws StreamWriteException, DatabindException, IOException {

		List<Medicalrecord> medicalrecords = elements.getMedicalrecords();
		
		medicalrecords.add(medicalrecord);
		
		dataManager.writeData(elements);
		
		logger.info("Saving a medicalrecord in the JSON file.");
		return medicalrecord;
	}

	/**
	 * Delete a medicalrecord in the medicalrecord's list depending of the firstname and the lastname 
	 * and wrinting to the json file
	 * 
	 * @param firstName of a medicalrecord
	 * @param lastName of a medicalrecord
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public void deleteAMedicalrecord(String firstName, String lastName) throws StreamWriteException, DatabindException, IOException {
		
		List<Medicalrecord> medicalrecords = elements.getMedicalrecords();
		
		Iterator<Medicalrecord> medicalrecordsIterator = medicalrecords.iterator();
		
		while(medicalrecordsIterator.hasNext()) {
			
			Medicalrecord medicalrecord = medicalrecordsIterator.next();
			
			if(medicalrecord.getFirstName().equals(firstName) && medicalrecord.getLastName().equals(lastName)) {
				
				medicalrecordsIterator.remove();
				
			}
			
		}
		
		dataManager.writeData(elements);
		logger.info("The medicalrecord is deleted in the JSON file.");
		
	}

	/**
	 * Update a medicalrecord in the medicalred's list finding by the firstname and lastname
	 * and writing to the json file
	 * 
	 * @param updateMedicalrecord
	 * @return The object updated
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public Medicalrecord updateAMedicalrecord(Medicalrecord updateMedicalrecord) throws StreamWriteException, DatabindException, IOException {
		
		List<Medicalrecord> medicalrecords = elements.getMedicalrecords();
		
		Iterator<Medicalrecord> medicalrecordsIterator = medicalrecords.iterator();
		
		while(medicalrecordsIterator.hasNext()) {
			
			Medicalrecord medicalrecord = medicalrecordsIterator.next();
			
			if(medicalrecord.getFirstName().equals(updateMedicalrecord.getFirstName()) && medicalrecord.getLastName().equals(updateMedicalrecord.getLastName())) {
				
				medicalrecord.setBirthdate(updateMedicalrecord.getBirthdate());
				medicalrecord.setMedications(updateMedicalrecord.getMedications());
				medicalrecord.setAllergies(updateMedicalrecord.getAllergies());
				
				updateMedicalrecord = medicalrecord;
				
			}
			
		}
		
		dataManager.writeData(elements);
		logger.info("The medicalrecord is updated in the JSON file.");
		
		return updateMedicalrecord;
		
	}
	
}
