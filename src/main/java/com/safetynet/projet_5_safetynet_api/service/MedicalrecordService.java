package com.safetynet.projet_5_safetynet_api.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.safetynet.projet_5_safetynet_api.model.Medicalrecord;
import com.safetynet.projet_5_safetynet_api.repository.MedicalrecordDAO;

@Service
public class MedicalrecordService {

	@Autowired
	MedicalrecordDAO medicalrecordDAO;
	
	public List<Medicalrecord> getAllMedicalrecords() throws StreamReadException, DatabindException, IOException {

		return medicalrecordDAO.getAllMedicalrecords();
	}

}
