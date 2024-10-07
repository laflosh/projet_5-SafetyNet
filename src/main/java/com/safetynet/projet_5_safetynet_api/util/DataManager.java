package com.safetynet.projet_5_safetynet_api.util;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.projet_5_safetynet_api.dto.ListOfElements;

/**
 * The class manage the tool Jackson with the ObjectMapper object for 
 * the two methods for reading the json file and writing in the json file
 */
@Component
public class DataManager {
	
	private static Logger logger = LogManager.getLogger(DataManager.class);

	private String dataPath = "C:\\Workspace\\_Openclassrooms\\projet_formation_java\\projet_5\\projet_5_safetynet_api\\data.json";
	
	ListOfElements elements = null;
	
	@Autowired
	ObjectMapper objectMapper;
	
	/**
	 * The method is about reading the data in the json file and return it in a object ListOfElements
	 * @return A ListOfElements with all the data in the Json file
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public  ListOfElements getAllData() throws StreamReadException, DatabindException, IOException{
		
		if(elements == null) {
			
			elements = objectMapper.readValue(new File(dataPath), ListOfElements.class);
			
		}
		
		return elements;

	}
	
	/**
	 * The method is about writing in the json file all the modify data
	 * @param A ListOfElements for writing elements
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public void writeData(ListOfElements elements) throws StreamWriteException, DatabindException, IOException {
		
		logger.info("Saving all the data in the JSON file.");
		objectMapper
		.writerWithDefaultPrettyPrinter()
		.writeValue(new File(dataPath), elements);
		
	}
	
}
