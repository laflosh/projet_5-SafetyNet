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
import com.safetynet.projet_5_safetynet_api.dtos.ListOfElements;

@Component
public class DataManager {
	
	private static Logger logger = LogManager.getLogger("DataManager");

	private String dataPath = "C:\\Workspace\\_Openclassrooms\\projet_formation_java\\projet_5\\projet_5_safetynet_api\\data.json";
	
	ListOfElements elements = null;
	
	@Autowired
	ObjectMapper objectMapper;
	
	public  ListOfElements getAllData() throws StreamReadException, DatabindException, IOException{
		
		if(elements == null) {
			
			elements = objectMapper.readValue(new File(dataPath), ListOfElements.class);
			
		}
		
		return elements;

	}
	
	public void writeData(ListOfElements elements) throws StreamWriteException, DatabindException, IOException {
		
		logger.info("Saving all the data in the JSON file.");
		objectMapper
		.writerWithDefaultPrettyPrinter()
		.writeValue(new File(dataPath), elements);
		
	}
	
}
