package com.safetynet.projet_5_safetynet_api.unitaryendpoints;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
	void testGetAllPersonsDependingOnTheFirstationNumberAndReturnOk() throws Exception {

		mockMvc.perform(get("/firestation?stationNumber=1"))
			.andExpect(status().isOk())
			.andReturn();
		
	}
	
	@Test
	void testGetAllChildrenDependingOnTheAddressAndReturnOk() throws Exception {
		
		String address = "1509 Culver St";
		
		mockMvc.perform(get("/childAlert")
				.param("address", address))
			.andExpect(status().isOk())
			.andReturn();
		
	}

}
