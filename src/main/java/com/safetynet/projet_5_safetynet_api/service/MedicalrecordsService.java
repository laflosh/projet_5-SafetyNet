package com.safetynet.projet_5_safetynet_api.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.safetynet.projet_5_safetynet_api.model.Medicalrecords;
import com.safetynet.projet_5_safetynet_api.repository.MedicalrecordsDAO;

@Service
public class MedicalrecordsService {

	@Autowired
	MedicalrecordsDAO medicalrecordsDAO;
	
	public List<Medicalrecords> getAllMedicalrecords() throws StreamReadException, DatabindException, IOException {

		return medicalrecordsDAO.getAllMedicalrecords();
	}

}
