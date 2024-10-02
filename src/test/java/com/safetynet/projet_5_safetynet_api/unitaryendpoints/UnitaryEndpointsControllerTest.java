package com.safetynet.projet_5_safetynet_api.unitaryendpoints;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UnitaryEndpointsControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void testGetAllPersonsDependingOnTheFirstationNumberAndReturnOk() throws Exception {

		mockMvc.perform(get("/firestation?stationNumber=1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", Matchers.not(Matchers.empty())));
		
	}
	
	@Test
	public void testGetAllChildrenDependingOnTheAddressAndReturnOk() throws Exception {
		
		String address = "1509 Culver St";
		
		mockMvc.perform(get("/childAlert")
				.param("address", address))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", Matchers.not(Matchers.empty())));
		
	}
	
	@Test
	public void testGetAllPhoneNumbersDependingOfTheStationNumberAndReturnOk() throws Exception {
		
		mockMvc.perform(get("/phoneAlert?firestation=1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", Matchers.not(Matchers.empty())));
		
	}
	
	@Test
	public void testGetAllPersonsLivingAndTheStationNumberDependingOfTheAddressAndReturnOk() throws Exception {
		
		String address = "1509 Culver St";
		
		mockMvc.perform(get("/fire")
				.param("address", address))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", Matchers.not(Matchers.empty())));
		
	}
	
	@Test
	public void testGetAllPersonsByTheFirestationNumberAndReturnOk() throws Exception {
		
		mockMvc.perform(get("/flood/stations?stations=1, 2"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", Matchers.not(Matchers.empty())));
		
	}
	
	@Test
	public void testGetPersonInformationsDependingOfTheLastNameAndReturnOk() throws Exception {
		
		String lastName = "Boyd";
		
		mockMvc.perform(get("/{lastName}", lastName))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", Matchers.not(Matchers.empty())));
		
	}
	
	@Test
	public void testGetAllEmailAddressDependingOfTheCityAndReturnOk() throws Exception {
		
		String city = "Culver";
		
		mockMvc.perform(get("/communityEmail")
				.param("city", city))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", Matchers.not(Matchers.empty())));
		
	}

}
