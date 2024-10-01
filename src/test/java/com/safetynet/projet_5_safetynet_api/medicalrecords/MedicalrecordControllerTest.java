package com.safetynet.projet_5_safetynet_api.medicalrecords;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.projet_5_safetynet_api.model.Medicalrecord;

@SpringBootTest
@AutoConfigureMockMvc
class MedicalrecordControllerTest {
	
	Medicalrecord medicalrecord = new Medicalrecord();
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	void testGetAllMedicalrecordsAndReturnOk() throws Exception {
		
		mockMvc.perform(get("/medicalrecord"))
			.andExpect(status().isOk())
			.andReturn();
		
	}
	
	@Test
	public void testPostANewMedicalrecordAndReturnCreated() throws Exception {
		
		medicalrecord.setFirstName("test");
		medicalrecord.setLastName("test");
		medicalrecord.setBirthdate("00/00/0000");
		medicalrecord.setAllergies(new String[] {"test"});
		medicalrecord.setMedications(new String[] {"test"});
		
		String requestJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(medicalrecord);
		
		mockMvc.perform(post("/medicalrecord").contentType(MediaType.APPLICATION_JSON).content(requestJson))
			.andExpect(status().isCreated())
			.andReturn();
		
	}
	
	@Test
	public void testUpdatedAMedicalrecordCreatedAndReturnIsOk() throws Exception {
		
		testPostANewMedicalrecordAndReturnCreated();
		
		medicalrecord.setBirthdate("11/11/1111");
		medicalrecord.setAllergies(new String[] {"try", "try"});
		medicalrecord.setMedications(new String[] {"try"});
		
		String requestJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(medicalrecord);
		
		mockMvc.perform(put("/medicalrecord").contentType(MediaType.APPLICATION_JSON).content(requestJson))
			.andExpect(status().isOk())
			.andReturn();
		
	}
	
	@Test
	public void testDeletedAMedicalrecordAndReturnNoContent() throws Exception {
		
		testPostANewMedicalrecordAndReturnCreated();
		
		String firstName = "test";
		String lastName = "test";
		
		mockMvc.perform(delete("/medicalrecord")
				.param("firstName", firstName)
				.param("lastName", lastName))
			.andExpect(status().isOk())
			.andReturn();
		
	}

}
