package com.safetynet.projet_5_safetynet_api.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
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

	public Object saveAMedicalrecord(Medicalrecord medicalrecord) throws StreamWriteException, DatabindException, IOException {

		return medicalrecordDAO.saveAMedicalrecord(medicalrecord);
		
	}

	public void deleteAMedicalrecord(String firstName, String lastName) throws StreamWriteException, DatabindException, IOException {
		
		medicalrecordDAO.deleteAMedicalrecord(firstName, lastName);
		
	}

	public Medicalrecord updateAMedicalRecord(Medicalrecord medicalrecord) throws StreamWriteException, DatabindException, IOException {
		
		return medicalrecordDAO.updateAMedicalrecord(medicalrecord);
		
	}

}
