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
import com.safetynet.projet_5_safetynet_api.model.Medicalrecord;

import jakarta.annotation.PostConstruct;

@Repository
public class MedicalrecordDAO {

	private static Logger logger = LogManager.getLogger("MedicalrecordsDAO");
	
	@Autowired
	ObjectMapper objectMapper;
	
	ListOfElements elements = null;
	
	private File data = new File("C:\\Workspace\\_Openclassrooms\\projet_formation_java\\projet_5\\projet_5_safetynet_api\\data.json");
	
	/**
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@PostConstruct
	public void initData() throws StreamReadException, DatabindException, IOException {
		
		if(elements == null) {
			
			logger.info("Fetching all data saved in the JSON file.");
			elements = objectMapper.readValue(data, ListOfElements.class);
			
		}
		
	}
	
	/**
	 * @return
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public List<Medicalrecord> getAllMedicalrecords() throws StreamReadException, DatabindException, IOException {
		
		logger.info("Return all medicalrecords saved in the JSON file.");
		return elements.getMedicalrecords();
		
	}
	
	/**
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	protected void saveElements() throws StreamWriteException, DatabindException, IOException {
		
		logger.info("Saving all the data in the JSON file.");
		objectMapper
			.writerWithDefaultPrettyPrinter()
			.writeValue(data, elements);
		
	}

}
