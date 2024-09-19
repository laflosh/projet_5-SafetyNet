package com.safetynet.projet_5_safetynet_api.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.safetynet.projet_5_safetynet_api.model.Firestation;
import com.safetynet.projet_5_safetynet_api.repository.FirestationDAO;

@Service
public class FirestationService {
	
	@Autowired
	FirestationDAO firestationDAO;

	public List<Firestation> getAllFirestations() throws StreamReadException, DatabindException, IOException {

		return firestationDAO.getAllFirestations();
		
	}
	
	public Firestation saveAFirestation(Firestation firestation) throws StreamWriteException, DatabindException, IOException {
		
		return firestationDAO.saveAFirestation(firestation);
		
	}

}
