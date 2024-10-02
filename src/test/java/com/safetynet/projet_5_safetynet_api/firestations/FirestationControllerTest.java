package com.safetynet.projet_5_safetynet_api.firestations;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.projet_5_safetynet_api.model.Firestation;

@SpringBootTest
@AutoConfigureMockMvc
class FirestationControllerTest {

	Firestation firestation = new Firestation();
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void testGetAllFirestationsAndReturnOk() throws Exception {

		mockMvc.perform(get("/firestation/all"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", Matchers.not(Matchers.empty())))
			.andExpect(jsonPath("$",Matchers.hasSize(13)));

	}
	
	@Test
	public void testPostANewFireStationAndReturnCreated() throws Exception {
		
		firestation.setAddress("test");
		firestation.setStation(0);
		
		String requestJson = objectMapper.writeValueAsString(firestation);
		
		mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON).content(requestJson))
			.andExpect(status().isCreated());
		
	}
	
	@Test
	public void testUpdatedAFirestationAndReturnIsOk() throws Exception {
		
		testPostANewFireStationAndReturnCreated();
		
		firestation.setStation(1);
		
		String requestJson = objectMapper.writeValueAsString(firestation);
		
		mockMvc.perform(put("/firestation").contentType(MediaType.APPLICATION_JSON).content(requestJson))
			.andExpect(status().isOk());
		
	}
	
	@Test
	public void testDeletedAFirestationAndReturnNoContent() throws Exception {
		
		testPostANewFireStationAndReturnCreated();
		
		String address = "test";
		
		mockMvc.perform(delete("/firestation")
				.param("address", address))
			.andExpect(status().isOk());
		
	}

}
